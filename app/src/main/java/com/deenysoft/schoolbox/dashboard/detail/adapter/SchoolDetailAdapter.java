package com.deenysoft.schoolbox.dashboard.detail.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;
import com.deenysoft.schoolbox.dashboard.adapter.SchoolDashAdapter;
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDBManager;
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDatabase;
import com.deenysoft.schoolbox.dashboard.model.SchoolBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 25/06/16.
 */
public class SchoolDetailAdapter extends CursorAdapter{

    private LayoutInflater mInflater;
    List<SchoolBoxItem> mSchoolBoxItemList;

    public SchoolDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateSchoolBoxItem();
    }


    public void updateSchoolBoxItem() {
        mSchoolBoxItemList = SchoolBoxDBManager.getInstance().getSchoolItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.school_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.schoolTitle);
        TextView txtSchoolNo = (TextView) view.findViewById(R.id.schoolNo);
        TextView txtMajor = (TextView) view.findViewById(R.id.schoolMajor);
        TextView txtStartDate = (TextView) view.findViewById(R.id.schoolStartDate);
        TextView txtEndDate = (TextView) view.findViewById(R.id.schoolEndDate);
        TextView txtLocation = (TextView) view.findViewById(R.id.schoolLocation);
        TextView txtStatus = (TextView) view.findViewById(R.id.schoolStatus);

        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("school_title"));
        String mSchoolNo = cursor.getString(cursor.getColumnIndexOrThrow("school_cardno"));
        String mMajor = cursor.getString(cursor.getColumnIndexOrThrow("school_major"));
        String mStartDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
        String mEndDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
        String mLocation = cursor.getString(cursor.getColumnIndexOrThrow("school_location"));
        String mStatus = cursor.getString(cursor.getColumnIndexOrThrow("status"));
        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtSchoolNo.setText(mSchoolNo);
        txtMajor.setText(mMajor);
        txtStartDate.setText(mStartDate);
        txtEndDate.setText(mEndDate);
        txtLocation.setText(mLocation);
        txtStatus.setText(mStatus);
    }



}
