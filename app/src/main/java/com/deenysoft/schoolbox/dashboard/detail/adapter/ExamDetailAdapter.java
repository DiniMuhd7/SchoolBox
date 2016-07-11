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
import com.deenysoft.schoolbox.dashboard.model.ExamBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 26/06/16.
 */
public class ExamDetailAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    List<ExamBoxItem> mExamBoxItemList;

    public ExamDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateExamBoxItem();
    }


    public void updateExamBoxItem() {
        mExamBoxItemList = SchoolBoxDBManager.getInstance().getExamItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.exam_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.examTitle);
        TextView txtExamNo = (TextView) view.findViewById(R.id.examNo);
        TextView txtExamVenue = (TextView) view.findViewById(R.id.examVenue);
        TextView txtExamDate = (TextView) view.findViewById(R.id.examDate);
        TextView txtExamTime = (TextView) view.findViewById(R.id.examTime);
        TextView txtExamStatus = (TextView) view.findViewById(R.id.examStatus);

        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("exam_title"));
        String mExamNo = cursor.getString(cursor.getColumnIndexOrThrow("exam_no"));
        String mExamVenue = cursor.getString(cursor.getColumnIndexOrThrow("exam_venue"));
        String mExamDate = cursor.getString(cursor.getColumnIndexOrThrow("exam_date"));
        String mExamTime = cursor.getString(cursor.getColumnIndexOrThrow("exam_time"));
        String mExamStatus = cursor.getString(cursor.getColumnIndexOrThrow("exam_status"));
        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtExamNo.setText(mExamNo);
        txtExamVenue.setText(mExamVenue);
        txtExamDate.setText(mExamDate);
        txtExamTime.setText(mExamTime);
        txtExamStatus.setText(mExamStatus);
    }

}
