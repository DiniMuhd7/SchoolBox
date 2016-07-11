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
import com.deenysoft.schoolbox.dashboard.model.NoteBoxItem;

import java.util.List;

/**
 * Created by shamsadam on 26/06/16.
 */
public class NoteDetailAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    List<NoteBoxItem> mNoteBoxItemList;

    public NoteDetailAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
        updateNoteBoxItem();
    }


    public void updateNoteBoxItem() {
        mNoteBoxItemList = SchoolBoxDBManager.getInstance().getNoteItem();
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.from(context).inflate(R.layout.note_detail_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtTitle = (TextView) view.findViewById(R.id.noteTitle);
        TextView txtNoteDescription = (TextView) view.findViewById(R.id.noteDescription);


        // Extract properties from cursor
        String mTitle = cursor.getString(cursor.getColumnIndexOrThrow("note_title"));
        String mNoteDescription = cursor.getString(cursor.getColumnIndexOrThrow("note_description"));

        // Populate fields with extracted properties
        txtTitle.setText(mTitle);
        txtNoteDescription.setText(mNoteDescription);

    }

}
