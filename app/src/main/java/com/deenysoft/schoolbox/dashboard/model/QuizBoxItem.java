package com.deenysoft.schoolbox.dashboard.model;

/**
 * Created by shamsadam on 08/06/16.
 */
public class QuizBoxItem {

    public static final String TAG = "QuizBoxItem";

    String QuizID;
    String QuizTitle;
    String QuizDate;
    String QuizVenue;
    String QuizTime;
    String QuizStatus;

    public void setQuizID(String QuizID) {
        this.QuizID = QuizID;
    }

    public String getQuizID(){
        return QuizID;
    }

    public void setQuizTitle(String QuizTitle) {
        this.QuizTitle = QuizTitle;
    }

    public String getQuizTitle(){
        return QuizTitle;

    }

    public void setQuizDate(String QuizDate) {
        this.QuizDate = QuizDate;
    }

    public String getQuizDate(){
        return QuizDate;
    }

    public void setQuizVenue(String QuizVenue) {
        this.QuizVenue = QuizVenue;
    }

    public String getQuizVenue() {
        return QuizVenue;
    }

    public void setQuizTime(String QuizTime) {
        this.QuizTime = QuizTime;
    }

    public String getQuizTime() {
        return QuizTime;
    }

    public void setQuizStatus(String QuizStatus) {
        this.QuizStatus = QuizStatus;
    }

    public String getQuizStatus() {
        return QuizStatus;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QuizID: "+QuizID);
        builder.append("\n");
        builder.append("Quiz: "+QuizTitle);
        builder.append("\n");
        builder.append("QuizDate: "+QuizDate);
        builder.append("\n");
        builder.append("Venue: "+QuizVenue);
        builder.append("\n");
        builder.append("Time: "+QuizTime);
        builder.append("\n");
        builder.append("Status: "+QuizStatus);
        builder.append("\n\n");
        return builder.toString();
    }

}
