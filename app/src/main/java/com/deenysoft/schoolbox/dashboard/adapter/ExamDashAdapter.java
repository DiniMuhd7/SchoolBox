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
import com.deenysoft.schoolbox.dashboard.model.ExamBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 23/06/16.
 */
public class ExamDashAdapter extends RecyclerView.Adapter<ExamDashAdapter.ViewHolder> {

    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<ExamBoxItem> mExamBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public ExamDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateExamBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_exam_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ExamBoxItem mExamBoxItem = mExamBoxItemList.get(position);
        holder.ExamTitle.setText(mExamBoxItem.getExamTitle());
        holder.ExamDate.setText(mExamBoxItem.getExamDate());
        holder.ExamTime.setText(mExamBoxItem.getExamTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mExamBoxItemList.get(position).getExamID().hashCode();
    }


    public final void notifyItemChanged(String id) {
        updateExamBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mExamBoxItemList.size(); i++) {
            if (mExamBoxItemList.get(i).getExamID().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public ExamBoxItem getItem(int position) {
        return mExamBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mExamBoxItemList.size();
    }


    public void updateExamBoxItem() {
        mExamBoxItemList = SchoolBoxDBManager.getInstance().getExamItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ExamTitle;
        TextView ExamDate;
        TextView ExamTime;

        public ViewHolder(View container) {
            super(container);
            ExamTitle = (TextView) container.findViewById(R.id.examTitle);
            ExamDate = (TextView) container.findViewById(R.id.examDate);
            ExamTime = (TextView) container.findViewById(R.id.examTime);
        }
    }

}
