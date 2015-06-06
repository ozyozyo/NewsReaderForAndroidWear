package com.ozyozyo.newsreaderforandroidwear.app.home.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;

import java.util.ArrayList;


public class CustomObject implements Parcelable {
    private ArrayList<Feed> feeds;

    public CustomObject(ArrayList<Feed> feeds) {
        this.feeds = feeds;
    }

    public ArrayList<Feed> getFeeds() {
        return feeds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(feeds.toArray(new Feed[0]), 0);
    }

    public CustomObject(Parcel in){
        ArrayList<Feed> tmp = new ArrayList<>();
        for (Parcelable p : in.readParcelableArray(Feed.class.getClassLoader())) {
            tmp.add((Feed) p);
        }
        this.feeds = tmp;
    }

    @Override
    public String toString() {
        return this.feeds.toString();
    }

}
