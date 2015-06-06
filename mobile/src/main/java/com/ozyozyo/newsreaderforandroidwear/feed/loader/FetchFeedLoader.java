package com.ozyozyo.newsreaderforandroidwear.feed.loader;

import android.content.Context;

import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;
import com.ozyozyo.newsreaderforandroidwear.util.AbstractLoader;

import java.util.ArrayList;
import java.util.List;

public class FetchFeedLoader extends AbstractLoader<List<Feed>> {
    public FetchFeedLoader(Context context) {
        super(context);
    }

    @Override
    public List<Feed> loadInBackground() {
        ArrayList<Feed> feeds = new ArrayList<>();
        feeds.add(new Feed("たいとる", "にゃーん"));
        feeds.add(new Feed("たいとる1", "にゃーん1"));
        feeds.add(new Feed("たいとる2", "にゃーん2"));
        feeds.add(new Feed("たいとる3", "にゃーん3"));
        feeds.add(new Feed("たいとる4", "にゃーん4"));
        return feeds;
    }
}
