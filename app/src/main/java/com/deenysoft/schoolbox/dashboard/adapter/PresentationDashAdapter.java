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
import com.deenysoft.schoolbox.dashboard.model.PresentationBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 23/06/16.
 */
public class PresentationDashAdapter extends RecyclerView.Adapter<PresentationDashAdapter.ViewHolder> {

    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<PresentationBoxItem> mPresentationBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public PresentationDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updatePresentationBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_presentation_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PresentationBoxItem mPresentationBoxItem = mPresentationBoxItemList.get(position);
        holder.PresentationTitle.setText(mPresentationBoxItem.getPresentationTitle());
        holder.PresentationDate.setText(mPresentationBoxItem.getPresentationDate());
        holder.PresentationTime.setText(mPresentationBoxItem.getPresentationTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mPresentationBoxItemList.get(position).getPresentationID().hashCode();
    }


    public final void notifyItemChanged(String id) {
        updatePresentationBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mPresentationBoxItemList.size(); i++) {
            if (mPresentationBoxItemList.get(i).getPresentationID().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public PresentationBoxItem getItem(int position) {
        return mPresentationBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mPresentationBoxItemList.size();
    }


    public void updatePresentationBoxItem() {
        mPresentationBoxItemList = SchoolBoxDBManager.getInstance().getPresentationItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView PresentationTitle;
        TextView PresentationDate;
        TextView PresentationTime;

        public ViewHolder(View container) {
            super(container);
            PresentationTitle = (TextView) container.findViewById(R.id.presentationTitle);
            PresentationDate = (TextView) container.findViewById(R.id.presentationDate);
            PresentationTime = (TextView) container.findViewById(R.id.presentationTime);
        }
    }


}
