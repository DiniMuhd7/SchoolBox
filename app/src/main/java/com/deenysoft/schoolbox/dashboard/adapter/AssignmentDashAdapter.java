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
import com.deenysoft.schoolbox.dashboard.model.AssignmentBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 23/06/16.
 */
public class AssignmentDashAdapter extends RecyclerView.Adapter<AssignmentDashAdapter.ViewHolder> {

    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<AssignmentBoxItem> mAssignmentBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public AssignmentDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateAssignmentBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_assignment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AssignmentBoxItem mAssignmentBoxItem = mAssignmentBoxItemList.get(position);
        holder.AssignmentTitle.setText(mAssignmentBoxItem.getAssignmentTitle());
        holder.AssignmentDate.setText(mAssignmentBoxItem.getAssignmentDueDate());
        holder.AssignmentStatus.setText(mAssignmentBoxItem.getAssignmentStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mAssignmentBoxItemList.get(position).getAssignmentID().hashCode();
    }


    public final void notifyItemChanged(String id) {
        updateAssignmentBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mAssignmentBoxItemList.size(); i++) {
            if (mAssignmentBoxItemList.get(i).getAssignmentID().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public AssignmentBoxItem getItem(int position) {
        return mAssignmentBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mAssignmentBoxItemList.size();
    }


    public void updateAssignmentBoxItem() {
        mAssignmentBoxItemList = SchoolBoxDBManager.getInstance().getAssignmentItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView AssignmentTitle;
        TextView AssignmentDate;
        TextView AssignmentStatus;

        public ViewHolder(View container) {
            super(container);
            AssignmentTitle = (TextView) container.findViewById(R.id.assignmentTitle);
            AssignmentDate = (TextView) container.findViewById(R.id.assignmentDueDate);
            AssignmentStatus = (TextView) container.findViewById(R.id.assignmentStatus);
        }
    }


}
