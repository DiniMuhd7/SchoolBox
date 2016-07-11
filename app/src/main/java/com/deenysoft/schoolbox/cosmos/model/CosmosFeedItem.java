package com.deenysoft.schoolbox.cosmos.model;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosFeedItem {

    String title;

    String description;

    String hyperlink;

    long date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;

    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Title: "+title);
        builder.append("\n");
        builder.append("Description: "+description);
        builder.append("\n");
        builder.append("Link: "+hyperlink);
        builder.append("\n");
        builder.append("Date: "+date);
        builder.append("\n\n");
        return builder.toString();
    }
}
