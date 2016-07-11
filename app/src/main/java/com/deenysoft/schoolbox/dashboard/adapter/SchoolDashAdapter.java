package com.deenysoft.schoolbox.dashboard.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDBManager;
import com.deenysoft.schoolbox.dashboard.model.SchoolBoxItem;
import com.deenysoft.schoolbox.helper.ApiLevelHelper;

import java.util.List;

/**
 * Created by shamsadam on 10/06/16.
 */
public class SchoolDashAdapter extends RecyclerView.Adapter<SchoolDashAdapter.ViewHolder> {


    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<SchoolBoxItem> mSchoolBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public SchoolDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateSchoolBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_school_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SchoolBoxItem mSchoolBoxItem = mSchoolBoxItemList.get(position);
        holder.SchoolTitle.setText(mSchoolBoxItem.getSchoolTitle());
        holder.SchoolMajor.setText(mSchoolBoxItem.getSchoolMajor());
        holder.SchoolStatus.setText(mSchoolBoxItem.getSchoolStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mSchoolBoxItemList.get(position).getSchoolCardNo().hashCode();
    }


    public final void notifyItemChanged(String id) {
        //updateSchoolBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mSchoolBoxItemList.size(); i++) {
            if (mSchoolBoxItemList.get(i).getSchoolCardNo().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public SchoolBoxItem getItem(int position) {
        return mSchoolBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mSchoolBoxItemList.size();
    }


    public void updateSchoolBoxItem() {
        mSchoolBoxItemList = SchoolBoxDBManager.getInstance().getSchoolItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView SchoolTitle;
        TextView SchoolMajor;
        TextView SchoolStatus;

        public ViewHolder(View container) {
            super(container);
            SchoolTitle = (TextView) container.findViewById(R.id.schoolTitle);
            SchoolMajor = (TextView) container.findViewById(R.id.schoolMajor);
            SchoolStatus = (TextView) container.findViewById(R.id.schoolStatus);
        }
    }
}
