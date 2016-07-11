package com.deenysoft.schoolbox.dashboard.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDBManager;
import com.deenysoft.schoolbox.dashboard.model.CourseBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 22/06/16.
 */
public class CourseDashAdapter extends RecyclerView.Adapter<CourseDashAdapter.ViewHolder> {

    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<CourseBoxItem> mCourseBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public CourseDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateCourseBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CourseBoxItem mCourseBoxItem = mCourseBoxItemList.get(position);
        holder.CourseTitle.setText(mCourseBoxItem.getCourseTitle());
        holder.CourseDate.setText(mCourseBoxItem.getCourseDate());
        holder.CourseTime.setText(mCourseBoxItem.getCourseTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mCourseBoxItemList.get(position).getCourseID().hashCode();
    }


    public final void notifyItemChanged(String id) {
        updateCourseBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mCourseBoxItemList.size(); i++) {
            if (mCourseBoxItemList.get(i).getCourseID().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public CourseBoxItem getItem(int position) {
        return mCourseBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mCourseBoxItemList.size();
    }


    public void updateCourseBoxItem() {
        mCourseBoxItemList = SchoolBoxDBManager.getInstance().getCourseItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView CourseTitle;
        TextView CourseDate;
        TextView CourseTime;

        public ViewHolder(View container) {
            super(container);
            CourseTitle = (TextView) container.findViewById(R.id.courseTitle);
            CourseDate = (TextView) container.findViewById(R.id.courseDate);
            CourseTime = (TextView) container.findViewById(R.id.courseTime);
        }
    }
}
