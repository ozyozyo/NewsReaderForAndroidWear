package com.ozyozyo.newsreaderforandroidwear.app.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ozyozyo.newsreaderforandroidwear.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebViewFragment extends Fragment {

    private static final String KEY_URL = "keyUrl";

    @InjectView(R.id.web_view) WebView mWebView;

    public static WebViewFragment getInstance(String s) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, s);

        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(bundle);

        return webViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, null);

        ButterKnife.inject(this, view);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                getActivity().setProgress(progress * 1000);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });

        mWebView.loadUrl(getArguments().getString(KEY_URL));
        return mWebView;
    }

    @Override
    public void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
}
