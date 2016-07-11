package com.deenysoft.schoolbox.dashboard.model;

/**
 * Created by shamsadam on 08/06/16.
 */
public class AssignmentBoxItem {

    public static final String TAG = "AssignmentBoxItem";

    String AssignmentID;
    String AssignmentTitle;
    String AssignmentDueDate;
    String AssignmentSubVenue;
    String AssignmentStatus;

    public void setAssignmentID(String AssignmentID) {
        this.AssignmentID = AssignmentID;
    }

    public String getAssignmentID(){
        return AssignmentID;
    }

    public void setAssignmentTitle(String AssignmentTitle) {
        this.AssignmentTitle = AssignmentTitle;
    }

    public String getAssignmentTitle(){
        return AssignmentTitle;

    }

    public void setAssignmentDueDate(String AssignmentDueDate) {
        this.AssignmentDueDate = AssignmentDueDate;
    }

    public String getAssignmentDueDate(){
        return AssignmentDueDate;
    }

    public void setAssignmentSubVenue(String AssignmentSubVenue) {
        this.AssignmentSubVenue = AssignmentSubVenue;
    }

    public String getAssignmentSubVenue() {
        return AssignmentSubVenue;
    }

    public void setAssignmentStatus(String AssignmentStatus) {
        this.AssignmentStatus = AssignmentStatus;
    }

    public String getAssignmentStatus() {
        return AssignmentStatus;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AssignmentID: "+AssignmentID);
        builder.append("\n");
        builder.append("AssignmentTitle: "+AssignmentTitle);
        builder.append("\n");
        builder.append("AssignmentDueDate: "+AssignmentDueDate);
        builder.append("\n");
        builder.append("SubmissionVenue: "+AssignmentSubVenue);
        builder.append("\n");
        builder.append("Status: "+AssignmentStatus);
        builder.append("\n\n");
        return builder.toString();
    }

}
