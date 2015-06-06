package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ozyozyo.newsreaderforandroidwear.R;

import java.util.ArrayList;

public class LinkAdapter extends BaseAdapter {
    private final Context mContext;
    private ArrayList<String> mLinkArray;

    public LinkAdapter(Context context, ArrayList<String> linkArray) {
        mContext = context;
        mLinkArray = linkArray;
    }
    
    @Override
    public int getCount() {
        return mLinkArray.size();
    }

    @Override
    public String getItem(int i) {
        return mLinkArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_url, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.url);
        textView.setText(getItem(i));
        return view;
    }
}
