package com.deenysoft.schoolbox.cosmos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosFeedDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "rssfeeds.db";

    public CosmosFeedDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists " + CosmosFeedDBConstants.RSS_FEED_TABLE +
                "(" + CosmosFeedDBConstants.RSS_FEED.FEED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CosmosFeedDBConstants.RSS_FEED.FEED_TITLE + " TEXT," +
                CosmosFeedDBConstants.RSS_FEED.FEED_DESCRIPTION + " TEXT," +
                CosmosFeedDBConstants.RSS_FEED.FEED_URL + " TEXT," +
                CosmosFeedDBConstants.RSS_FEED.FEED_DATE + " INTEGER, " +
                "UNIQUE ("+CosmosFeedDBConstants.RSS_FEED.FEED_TITLE+","+
                CosmosFeedDBConstants.RSS_FEED.FEED_DESCRIPTION+","+
                CosmosFeedDBConstants.RSS_FEED.FEED_URL+")"+
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CosmosFeedDBConstants.RSS_FEED_TABLE);
    }
}
