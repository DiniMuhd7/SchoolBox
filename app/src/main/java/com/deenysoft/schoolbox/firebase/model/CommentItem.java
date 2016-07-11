package com.deenysoft.schoolbox.firebase.model;

/**
 * Created by shamsadam on 15/06/16.
 */
public class CommentItem {

    public String uid;
    public String author;
    public String text;

    public CommentItem() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public CommentItem(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }
}
