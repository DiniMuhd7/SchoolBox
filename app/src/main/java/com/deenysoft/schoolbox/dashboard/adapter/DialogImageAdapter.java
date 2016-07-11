package com.deenysoft.schoolbox.dashboard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.deenysoft.schoolbox.R;

/**
 * Created by shamsadam on 12/06/16.
 */
public class DialogImageAdapter extends BaseAdapter {

    private Context mContext;

    public DialogImageAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.ic_school_add, R.drawable.ic_course_add,
            R.drawable.ic_test_add, R.drawable.ic_quiz_add, R.drawable.ic_assign_add,
            R.drawable.ic_present_add, R.drawable.ic_exam_add, R.drawable.ic_note_add
    };
}
