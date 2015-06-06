package com.ozyozyo.newsreaderforandroidwear.app.ui;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class FetchFeedService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        // めっせーじきたら、loadしてwearかえしてあげたい
        Log.e("MyService", "onMessageReceived");
        Log.e("MyService", messageEvent.getPath());
        Log.e("MyService", new String(messageEvent.getData()));
    }
}
