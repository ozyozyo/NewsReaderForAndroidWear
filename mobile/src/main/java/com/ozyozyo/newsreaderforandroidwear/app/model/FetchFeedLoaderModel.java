package com.ozyozyo.newsreaderforandroidwear.app.model;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.ozyozyo.newsreaderforandroidwear.app.event.CustomObject;
import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;
import com.ozyozyo.newsreaderforandroidwear.feed.loader.FetchFeedLoader;
import com.ozyozyo.newsreaderforandroidwear.util.AbstractLoaderModel;

import java.util.ArrayList;
import java.util.List;

import pl.tajchert.buswear.EventBus;

public class FetchFeedLoaderModel extends AbstractLoaderModel<List<Feed>> {
    private static final int LOADER_ID = 1;

    public FetchFeedLoaderModel(Context context) {
        super(context);
    }

    public void load() {
        initLoader(LOADER_ID, null);
    }

    @Override
    public Loader<List<Feed>> onCreateLoader(int i, Bundle bundle) {
        return new FetchFeedLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Feed>> loader, List<Feed> s) {
        ArrayList<Feed> arrayList = new ArrayList<>();
        for (Feed feed : s) {
            arrayList.add(feed);
        }
        EventBus.getDefault().postRemote(new CustomObject("tototo", arrayList), getContext());
    }

    @Override
    public void onLoaderReset(Loader<List<Feed>> loader) {

    }
}
