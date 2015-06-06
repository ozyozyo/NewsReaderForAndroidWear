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
        dest.writeList(feeds);
    }

    public CustomObject(Parcel in){
        this.name = in.readString();
        this.feeds = in.readArrayList(Feed.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "CustomObject{" +
                "name='" + name + '\'' +
                '}';
    }

}
