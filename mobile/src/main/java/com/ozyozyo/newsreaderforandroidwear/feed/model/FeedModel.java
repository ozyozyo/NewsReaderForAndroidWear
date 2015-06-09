package com.ozyozyo.newsreaderforandroidwear.feed.model;

import android.util.Log;
import android.util.Xml;

import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;

import org.xmlpull.v1.XmlPullParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class FeedModel {
    public static ArrayList<Feed> create() {
        ArrayList<Feed> result = new ArrayList<>();
        result = doGet(result, "http://headlines.yahoo.co.jp/rss/jct-dom.xml");
        result = doGet(result, "http://rss.dailynews.yahoo.co.jp/fc/rss.xml");
        result = doGet(result, "http://headlines.yahoo.co.jp/rss/zdn_mkt-dom.xml");
        result = doGet(result, "http://b.hatena.ne.jp/hotentry/it.rss");
        return result;
    }

    // こぴーあんどぺぺぺぺ
    private static ArrayList<Feed> doGet(ArrayList<Feed> result, String string) {
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

                if (eventType == XmlPullParser.START_TAG && "title".equals(xmlPullParser.getName())) {
                    title = xmlPullParser.nextText();
                }

                if (eventType == XmlPullParser.START_TAG && "link".equals(xmlPullParser.getName())) {
                    link = xmlPullParser.nextText();
                }
            }
        } catch (Exception e){
            Log.d("XmlPullParserSampleUrl", "Error");
        }
        return result;
    }
}
