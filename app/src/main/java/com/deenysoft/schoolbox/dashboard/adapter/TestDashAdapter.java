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
import com.deenysoft.schoolbox.dashboard.model.TestBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 23/06/16.
 */
public class TestDashAdapter extends RecyclerView.Adapter<TestDashAdapter.ViewHolder> {

    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<TestBoxItem> mTestBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public TestDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateTestBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_test_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TestBoxItem mTestBoxItem = mTestBoxItemList.get(position);
        holder.TestTitle.setText(mTestBoxItem.getTestTitle());
        holder.TestDate.setText(mTestBoxItem.getTestDate());
        holder.TestTime.setText(mTestBoxItem.getTestTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mTestBoxItemList.get(position).getTestID().hashCode();
    }


    public final void notifyItemChanged(String id) {
        updateTestBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mTestBoxItemList.size(); i++) {
            if (mTestBoxItemList.get(i).getTestID().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public TestBoxItem getItem(int position) {
        return mTestBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mTestBoxItemList.size();
    }


    public void updateTestBoxItem() {
        mTestBoxItemList = SchoolBoxDBManager.getInstance().getTestItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView TestTitle;
        TextView TestDate;
        TextView TestTime;

        public ViewHolder(View container) {
            super(container);
            TestTitle = (TextView) container.findViewById(R.id.testTitle);
            TestDate = (TextView) container.findViewById(R.id.testDate);
            TestTime = (TextView) container.findViewById(R.id.testTime);
        }
    }

}
