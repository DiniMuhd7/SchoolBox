package com.deenysoft.schoolbox.cosmos.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.deenysoft.schoolbox.cosmos.database.CosmosFeedDBManager;
import com.deenysoft.schoolbox.cosmos.model.CosmosFeedItem;
import com.deenysoft.schoolbox.cosmos.receiver.UpdateNewFeedReceiver;

import java.util.List;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosFeedsLoader extends AsyncTaskLoader<List<CosmosFeedItem>> {

    /** List of feedItems **/
    List<CosmosFeedItem> mCosmosFeedItems;

    // The observer to notify the Loader when there is a new feed.
    private UpdateNewFeedReceiver mUpdateNewFeedReceiver;

    private final String TAG = "CosmosFeedsLoader";

    public CosmosFeedsLoader(Context context) {
        super(context);
    }

    @Override
    public List<CosmosFeedItem> loadInBackground() {
        Log.d(TAG, "loadInBackground()");
        mCosmosFeedItems = CosmosFeedDBManager.getInstance().getFeeds();
        return mCosmosFeedItems;
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
        Log.d(TAG, "forceLoad()");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG,"onStartLoading()");

        if(mCosmosFeedItems != null) {
            deliverResult(mCosmosFeedItems);
        }

        if (mUpdateNewFeedReceiver == null) {
            mUpdateNewFeedReceiver = new UpdateNewFeedReceiver(this);
        }

        if (takeContentChanged() || mCosmosFeedItems == null) {
            // When the updatenewfeed receiver receives a new feeds notification, it will call
            // onContentChanged() on the Loader, which will cause the next call to
            // takeContentChanged() to return true. So force a new load.
            Log.d(TAG,"A content change has been detected, so force load!");
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(TAG, "onStopLoading()");
        cancelLoad();
    }

    @Override
    protected void onReset() {
        Log.d(TAG, "onReset()");
        onStopLoading();

        // The Loader is being reset, so we should stop listening for new feeds broadcast.
        if (mUpdateNewFeedReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mUpdateNewFeedReceiver);
            mUpdateNewFeedReceiver = null;
        }

    }

    @Override
    public void onCanceled(List<CosmosFeedItem> apps) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(apps);
        Log.d(TAG,"onCanceled()");
    }


}
