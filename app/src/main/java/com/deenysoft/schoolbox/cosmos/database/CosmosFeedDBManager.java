package com.deenysoft.schoolbox.cosmos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;
import com.deenysoft.schoolbox.cosmos.model.CosmosFeedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosFeedDBManager {

    private static SQLiteDatabase rssFeedDB;
    private static CosmosFeedDatabase rssFeedDBCreator;
    private static CosmosFeedDBManager instance;

    public static CosmosFeedDBManager getInstance() {
        if( instance == null ) {
            synchronized (CosmosFeedDBManager.class) {
                if (instance == null) {
                    instance = new CosmosFeedDBManager();
                }
            }
        }
        return instance;
    }

    public CosmosFeedDBManager() {
        rssFeedDBCreator = new CosmosFeedDatabase(CosmosApplication.getContext());
        open();
    }

    public void open() throws android.database.SQLException {
        rssFeedDB = rssFeedDBCreator.getWritableDatabase();
    }

    public static void release() {
        close();
    }

    public static void close() {
        if( rssFeedDB != null) {
            rssFeedDB.close();
            rssFeedDB = null;
        }
        if(rssFeedDBCreator != null) {
            rssFeedDBCreator.close();
            rssFeedDBCreator = null;
        }
    }

    public long addFeedItem(CosmosFeedItem feedItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(CosmosFeedDBConstants.RSS_FEED.FEED_TITLE, feedItem.getTitle());
        values.put(CosmosFeedDBConstants.RSS_FEED.FEED_DESCRIPTION, feedItem.getDescription());
        values.put(CosmosFeedDBConstants.RSS_FEED.FEED_URL, feedItem.getHyperlink());
        values.put(CosmosFeedDBConstants.RSS_FEED.FEED_DATE, feedItem.getDate());

        try {
            insertId = rssFeedDB.insertOrThrow(CosmosFeedDBConstants.RSS_FEED_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }

    public List<CosmosFeedItem> getFeeds() {
        Cursor cursor = rssFeedDB.query(CosmosFeedDBConstants.RSS_FEED_TABLE,
                null, null, null, null, null, CosmosFeedDBConstants.RSS_FEED.FEED_DATE + " DESC");

        List<CosmosFeedItem> feedItemList = new ArrayList<CosmosFeedItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    CosmosFeedItem feedItem = new CosmosFeedItem();
                    String title = cursor.getString(cursor.getColumnIndex(CosmosFeedDBConstants.RSS_FEED.FEED_TITLE));
                    String description = cursor.getString(cursor.getColumnIndex(CosmosFeedDBConstants.RSS_FEED.FEED_DESCRIPTION));
                    String url = cursor.getString(cursor.getColumnIndex(CosmosFeedDBConstants.RSS_FEED.FEED_URL));
                    long timeInMillis = cursor.getLong(cursor.getColumnIndex(CosmosFeedDBConstants.RSS_FEED.FEED_DATE));

                    feedItem.setTitle(title);
                    feedItem.setDescription(description);
                    feedItem.setHyperlink(url);
                    feedItem.setDate(timeInMillis);
                    feedItemList.add(feedItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return feedItemList;

    }

    public long getLatestFeedPublishedDate() {
        Cursor cursor = rssFeedDB.query(CosmosFeedDBConstants.RSS_FEED_TABLE,
                null, null, null, null, null, CosmosFeedDBConstants.RSS_FEED.FEED_DATE+" DESC LIMIT 1");

        long publishedDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                publishedDate = cursor.getLong(cursor.getColumnIndex(CosmosFeedDBConstants.RSS_FEED.FEED_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return publishedDate;
    }
}
