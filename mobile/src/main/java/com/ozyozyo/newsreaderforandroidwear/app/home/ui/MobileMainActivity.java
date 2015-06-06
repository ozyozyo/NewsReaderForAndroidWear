package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ozyozyo.newsreaderforandroidwear.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.tajchert.buswear.EventBus;

public class MobileMainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @InjectView(R.id.list_view) ListView mListView;

    private ArrayList<String> mLinkArray;
    private LinkAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        EventBus.getDefault().register(this);

        mLinkArray = new ArrayList<>();
        mAdapter = new LinkAdapter(this, mLinkArray);
        mListView.setAdapter(mAdapter);
    }

    @OnClick(R.id.show_button)
    public void submit(View view) {
        Intent intent = new Intent(MobileMainActivity.this, SwipeViewActivity.class);
        intent.putExtra(SwipeViewActivity.KEY_URL_LIST, mLinkArray);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    // Event Receiver
    public void onEvent(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLinkArray.add(url);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
