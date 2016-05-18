package ninja.bryansills.citytwitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
    private static final int VIEW_TYPE_OBJECT_VIEW = 1;

    private List<Tweet> tweetList;

    public TweetAdapter(List<Tweet> tweets) {
        this.tweetList = tweets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v;

        switch(viewType) {
            case VIEW_TYPE_EMPTY_LIST_PLACEHOLDER:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.empty_list, parent, false);

                vh = new EmptyViewHolder(v);
                break;
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tweet, parent, false);

                vh = new TweetViewHolder(v);
                break;
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TweetViewHolder) {
            TweetViewHolder.bind((TweetViewHolder) holder, tweetList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (tweetList.isEmpty()) {
            return VIEW_TYPE_EMPTY_LIST_PLACEHOLDER;
        } else {
            return VIEW_TYPE_OBJECT_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        return tweetList.size() > 0 ? tweetList.size() : 1;
    }

    public void setTweetList(List<Tweet> tweets) {
        tweetList = tweets;
        notifyDataSetChanged();
    }
}
