package com.deenysoft.schoolbox.cosmos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.deenysoft.schoolbox.cosmos.loader.CosmosFeedsLoader;

/**
 * Created by shamsadam on 28/03/16.
 */
public class UpdateNewFeedReceiver extends BroadcastReceiver {

    private CosmosFeedsLoader mFeedsLoader;

    public static final String ACTION_UPDATE_NEW_FEEDS = "action_NEW_FEEDS";

    public UpdateNewFeedReceiver(CosmosFeedsLoader loader) {
        mFeedsLoader = loader;
        IntentFilter filter = new IntentFilter(ACTION_UPDATE_NEW_FEEDS);
        LocalBroadcastManager.getInstance(mFeedsLoader.getContext()).registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mFeedsLoader.onContentChanged();
    }
}
