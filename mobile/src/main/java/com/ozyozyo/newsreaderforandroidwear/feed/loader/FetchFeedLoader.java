package com.ozyozyo.newsreaderforandroidwear.feed.loader;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;
import com.ozyozyo.newsreaderforandroidwear.util.AbstractLoader;

import org.xmlpull.v1.XmlPullParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FetchFeedLoader extends AbstractLoader<List<Feed>> {
    public FetchFeedLoader(Context context) {
        super(context);
    }

    @Override
    public List<Feed> loadInBackground() {
        return doGet("http://headlines.yahoo.co.jp/rss/zdn_mkt-dom.xml");
    }

    public List<Feed> doGet(String string)
    {
        ArrayList<Feed> result = new ArrayList<>();
        try{
            XmlPullParser xmlPullParser = Xml.newPullParser();

            URL url = new URL(string);
            URLConnection connection = url.openConnection();
            xmlPullParser.setInput(connection.getInputStream(), "UTF-8");

            boolean isInItem = false;

            int eventType;
            String title = null;
            String link = null;

            while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "item".equals(xmlPullParser.getName())) {
                    isInItem = true;

                    title = null;
                    link = null;
                }
                if (eventType == XmlPullParser.END_TAG && "item".equals(xmlPullParser.getName())) {
                    result.add(new Feed(title, link));
                    isInItem = false;
                }

                if (!isInItem) continue;

                if (isInItem && eventType == XmlPullParser.START_TAG && "title".equals(xmlPullParser.getName())) {
                    title = xmlPullParser.nextText();
                }

                if (isInItem && eventType == XmlPullParser.START_TAG && "link".equals(xmlPullParser.getName())) {
                    link = xmlPullParser.nextText();
                }
            }
        } catch (Exception e){
            Log.d("XmlPullParserSampleUrl", "Error");
        }
        return result;
    }
}
