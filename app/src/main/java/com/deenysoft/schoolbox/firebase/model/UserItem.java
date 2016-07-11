package com.deenysoft.schoolbox.firebase.model;

/**
 * Created by shamsadam on 13/06/16.
 */


public class UserItem {

    public String username;
    public String email;

    public UserItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserItem(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
