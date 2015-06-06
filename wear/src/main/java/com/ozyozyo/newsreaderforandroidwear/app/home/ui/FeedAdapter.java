package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.ozyozyo.newsreaderforandroidwear.R;
import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;
import com.ozyozyo.newsreaderforandroidwear.feed.model.FeedModel;

import java.util.ArrayList;

public class FeedAdapter extends WearableListView.Adapter {
    private final Context mContext;
    private final ArrayList<Feed> mFeeds;
    private final GoogleApiClient mClient;

    public FeedAdapter(Context context, GoogleApiClient client, ArrayList<Feed> feeds) {
        mContext = context;
        mClient = client;
        mFeeds = feeds;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_feed_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, final int position) {
        FeedViewHolder viewHolder = (FeedViewHolder) holder;
        TextView view = viewHolder.getTextView();
        view.setText(mFeeds.get(position).getTitle());
        viewHolder.getBackgroundView().setBackgroundColor(
                position % 2 == 0
                        ? mContext.getResources().getColor(R.color.light_blue)
                        : Color.WHITE
        );
        holder.itemView.setTag(position);
        viewHolder.getBackgroundView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedModel.stockFeed(mClient, mFeeds.get(position));
                Toast.makeText(mContext, "stored", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }
}
