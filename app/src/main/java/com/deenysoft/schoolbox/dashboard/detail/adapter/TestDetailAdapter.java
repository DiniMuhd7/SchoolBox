package com.deenysoft.schoolbox.dashboard.detail.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDBManager;
import com.deenysoft.schoolbox.dashboard.model.TestBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 26/06/16.
 */
public class TestDetailAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    List<TestBoxItem> mTestBoxItemList;

    public TestDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateTestBoxItem();
    }


    public void updateTestBoxItem() {
        mTestBoxItemList = SchoolBoxDBManager.getInstance().getTestItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.test_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.testTitle);
        TextView txtTestNo = (TextView) view.findViewById(R.id.testNo);
        TextView txtTestVenue = (TextView) view.findViewById(R.id.testVenue);
        TextView txtTestDate = (TextView) view.findViewById(R.id.testDate);
        TextView txtTestTime = (TextView) view.findViewById(R.id.testTime);
        TextView txtTestStatus = (TextView) view.findViewById(R.id.testStatus);

        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("test_title"));
        String mTestNo = cursor.getString(cursor.getColumnIndexOrThrow("test_no"));
        String mTestVenue = cursor.getString(cursor.getColumnIndexOrThrow("test_venue"));
        String mTestDate = cursor.getString(cursor.getColumnIndexOrThrow("test_date"));
        String mTestTime = cursor.getString(cursor.getColumnIndexOrThrow("test_time"));
        String mTestStatus = cursor.getString(cursor.getColumnIndexOrThrow("test_status"));
        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtTestNo.setText(mTestNo);
        txtTestVenue.setText(mTestVenue);
        txtTestDate.setText(mTestDate);
        txtTestTime.setText(mTestTime);
        txtTestStatus.setText(mTestStatus);

    }

}
