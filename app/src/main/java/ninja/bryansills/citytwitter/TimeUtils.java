package ninja.bryansills.citytwitter;

import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    private static final long SIXTY_SECS_IN_MILLIS = 60 * 1000;
    private static final long SIXTY_MINS_IN_MILLIS = 60 * 60 * 1000;
    private static final long TWENTY_FOUR_HOURS_IN_MILLIS = 24 * 60 * 60 * 1000;

    public static String toTimestamp(Date date) {
        long current = System.currentTimeMillis();
        long timestamp = date.getTime();
        long difference = current - timestamp;

        if (difference < 0) {
            return "WTF";
        } else if (difference < SIXTY_SECS_IN_MILLIS) {
            return (difference / TimeUnit.MILLISECONDS.convert(1L, TimeUnit.SECONDS)) + "s";
        } else if (difference < SIXTY_MINS_IN_MILLIS) {
            return (difference / TimeUnit.MILLISECONDS.convert(1L, TimeUnit.MINUTES)) + "m";
        } else if (difference < TWENTY_FOUR_HOURS_IN_MILLIS) {
            return (difference / TimeUnit.MILLISECONDS.convert(1L, TimeUnit.HOURS)) + "h";
        } else {
            return (difference / TimeUnit.MILLISECONDS.convert(1L, TimeUnit.DAYS)) + "d";
        }
    }
}
