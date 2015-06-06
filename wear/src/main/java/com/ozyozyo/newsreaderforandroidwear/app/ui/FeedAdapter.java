package com.ozyozyo.newsreaderforandroidwear.app.ui;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ozyozyo.newsreaderforandroidwear.R;
import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;

import java.util.ArrayList;

public class FeedAdapter extends WearableListView.Adapter {
    private final Context mContext;
    private final ArrayList<Feed> mFeeds;

    public FeedAdapter(Context context, ArrayList<Feed> feeds) {
        mContext = context;
        mFeeds = feeds;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_feed_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        FeedViewHolder viewHolder = (FeedViewHolder) holder;
        TextView view = viewHolder.getTextView();
        view.setText(mFeeds.get(position).getTitle()); // FIXME
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }
}
