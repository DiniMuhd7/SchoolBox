package com.deenysoft.schoolbox.dashboard.model;

/**
 * Created by shamsadam on 23/06/16.
 */
public class ExamBoxItem {

    public static final String TAG = "ExamBoxItem";

    String ExamID;
    String ExamTitle;
    String ExamDate;
    String ExamVenue;
    String ExamTime;
    String ExamStatus;

    public void setExamID(String ExamID) {
        this.ExamID = ExamID;
    }

    public String getExamID(){
        return ExamID;
    }

    public void setExamTitle(String ExamTitle) {
        this.ExamTitle = ExamTitle;
    }

    public String getExamTitle(){
        return ExamTitle;

    }

    public void setExamDate(String ExamDate) {
        this.ExamDate = ExamDate;
    }

    public String getExamDate(){
        return ExamDate;
    }

    public void setExamVenue(String ExamVenue) {
        this.ExamVenue = ExamVenue;
    }

    public String getExamVenue() {
        return ExamVenue;
    }

    public void setExamTime(String ExamTime) {
        this.ExamTime = ExamTime;
    }

    public String getExamTime() {
        return ExamTime;
    }

    public void setExamStatus(String ExamStatus) {
        this.ExamStatus = ExamStatus;
    }

    public String getExamStatus() {
        return ExamStatus;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExamID: "+ExamID);
        builder.append("\n");
        builder.append("ExamTitle: "+ExamTitle);
        builder.append("\n");
        builder.append("ExamDate: "+ExamDate);
        builder.append("\n");
        builder.append("Venue: "+ExamVenue);
        builder.append("\n");
        builder.append("Time: "+ExamTime);
        builder.append("\n\n");
        return builder.toString();
    }

}
