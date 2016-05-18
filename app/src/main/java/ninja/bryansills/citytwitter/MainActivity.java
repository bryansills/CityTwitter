package ninja.bryansills.citytwitter;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_SERVICE_OBJ = 2;

    private LocalDataManager localDataManager;
    ComponentName serviceComponent;
    private GetTweetsService getTweetsService;

    private RecyclerView recyclerView;
    private TweetAdapter tweetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.tweet_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Tweet> items = new ArrayList<>();
        tweetAdapter = new TweetAdapter(items);
        recyclerView.setAdapter(tweetAdapter);

        localDataManager = new LocalDataManager(this);
        List<Tweet> tweets = localDataManager.get(LocalDataManager.TWEETS);

        if (tweets != null) {
            Log.d("BLARG", "Loading tweets from local storage");
            loadTweets(tweets);
        } else {
            Log.d("BLARG", "Loading tweets from the network");
            serviceComponent = new ComponentName(this, GetTweetsService.class);

            Intent startServiceIntent = new Intent(this, GetTweetsService.class);
            startServiceIntent.putExtra("messenger", new Messenger(mHandler));
            startService(startServiceIntent);
        }
    }

    public void loadTweets(List<Tweet> tweets) {
        tweetAdapter.setTweetList(tweets);
    }

    Handler mHandler = new Handler(/* default looper */) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SERVICE_OBJ:
                    getTweetsService = (GetTweetsService) msg.obj;
                    getTweetsService.setUiCallback(MainActivity.this);

                    JobScheduler tm =
                            (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

                    JobInfo.Builder builder = new JobInfo.Builder(1, serviceComponent)
                            .setMinimumLatency(15 * 1000)
                            .setOverrideDeadline(30 * 1000)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                            .setRequiresCharging(false)
                            .setRequiresDeviceIdle(false);

                    tm.schedule(builder.build());
            }
        }
    };
}
