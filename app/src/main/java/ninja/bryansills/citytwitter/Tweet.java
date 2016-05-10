package ninja.bryansills.citytwitter;

import java.util.Date;

public class Tweet {
    public final String author;
    public final String author_url;
    public final String text;
    public final Date timestamp;

    public Tweet(String author, String authorUrl, String text, Date timestamp) {
        this.author = author;
        this.author_url = authorUrl;
        this.text = text;
        this.timestamp = timestamp;
    }

    @Override public String toString() {
        return "User{" +
                "author='" + author + '\'' +
                ", author_url='" + author_url + '\'' +
                ", text='" + text + '\'' +
                ", timestamp='" + timestamp.toString() + '\'' +
                '}';
    }
}
