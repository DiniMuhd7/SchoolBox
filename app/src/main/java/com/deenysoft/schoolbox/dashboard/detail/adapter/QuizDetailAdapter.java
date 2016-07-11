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
import com.deenysoft.schoolbox.dashboard.model.QuizBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 26/06/16.
 */
public class QuizDetailAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    List<QuizBoxItem> mQuizBoxItemList;

    public QuizDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateQuizBoxItem();
    }


    public void updateQuizBoxItem() {
        mQuizBoxItemList = SchoolBoxDBManager.getInstance().getQuizItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.quiz_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.quizTitle);
        TextView txtQuizNo = (TextView) view.findViewById(R.id.quizNo);
        TextView txtQuizVenue = (TextView) view.findViewById(R.id.quizVenue);
        TextView txtQuizDate = (TextView) view.findViewById(R.id.quizDate);
        TextView txtQuizTime = (TextView) view.findViewById(R.id.quizTime);
        TextView txtQuizStatus = (TextView) view.findViewById(R.id.quizStatus);

        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("quiz_title"));
        String mQuizNo = cursor.getString(cursor.getColumnIndexOrThrow("quiz_no"));
        String mQuizVenue = cursor.getString(cursor.getColumnIndexOrThrow("quiz_venue"));
        String mQuizDate = cursor.getString(cursor.getColumnIndexOrThrow("quiz_date"));
        String mQuizTime = cursor.getString(cursor.getColumnIndexOrThrow("quiz_time"));
        String mQuizStatus = cursor.getString(cursor.getColumnIndexOrThrow("quiz_status"));
        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtQuizNo.setText(mQuizNo);
        txtQuizVenue.setText(mQuizVenue);
        txtQuizDate.setText(mQuizDate);
        txtQuizTime.setText(mQuizTime);
        txtQuizStatus.setText(mQuizStatus);

    }

}
