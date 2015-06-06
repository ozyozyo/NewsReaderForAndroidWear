package com.ozyozyo.newsreaderforandroidwear.feed.model;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class FeedModel {
    private GoogleApiClient mGoogleApiClient;

    public FeedModel(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    public void fetchFeed(ResultCallback<DataApi.DataItemResult> callback) {
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create("/load");
        PutDataRequest request = dataMapRequest.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, request);
        pendingResult.setResultCallback(callback);
    }
}
