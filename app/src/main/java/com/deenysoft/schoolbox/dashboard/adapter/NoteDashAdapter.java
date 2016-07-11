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
import com.deenysoft.schoolbox.dashboard.model.NoteBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 23/06/16.
 */
public class NoteDashAdapter extends RecyclerView.Adapter<NoteDashAdapter.ViewHolder> {

    private final Activity mActivity;
    private final Resources mResources;
    private final String mPackageName;

    private LayoutInflater mInflater;
    List<NoteBoxItem> mNoteBoxItemList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


    public NoteDashAdapter(Activity activity){
        mActivity = activity;
        mResources = mActivity.getResources();
        mPackageName = mActivity.getPackageName();
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateNoteBoxItem();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.dashboard_note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        NoteBoxItem mNoteBoxItem = mNoteBoxItemList.get(position);
        holder.NoteTitle.setText(mNoteBoxItem.getNoteTitle());
        holder.NoteDate.setText(mNoteBoxItem.getNoteDate());
        holder.NoteTime.setText(mNoteBoxItem.getNoteTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mNoteBoxItemList.get(position).getNoteID().hashCode();
    }


    public final void notifyItemChanged(String id) {
        updateNoteBoxItem();
        notifyItemChanged(getItemPositionById(id));
    }

    private int getItemPositionById(String id) {
        for (int i = 0; i < mNoteBoxItemList.size(); i++) {
            if (mNoteBoxItemList.get(i).getNoteID().equals(id)) {
                return i;
            }

        }
        return -1;
    }

    public NoteBoxItem getItem(int position) {
        return mNoteBoxItemList.get(position);
    }

    @Override
    public int getItemCount() {
        return mNoteBoxItemList.size();
    }


    public void updateNoteBoxItem() {
        mNoteBoxItemList = SchoolBoxDBManager.getInstance().getNoteItem();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NoteTitle;
        TextView NoteDate;
        TextView NoteTime;

        public ViewHolder(View container) {
            super(container);
            NoteTitle = (TextView) container.findViewById(R.id.noteTitle);
            NoteDate = (TextView) container.findViewById(R.id.noteDate);
            NoteTime = (TextView) container.findViewById(R.id.noteTime);
        }
    }


}
