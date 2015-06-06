package com.ozyozyo.newsreaderforandroidwear.feed.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {
    private final String title;
    private final String text;

    public Feed(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Feed(Parcel source) {
        this.title = source.readString();
        this.text = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(text);
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
