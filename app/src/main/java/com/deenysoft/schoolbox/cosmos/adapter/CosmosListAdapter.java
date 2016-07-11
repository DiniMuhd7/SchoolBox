package com.deenysoft.schoolbox.cosmos.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;
import com.deenysoft.schoolbox.cosmos.model.CosmosFeedItem;
import com.deenysoft.schoolbox.cosmos.util.DateTimeUtil;

import java.util.List;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    List<CosmosFeedItem> feedItemList;

    public CosmosListAdapter() {
        mInflater = LayoutInflater.from(CosmosApplication.getContext());
    }

    public void setFeedItems(List<CosmosFeedItem> data) {
        this.feedItemList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return feedItemList == null ? 0 : feedItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItemList == null ? null : feedItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cos_feeder_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.feedTitle = (TextView) convertView.findViewById(R.id.feedTitle);
            holder.feedDescription = (TextView) convertView.findViewById(R.id.feedDescription);
            holder.feedDate = (TextView) convertView.findViewById(R.id.feedTime);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CosmosFeedItem feedItem = feedItemList.get(position);
        holder.feedTitle.setText(Html.fromHtml(feedItem.getTitle()));
        holder.feedDescription.setText(Html.fromHtml(feedItem.getDescription()));
        holder.feedDate.setText(DateTimeUtil.getFormattedString(feedItem.getDate()));
        return convertView;
    }

    class ViewHolder {
        TextView feedTitle;
        TextView feedDescription;
        TextView feedDate;
    }
}
