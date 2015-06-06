package com.ozyozyo.newsreaderforandroidwear.util;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;

public abstract class AbstractLoaderModel<E> implements LoaderManager.LoaderCallbacks<E> {
    private final Context mContext;

    protected AbstractLoaderModel(Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    protected void initLoader(int id, Bundle args) {
        LoaderManager manager = ((Activity) mContext).getLoaderManager();
        manager.initLoader(id, args, this);
    }

    /**
     * Destroys the {@link android.content.Loader} instance of this {@link android.content.Context}.
     * @param id the loader id
     */
    protected void destroyLoader(int id) {
        LoaderManager manager = ((Activity) mContext).getLoaderManager();
        manager.destroyLoader(id);
    }
}
