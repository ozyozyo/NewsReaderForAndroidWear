package com.ozyozyo.newsreaderforandroidwear.app.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;

import java.util.ArrayList;


public class CustomObject implements Parcelable {
    private String name;
    private ArrayList<Feed> feeds;

    public CustomObject(String name, ArrayList<Feed> feeds) {
        this.name = name;
        this.feeds = feeds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(name);
        dest.writeParcelableArray(feeds.toArray(new Feed[0]), 0);
    }

    public CustomObject(Parcel in){
        this.name = in.readString();
        ArrayList<Feed> tmp = new ArrayList<>();
        for (Parcelable p : in.readParcelableArray(Feed.class.getClassLoader())) {
            tmp.add((Feed) p);
        }
        this.feeds = tmp;
    }

    @Override
    public String toString() {
        return "CustomObject{" +
                "name='" + name + '\'' +
                '}';
    }

}
