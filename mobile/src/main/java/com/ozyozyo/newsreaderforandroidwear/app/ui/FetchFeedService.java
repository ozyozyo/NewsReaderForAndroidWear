package com.ozyozyo.newsreaderforandroidwear.app.ui;

import android.util.Log;
import android.util.Xml;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.ozyozyo.newsreaderforandroidwear.app.event.CustomObject;
import com.ozyozyo.newsreaderforandroidwear.feed.entity.Feed;

import org.xmlpull.v1.XmlPullParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import pl.tajchert.buswear.EventBus;

public class FetchFeedService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        EventBus.getDefault().postRemote(new CustomObject(createFeed()), this);
    }

    public ArrayList<Feed> createFeed() {
        ArrayList<Feed> result = new ArrayList<>();
        result = doGet(result, "http://headlines.yahoo.co.jp/rss/zdn_mkt-dom.xml");
        result = doGet(result, "http://rss.dailynews.yahoo.co.jp/fc/rss.xml");
        result = doGet(result, "http://b.hatena.ne.jp/hotentry/it.rss");
        return result;
    }

    // こぴーあんどぺぺぺぺ
    private ArrayList<Feed> doGet(ArrayList<Feed> result, String string) {
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
