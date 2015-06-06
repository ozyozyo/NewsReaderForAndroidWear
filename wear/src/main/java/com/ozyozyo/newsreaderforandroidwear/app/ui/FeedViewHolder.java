package com.ozyozyo.newsreaderforandroidwear.app.ui;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.ozyozyo.newsreaderforandroidwear.R;

public class FeedViewHolder extends WearableListView.ViewHolder {
    private final TextView textView;

    public FeedViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.text);
    }

    public TextView getTextView() {
        return textView;
    }
}
