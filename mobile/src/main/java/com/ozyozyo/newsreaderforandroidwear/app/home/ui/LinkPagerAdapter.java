package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ozyozyo.newsreaderforandroidwear.app.home.event.DeleteEvent;

import java.util.ArrayList;

import pl.tajchert.buswear.EventBus;

public class LinkPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private final ArrayList<String> mLinkList;

    public LinkPagerAdapter(FragmentManager fm, ArrayList<String> linkList) {
        super(fm);
        mLinkList = linkList;
    }

    @Override
    public LinkWebViewFragment getItem(int position) {
        return LinkWebViewFragment.getInstance(mLinkList.get(position));
    }

    @Override
    public int getCount() {
        return mLinkList.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        EventBus.getDefault().postLocal(new DeleteEvent(mLinkList.get(position)));
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
