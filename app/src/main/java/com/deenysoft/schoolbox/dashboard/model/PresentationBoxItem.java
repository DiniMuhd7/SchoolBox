package com.deenysoft.schoolbox.dashboard.model;

/**
 * Created by shamsadam on 08/06/16.
 */
public class PresentationBoxItem {

    public static final String TAG = "PresentationBoxItem";

    String PresentationID;
    //String CourseID; // Foreign Key
    String PresentationTitle;
    String PresentationDate;
    String PresentationVenue;
    String PresentationTime;
    String PresentationStatus;

    public void setPresentationID(String PresentationID) {
        this.PresentationID = PresentationID;
    }

    public String getPresentationID(){
        return PresentationID;
    }

    public void setPresentationTitle(String PresentationTitle) {
        this.PresentationTitle = PresentationTitle;
    }

    public String getPresentationTitle(){
        return PresentationTitle;

    }

    public void setPresentationDate(String PresentationDate) {
        this.PresentationDate = PresentationDate;
    }

    public String getPresentationDate(){
        return PresentationDate;
    }

    public void setPresentationVenue(String PresentationVenue) {
        this.PresentationVenue = PresentationVenue;
    }

    public String getPresentationVenue() {
        return PresentationVenue;
    }

    public void setPresentationTime(String PresentationTime) {
        this.PresentationTime = PresentationTime;
    }

    public String getPresentationTime() {
        return PresentationTime;
    }

    public void setPresentationStatus(String PresentationStatus) {
        this.PresentationStatus = PresentationStatus;
    }

    public String getPresentationStatus() {
        return PresentationStatus;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PresentationID: "+PresentationID);
        builder.append("\n");
        builder.append("PresentationTitle: "+PresentationTitle);
        builder.append("\n");
        builder.append("PresentationDate: "+PresentationDate);
        builder.append("\n");
        builder.append("PresentationVenue: "+PresentationVenue);
        builder.append("\n");
        builder.append("Time: "+PresentationTime);
        builder.append("\n");
        builder.append("Status: "+PresentationStatus);
        builder.append("\n\n");
        return builder.toString();
    }

}
