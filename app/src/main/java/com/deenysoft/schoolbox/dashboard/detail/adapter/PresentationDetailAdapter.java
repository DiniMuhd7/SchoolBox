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
import com.deenysoft.schoolbox.dashboard.model.PresentationBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 26/06/16.
 */
public class PresentationDetailAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    List<PresentationBoxItem> mPresentationBoxItemList;

    public PresentationDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updatePresentationBoxItem();
    }


    public void updatePresentationBoxItem() {
        mPresentationBoxItemList = SchoolBoxDBManager.getInstance().getPresentationItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.presentation_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.presentTitle);
        TextView txtPresentNo = (TextView) view.findViewById(R.id.presentNo);
        TextView txtPresentVenue = (TextView) view.findViewById(R.id.presentVenue);
        TextView txtPresentDate = (TextView) view.findViewById(R.id.presentDate);
        TextView txtPresentStatus = (TextView) view.findViewById(R.id.presentStatus);

        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("presentation_title"));
        String mPresentNo = cursor.getString(cursor.getColumnIndexOrThrow("presentation_no"));
        String mPresentVenue = cursor.getString(cursor.getColumnIndexOrThrow("presentation_venue"));
        String mPresentDate = cursor.getString(cursor.getColumnIndexOrThrow("presentation_date"));
        String mPresentStatus = cursor.getString(cursor.getColumnIndexOrThrow("presentation_status"));
        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtPresentNo.setText(mPresentNo);
        txtPresentVenue.setText(mPresentVenue);
        txtPresentDate.setText(mPresentDate);
        txtPresentStatus.setText(mPresentStatus);
    }


}
