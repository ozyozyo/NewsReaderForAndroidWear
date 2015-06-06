package com.ozyozyo.newsreaderforandroidwear.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.ozyozyo.newsreaderforandroidwear.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WearMainActivity extends Activity implements WearableListView.ClickListener,
        WearableListView.OnScrollListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DataApi.DataListener {

    private static final int SCROLL_INTERVAL_Y = 1;
    private static final long TIMER_INTERVAL = 1500;

    @InjectView(R.id.feed) WearableListView mFeedListView;
    @InjectView(R.id.change_status_button) CircledImageView mButton;

    private FeedAdapter mAdapter;
    private Timer mTimer;
    private Handler mHandler;

    private int mPreviousY = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(mLayoutInflatedListener);

        mHandler = new Handler();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            Wearable.DataApi.removeListener(mGoogleApiClient, this);
        }
    }

    private WatchViewStub.OnLayoutInflatedListener mLayoutInflatedListener = new WatchViewStub.OnLayoutInflatedListener() {
        @Override
        public void onLayoutInflated(WatchViewStub stub) {
            ButterKnife.inject(WearMainActivity.this, stub);

            mFeedListView.setClickListener(WearMainActivity.this);
            mFeedListView.addOnScrollListener(WearMainActivity.this);

            mButton.setVisibility(View.GONE);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTimer == null) {
                        startScroll();
                    } else {
                        stopScroll();
                    }
                }
            });
        }
    };

    private void startScroll() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        mPreviousY += SCROLL_INTERVAL_Y;
                        if (mPreviousY > mAdapter.getItemCount()) {
                            stopScroll();
                            return;
                        }
                        mFeedListView.smoothScrollToPosition(mPreviousY);
                    }
                });
            }
        }, TIMER_INTERVAL, TIMER_INTERVAL);
        mButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_media_pause, null));
    }

    private void stopScroll() {
        mTimer.cancel();
        mTimer = null;

        mButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_media_next, null));
    }

    // WearableListView.ClickListener
    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    // WearableListView.OnScrollListener
    @Override
    public void onScroll(int i) {

    }

    @Override
    public void onAbsoluteScrollChange(int i) {

    }

    @Override
    public void onScrollStateChanged(int i) {

    }

    @Override
    public void onCentralPositionChanged(int i) {
        mPreviousY = i;
    }

    // GoogleApiClient.ConnectionCallbacks
    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    // GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), R.string.error_connection_failed, Toast.LENGTH_SHORT).show();
    }

    // DataApi.DataListener
    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        if (mAdapter != null) return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new FeedAdapter(WearMainActivity.this); //TODO
                mFeedListView.setAdapter(mAdapter);
                mButton.setVisibility(View.VISIBLE);
                startScroll();
            }
        });
    }
}
