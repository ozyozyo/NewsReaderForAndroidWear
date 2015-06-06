package com.ozyozyo.newsreaderforandroidwear.app.ui;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ozyozyo.newsreaderforandroidwear.R;

public class FeedAdapter extends WearableListView.Adapter {
    private final Context mContext;

    public FeedAdapter(Context context) {
        mContext = context;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_feed_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        FeedViewHolder viewHolder = (FeedViewHolder) holder;
        TextView view = viewHolder.getTextView();
        view.setText(position + "aa"); // FIXME
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 500;
    }
}
