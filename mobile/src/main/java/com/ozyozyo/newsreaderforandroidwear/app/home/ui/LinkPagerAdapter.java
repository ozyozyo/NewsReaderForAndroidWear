package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class LinkPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> mLinkList;

    public LinkPagerAdapter(FragmentManager fm, ArrayList<String> linkList) {
        super(fm);
        mLinkList = linkList;
    }

    @Override
    public Fragment getItem(int position) {
        return WebViewFragment.getInstance(mLinkList.get(position));
    }

    @Override
    public int getCount() {
        return mLinkList.size();
    }
}
