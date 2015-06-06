package com.ozyozyo.newsreaderforandroidwear.app.home.ui;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.ozyozyo.newsreaderforandroidwear.R;
import com.ozyozyo.newsreaderforandroidwear.app.home.event.CustomObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.tajchert.buswear.EventBus;

public class WearMainActivity extends Activity implements WearableListView.OnScrollListener {

    private static final int SCROLL_INTERVAL_Y = 1;
    private static final long TIMER_INTERVAL = 1500;

    @InjectView(R.id.feed) WearableListView mFeedListView;
    @InjectView(R.id.change_status_button) CircledImageView mButton;

    private FeedAdapter mAdapter;
    private Timer mTimer;
    private Handler mHandler;

    private int mPreviousY = 0;
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(mLayoutInflatedListener);

        mHandler = new Handler();
        EventBus.getDefault().register(this);

        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }
                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                    }
                })
                .addApi(Wearable.API)
                .build();
        mClient.connect();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String message = "Hello world";
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mClient).await();
                for (Node node : nodes.getNodes()) {
                    Wearable.MessageApi.sendMessage(mClient, node.getId(), "/hello", message.getBytes()).await();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        mClient.disconnect();

        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private WatchViewStub.OnLayoutInflatedListener mLayoutInflatedListener = new WatchViewStub.OnLayoutInflatedListener() {
        @Override
        public void onLayoutInflated(WatchViewStub stub) {
            ButterKnife.inject(WearMainActivity.this, stub);

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


    // Event Receiver
    public void onEvent(final CustomObject object) {
        if (mAdapter != null) return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new FeedAdapter(WearMainActivity.this, object.getFeeds()); //TODO
                mFeedListView.setAdapter(mAdapter);
                mButton.setVisibility(View.VISIBLE);
                startScroll();
            }
        });
    }
}
