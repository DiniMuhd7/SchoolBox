package com.deenysoft.schoolbox.cosmos.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deenysoft.schoolbox.cosmos.services.CosmosService;
import com.deenysoft.schoolbox.cosmos.util.CosmosPreferenceManager;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosApplication extends Application {

    private static Context applicationContext;
    private SharedPreferences sharedPreferences;

    // AppController variables declaration
    public static final String TAG = CosmosApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static CosmosApplication mInstance;

    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        mInstance = this;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        CosmosPreferenceManager.initializePreferenceManager(sharedPreferences);

        //Start the sync service only once when application is coming alive first time.
        Intent msgIntent = new Intent(this, CosmosService.class);
        startService(msgIntent);

    }

    public static Context getContext() {
        return applicationContext;
    }

    public static synchronized CosmosApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
