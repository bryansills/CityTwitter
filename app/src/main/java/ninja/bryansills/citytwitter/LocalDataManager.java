package ninja.bryansills.citytwitter;

import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class LocalDataManager {

    public static final String TWEETS = "KEY_TWEETS";

    private static final String PREFS_NAME = "PREFS_NAME";
    private static final String KEY_TIMESTAMP = "KEY_TIMESTAMP";
    private static final long THIRTY_SECS_IN_MILLIS = 30 * 1000;

    private SharedPreferences prefs;
    private Moshi moshi;

    public LocalDataManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        moshi = new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();
    }

    public void set(String key, List<Tweet> tweets) {
        long timestamp = System.currentTimeMillis();
        Type listOfTweetsType = Types.newParameterizedType(List.class, Tweet.class);
        JsonAdapter<List<Tweet>> jsonAdapter = moshi.adapter(listOfTweetsType);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_TIMESTAMP, timestamp);
        editor.putString(key, jsonAdapter.toJson(tweets));

        editor.commit();
    }

    public List<Tweet> get(String key) {
        long timeDiff = System.currentTimeMillis() - prefs.getLong(KEY_TIMESTAMP, -1);
        if (timeDiff > THIRTY_SECS_IN_MILLIS) {
            return null;
        } else {
            Type listOfTweetsType = Types.newParameterizedType(List.class, Tweet.class);
            JsonAdapter<Tweet> jsonAdapter = moshi.adapter(listOfTweetsType);
            try {
                return (List<Tweet>) jsonAdapter.fromJson(prefs.getString(key, ""));
            } catch (IOException e) {
                return null;
            }
        }
    }
}
