package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.ozyozyo.newsreaderforandroidwear.R;

public class FeedViewHolder extends WearableListView.ViewHolder {
    private final TextView textView;
    private final View backgroundView;

    public FeedViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.text);
        backgroundView = itemView.findViewById(R.id.background);
    }

    public TextView getTextView() {
        return textView;
    }

    public View getBackgroundView() {
        return backgroundView;
    }
}
