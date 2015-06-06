package com.ozyozyo.newsreaderforandroidwear.app.receiver;

import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.ozyozyo.newsreaderforandroidwear.app.home.event.CustomObject;
import com.ozyozyo.newsreaderforandroidwear.feed.model.FeedModel;

import pl.tajchert.buswear.EventBus;

public class OzyozyoWearableListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        EventBus.getDefault().postRemote(new CustomObject(FeedModel.create()), this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);

        for (DataEvent event : dataEvents) {
            if (event.getType() != DataEvent.TYPE_CHANGED) continue;
            DataMap dataMap = DataMap.fromByteArray(event.getDataItem().getData());

            // TODO: ほんとはSQLiteにかきこみたい
            Log.e("hoge", dataMap.getString("link"));
            EventBus.getDefault().postLocal(dataMap.getString("link"));
        }
    }
}
