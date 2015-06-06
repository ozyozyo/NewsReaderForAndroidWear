package com.ozyozyo.newsreaderforandroidwear.util;

import android.content.AsyncTaskLoader;
import android.content.Context;

public abstract class AbstractLoader<T> extends AsyncTaskLoader<T> {
    private T mResult;

    public AbstractLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            mResult = null;
            return;
        }

        mResult = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        mResult = null;
        onStopLoading();
    }

}
