package com.deenysoft.schoolbox.dashboard.model;

/**
 * Created by shamsadam on 08/06/16.
 */
public class TestBoxItem {

    public static final String TAG = "TestBoxItem";

    String TestID;
    String TestTitle;
    String TestDate;
    String TestVenue;
    String TestTime;
    String TestStatus;

    public void setTestID(String TestID) {
        this.TestID = TestID;
    }

    public String getTestID(){
        return TestID;
    }

    public void setTestTitle(String TestTitle) {
        this.TestTitle = TestTitle;
    }

    public String getTestTitle(){
        return TestTitle;

    }

    public void setTestDate(String TestDate) {
        this.TestDate = TestDate;
    }

    public String getTestDate(){
        return TestDate;
    }

    public void setTestVenue(String TestVenue) {
        this.TestVenue = TestVenue;
    }

    public String getTestVenue() {
        return TestVenue;
    }

    public void setTestTime(String TestTime) {
        this.TestTime = TestTime;
    }

    public String getTestTime() {
        return TestTime;
    }

    public void setTestStatus(String TestStatus) {
        this.TestStatus = TestStatus;
    }

    public String getTestStatus() {
        return TestStatus;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TestID: "+TestID);
        builder.append("\n");
        builder.append("TestTitle: "+TestTitle);
        builder.append("\n");
        builder.append("TestDate: "+TestDate);
        builder.append("\n");
        builder.append("Venue: "+TestVenue);
        builder.append("\n");
        builder.append("Time: "+TestTime);
        builder.append("\n");
        builder.append("Status: "+TestStatus);
        builder.append("\n\n");
        return builder.toString();
    }

}
