package ninja.bryansills.citytwitter;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class GetTweetsService extends JobService {

    private MainActivity activity;
    private LocalDataManager localDataManager;

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCalback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BLARG", "onStartCommand");

        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = MainActivity.MSG_SERVICE_OBJ;
        m.obj = this;
        try {
            callback.send(m);
        } catch (RemoteException e) {
            Log.e("BLARG", "Error passing service object back to activity.");
        }
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("BLARG", "onStartJob");
        localDataManager = new LocalDataManager(this);
        makeNetworkCall();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void setUiCallback(MainActivity activity) {
        this.activity = activity;
    }

    private void makeNetworkCall() {
        Log.d("BLARG", "makeNetworkCall");

        Moshi moshi = new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://damp-dawn-82482.herokuapp.com")
                .client(getOkHttpClient())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        CityService cityService = retrofit.create(CityService.class);

        cityService.tweets().enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                for (Tweet tweet : response.body()) {
                    Log.d("BLARG", tweet.toString());
                }

                Log.d("BLARG", "saving tweets");
                localDataManager.set(LocalDataManager.TWEETS, response.body());

                if (activity != null) {
                    activity.loadTweets(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Log.d("BLARG", t.getLocalizedMessage());
                Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
