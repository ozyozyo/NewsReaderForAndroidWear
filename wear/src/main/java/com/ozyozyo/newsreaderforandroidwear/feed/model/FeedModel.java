package com.ozyozyo.newsreaderforandroidwear.feed.model;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;

public class FeedModel {
    public static void stockFeed(GoogleApiClient apiClient, Feed feed) {
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create("/stockdata");
        DataMap dataMap = dataMapRequest.getDataMap();

        dataMap.putString("title", feed.getTitle());
        dataMap.putString("link", feed.getText());

        PutDataRequest request = dataMapRequest.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(apiClient, request);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {
                Log.d("TAG", "onResult: " + dataItemResult.getStatus());
            }
        });
    }
}
