package ozyozyo.com.newsreaderforandroidwear.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ozyozyo.com.newsreaderforandroidwear.R;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    private static final int SCROLL_INTERVAL_Y = 1;
    private static final long TIMER_INTERVAL = 100;

    @InjectView(R.id.feed) WearableListView mFeedListView;
    @InjectView(R.id.change_status_button) Button mButton;

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

        mTimer = new Timer();
        mHandler = new Handler();
        mAdapter = new FeedAdapter(MainActivity.this);
    }

    private WatchViewStub.OnLayoutInflatedListener mLayoutInflatedListener = new WatchViewStub.OnLayoutInflatedListener() {
        @Override
        public void onLayoutInflated(WatchViewStub stub) {
            ButterKnife.inject(MainActivity.this, stub);
            mFeedListView.setAdapter(mAdapter);
            mFeedListView.setClickListener(MainActivity.this);
            mFeedListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    mButton.setVisibility(newState == RecyclerView.SCROLL_STATE_IDLE ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopScroll();
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
    }

    private void stopScroll() {
        mTimer.cancel();
    }
}
