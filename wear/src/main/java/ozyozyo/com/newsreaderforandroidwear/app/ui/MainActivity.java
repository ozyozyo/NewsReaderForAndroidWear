package ozyozyo.com.newsreaderforandroidwear.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ozyozyo.com.newsreaderforandroidwear.R;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    @InjectView(R.id.feed)
    WearableListView mFeedListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(mLayoutInflatedListener);
    }

    private WatchViewStub.OnLayoutInflatedListener mLayoutInflatedListener = new WatchViewStub.OnLayoutInflatedListener() {
        @Override
        public void onLayoutInflated(WatchViewStub stub) {
            ButterKnife.inject(MainActivity.this, stub);
            mFeedListView.setAdapter(new FeedAdapter(MainActivity.this));
            mFeedListView.setClickListener(MainActivity.this);
        }
    };

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
