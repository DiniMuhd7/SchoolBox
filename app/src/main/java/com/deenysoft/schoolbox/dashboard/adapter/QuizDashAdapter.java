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
import com.deenysoft.schoolbox.dashboard.model.QuizBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 23/06/16.
 */
public class QuizDashAdapter extends RecyclerView.Adapter<QuizDashAdapter.ViewHolder> {

    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<QuizBoxItem> mQuizBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public QuizDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateQuizBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_quiz_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        QuizBoxItem mQuizBoxItem = mQuizBoxItemList.get(position);
        holder.QuizTitle.setText(mQuizBoxItem.getQuizTitle());
        holder.QuizDate.setText(mQuizBoxItem.getQuizDate());
        holder.QuizTime.setText(mQuizBoxItem.getQuizTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mQuizBoxItemList.get(position).getQuizID().hashCode();
    }


    public final void notifyItemChanged(String id) {
        updateQuizBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mQuizBoxItemList.size(); i++) {
            if (mQuizBoxItemList.get(i).getQuizID().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public QuizBoxItem getItem(int position) {
        return mQuizBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mQuizBoxItemList.size();
    }


    public void updateQuizBoxItem() {
        mQuizBoxItemList = SchoolBoxDBManager.getInstance().getQuizItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView QuizTitle;
        TextView QuizDate;
        TextView QuizTime;

        public ViewHolder(View container) {
            super(container);
            QuizTitle = (TextView) container.findViewById(R.id.quizTitle);
            QuizDate = (TextView) container.findViewById(R.id.quizDate);
            QuizTime = (TextView) container.findViewById(R.id.quizTime);
        }
    }

}
