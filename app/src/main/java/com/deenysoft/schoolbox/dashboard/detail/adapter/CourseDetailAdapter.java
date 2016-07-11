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
import com.deenysoft.schoolbox.dashboard.model.CourseBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 26/06/16.
 */
public class CourseDetailAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    List<CourseBoxItem> mCourseBoxItemList;

    public CourseDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateCourseBoxItem();
    }


    public void updateCourseBoxItem() {
        mCourseBoxItemList = SchoolBoxDBManager.getInstance().getCourseItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.course_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.courseTitle);
        TextView txtCourseNo = (TextView) view.findViewById(R.id.courseNo);
        TextView txtInstructor = (TextView) view.findViewById(R.id.courseInstructor);
        TextView txtCourseDate = (TextView) view.findViewById(R.id.courseDate);
        TextView txtCourseTime = (TextView) view.findViewById(R.id.courseTime);
        TextView txtCourseVenue = (TextView) view.findViewById(R.id.courseVenue);
        TextView txtCourseStatus = (TextView) view.findViewById(R.id.courseStatus);

        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("course_title"));
        String mCourseNo = cursor.getString(cursor.getColumnIndexOrThrow("course_no"));
        String mInstructor = cursor.getString(cursor.getColumnIndexOrThrow("course_instructor"));
        String mCourseDate = cursor.getString(cursor.getColumnIndexOrThrow("course_date"));
        String mCourseTime = cursor.getString(cursor.getColumnIndexOrThrow("course_time"));
        String mCourseVenue = cursor.getString(cursor.getColumnIndexOrThrow("course_venue"));
        String mCourseStatus = cursor.getString(cursor.getColumnIndexOrThrow("course_status"));
        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtCourseNo.setText(mCourseNo);
        txtInstructor.setText(mInstructor);
        txtCourseDate.setText(mCourseDate);
        txtCourseTime.setText(mCourseTime);
        txtCourseVenue.setText(mCourseVenue);
        txtCourseStatus.setText(mCourseStatus);
    }

}
