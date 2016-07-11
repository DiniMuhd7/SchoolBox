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
import com.deenysoft.schoolbox.dashboard.model.AssignmentBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 26/06/16.
 */
public class AssignmentDetailAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    List<AssignmentBoxItem> mAssignmentBoxItemList;

    public AssignmentDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateAssignmentBoxItem();
    }


    public void updateAssignmentBoxItem() {
        mAssignmentBoxItemList = SchoolBoxDBManager.getInstance().getAssignmentItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.assignment_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.assignTitle);
        TextView txtAssignNo = (TextView) view.findViewById(R.id.assignNo);
        TextView txtAssignSubVenue = (TextView) view.findViewById(R.id.assignSubVenue);
        TextView txtAssignDueDate = (TextView) view.findViewById(R.id.assignDueDate);
        TextView txtAssignStatus = (TextView) view.findViewById(R.id.assignStatus);

        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("assignment_title"));
        String mAssignNo = cursor.getString(cursor.getColumnIndexOrThrow("assignment_no"));
        String mAssignSubVenue = cursor.getString(cursor.getColumnIndexOrThrow("assignment_submission_venue"));
        String mAssignDueDate = cursor.getString(cursor.getColumnIndexOrThrow("assignment_due_date"));
        String mAssignStatus = cursor.getString(cursor.getColumnIndexOrThrow("assignment_status"));
        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtAssignNo.setText(mAssignNo);
        txtAssignSubVenue.setText(mAssignSubVenue);
        txtAssignDueDate.setText(mAssignDueDate);
        txtAssignStatus.setText(mAssignStatus);
    }


}
