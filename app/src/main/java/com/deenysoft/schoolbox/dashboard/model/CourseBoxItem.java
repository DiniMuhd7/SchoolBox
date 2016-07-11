package com.deenysoft.schoolbox.dashboard.model;

/**
 * Created by shamsadam on 08/06/16.
 */
public class CourseBoxItem {

    public static final String TAG = "CourseBoxItem";

    String CourseID;
    String CourseTitle;
    String CourseInstructor;
    String CourseVenue;
    String CourseDate;
    String CourseTime;
    String CourseStatus;

    public void setCourseID(String CourseID) {
        this.CourseID = CourseID;
    }

    public String getCourseID(){
        return CourseID;
    }

    public void setCourseTitle(String CourseTitle) {
        this.CourseTitle = CourseTitle;
    }

    public String getCourseTitle(){
        return CourseTitle;
    }

    public void setCourseInstructor(String CourseInstructor) {
        this.CourseInstructor = CourseInstructor;
    }

    public String getCourseInstructor(){
        return CourseInstructor;
    }


    public void setCourseVenue(String CourseVenue) {
        this.CourseVenue = CourseVenue;
    }

    public String getCourseVenue() {
        return CourseVenue;
    }

    public void setCourseTime(String CourseTime) {
        this.CourseTime = CourseTime;
    }

    public String getCourseTime() {
        return CourseTime;
    }

    public void setCourseDate(String CourseDate) {
        this.CourseDate = CourseDate;
    }

    public String getCourseDate() {
        return CourseDate;
    }

    public void setCourseStatus(String CourseStatus) {
        this.CourseStatus = CourseStatus;
    }

    public String getCourseStatus() {
        return CourseStatus;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CourseID: "+CourseID);
        builder.append("\n");
        builder.append("Course: "+CourseTitle);
        builder.append("\n");
        builder.append("Instructor: "+CourseInstructor);
        builder.append("\n");
        builder.append("Venue: "+CourseVenue);
        builder.append("\n");
        builder.append("Time: "+CourseTime);
        builder.append("\n");
        builder.append("Status: "+CourseStatus);
        builder.append("\n\n");
        return builder.toString();
    }

}
