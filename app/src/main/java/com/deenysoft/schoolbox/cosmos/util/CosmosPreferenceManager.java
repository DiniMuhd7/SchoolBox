package com.deenysoft.schoolbox.cosmos.util;

import android.content.SharedPreferences;

import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosPreferenceManager {

    private static SharedPreferences preferences;
    private static CosmosPreferenceManager mInstance;

    private static final String PREFERENCES = "settings";
    public static final String DARK_THEME = "prefThemeColor";

    public static class PreferenceKeys {
        public static String LAST_PUBLISHED_DATE = "last_published_date";
        public static String LAST_FETCHED_TIME = "last_fetched_time";

    }

    // Additional Improvements
    public static CosmosPreferenceManager getInstance() {
        if (mInstance == null) {
            mInstance = new CosmosPreferenceManager();
        }
        return mInstance;
    }

    private CosmosPreferenceManager() {
        preferences = CosmosApplication.getContext().getSharedPreferences(PREFERENCES,0);
    }

    public static void initializePreferenceManager(SharedPreferences _preferences) {
        preferences = _preferences;
    }

    private void putBoolean(String name, boolean value) {
        preferences.edit().putBoolean(name, value).apply();
    }

    public boolean getUseDarkTheme() {
        return preferences.getBoolean(DARK_THEME, false);
    }

    public void setUseDarkTheme(boolean use) {
        putBoolean(DARK_THEME, use);
    }

    // -----------------------





    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public static void setLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }
}
