package ninja.bryansills.citytwitter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TweetViewHolder extends RecyclerView.ViewHolder {

    public TextView author, text, timestamp;
    public ImageView authorImage;

    public TweetViewHolder(View itemView) {
        super(itemView);

        author = (TextView) itemView.findViewById(R.id.tweet_author);
        text = (TextView) itemView.findViewById(R.id.tweet_text);
        timestamp = (TextView) itemView.findViewById(R.id.tweet_timestamp);
        authorImage = (ImageView) itemView.findViewById(R.id.tweet_author_image);
    }

    public static void bind(TweetViewHolder holder, Tweet tweet) {
        holder.author.setText(tweet.author);
        holder.text.setText(tweet.text);
        holder.timestamp.setText(TimeUtils.toTimestamp(tweet.timestamp));

        Picasso.with(holder.itemView.getContext())
               .load(tweet.author_img)
               .fit().centerCrop()
               .into(holder.authorImage);
    }
}
