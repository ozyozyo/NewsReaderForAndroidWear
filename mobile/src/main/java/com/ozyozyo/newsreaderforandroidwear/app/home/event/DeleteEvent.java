package com.ozyozyo.newsreaderforandroidwear.app.home.event;

public class DeleteEvent {
    private final String mUrl;

    public DeleteEvent(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}
