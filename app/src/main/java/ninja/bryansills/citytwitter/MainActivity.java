package ninja.bryansills.citytwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

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

        Moshi moshi = new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://damp-dawn-82482.herokuapp.com")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        CityService cityService = retrofit.create(CityService.class);

        cityService.tweets().enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                for (Tweet tweet : response.body()) {
                    Log.d("BLARG", tweet.toString());
                }
                tweetAdapter.setTweetList(response.body());
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Log.d("BLARG", t.getLocalizedMessage());
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
