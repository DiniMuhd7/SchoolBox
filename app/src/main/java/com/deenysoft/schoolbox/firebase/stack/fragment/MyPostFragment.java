package com.deenysoft.schoolbox.firebase.stack.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by shamsadam on 15/06/16.
 */
public class MyPostFragment extends PostListFragment {

    public MyPostFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid());
    }
}
