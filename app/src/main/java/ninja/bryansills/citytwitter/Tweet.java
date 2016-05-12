package ninja.bryansills.citytwitter;

import java.util.Date;

public class Tweet {
    public final String author;
    public final String author_img;
    public final String text;
    public final Date timestamp;

    public Tweet(String author, String author_img, String text, Date timestamp) {
        this.author = author;
        this.author_img = author_img;
        this.text = text;
        this.timestamp = timestamp;
    }

    @Override public String toString() {
        return "User{" +
                "author='" + author + '\'' +
                ", author_img='" + author_img + '\'' +
                ", text='" + text + '\'' +
                ", timestamp='" + timestamp.toString() + '\'' +
                '}';
    }
}
