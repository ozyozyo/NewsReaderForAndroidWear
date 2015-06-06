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

import com.ozyozyo.newsreaderforandroidwear.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity implements WearableListView.ClickListener, WearableListView.OnScrollListener {

    private static final int SCROLL_INTERVAL_Y = 1;
    private static final long TIMER_INTERVAL = 1500;

    @InjectView(R.id.feed) WearableListView mFeedListView;
    @InjectView(R.id.change_status_button) CircledImageView mButton;

    private FeedAdapter mAdapter;
    private Timer mTimer;
    private Handler mHandler;

    private int mPreviousY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(mLayoutInflatedListener);

        mHandler = new Handler();
        mAdapter = new FeedAdapter(MainActivity.this);
    }

    private WatchViewStub.OnLayoutInflatedListener mLayoutInflatedListener = new WatchViewStub.OnLayoutInflatedListener() {
        @Override
        public void onLayoutInflated(WatchViewStub stub) {
            ButterKnife.inject(MainActivity.this, stub);
            mFeedListView.setAdapter(mAdapter);
            mFeedListView.setClickListener(MainActivity.this);
            mFeedListView.addOnScrollListener(MainActivity.this);

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

            startScroll();
        }
    };

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

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
}
