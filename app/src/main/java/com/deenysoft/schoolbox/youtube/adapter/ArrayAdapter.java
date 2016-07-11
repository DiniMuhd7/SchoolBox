package com.deenysoft.schoolbox.youtube.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deenysoft.schoolbox.R;

import java.util.List;

/**
 * Created by shamsadam on 04/07/16.
 */
public final class ArrayAdapter extends android.widget.ArrayAdapter<ListViewItem> {

    private final LayoutInflater inflater;

    public ArrayAdapter(Context context, int textViewResourceId, List<ListViewItem> objects) {
        super(context, textViewResourceId, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.youtube_list_item, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.list_item_text);
        textView.setText(getItem(position).getTitle());
        TextView disabledText = (TextView) view.findViewById(R.id.list_item_disabled_text);
        disabledText.setText(getItem(position).getDisabledText());

        if (isEnabled(position)) {
            disabledText.setVisibility(View.INVISIBLE);
            textView.setTextColor(Color.WHITE);
        } else {
            disabledText.setVisibility(View.VISIBLE);
            textView.setTextColor(Color.GRAY);
        }

        return view;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // have to return true here otherwise disabled items won't show a divider in the list.
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).isEnabled();
    }

    public boolean anyDisabled() {
        for (int i = 0; i < getCount(); i++) {
            if (!isEnabled(i)) {
                return true;
            }
        }
        return false;
    }

}
