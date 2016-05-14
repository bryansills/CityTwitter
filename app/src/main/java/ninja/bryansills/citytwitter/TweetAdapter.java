package ninja.bryansills.citytwitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    private List<Tweet> tweetList;

    public TweetAdapter(List<Tweet> tweets) {
        this.tweetList = tweets;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);

        TweetViewHolder vh = new TweetViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        TweetViewHolder.bind(holder, tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    public void setTweetList(List<Tweet> tweets) {
        tweetList = tweets;
        notifyDataSetChanged();
    }
}
