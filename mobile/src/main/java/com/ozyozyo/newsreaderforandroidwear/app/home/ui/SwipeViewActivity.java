package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.ozyozyo.newsreaderforandroidwear.R;

import java.util.ArrayList;

public class SwipeViewActivity extends ActionBarActivity {
    public static final String KEY_URL_LIST = "urlList";
    private ArrayList<String> mLinkList;
    private LinkPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swipe_view);
        Intent intent = getIntent();
        mLinkList = intent.getExtras().getStringArrayList(KEY_URL_LIST);
        mPagerAdapter = new LinkPagerAdapter(getSupportFragmentManager(), mLinkList);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(mPagerAdapter);
    }
}
