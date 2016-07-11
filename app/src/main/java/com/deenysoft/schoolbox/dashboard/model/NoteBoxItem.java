package com.deenysoft.schoolbox.dashboard.model;

/**
 * Created by shamsadam on 08/06/16.
 */
public class NoteBoxItem {

    public static final String TAG = "NoteBoxItem";

    String NoteID;
    String NoteTitle;
    String NoteDate;
    String NoteDescription;
    String NoteTime;

    public void setNoteID(String NoteID) {
        this.NoteID = NoteID;
    }

    public String getNoteID(){
        return NoteID;
    }

    public void setNoteTitle(String NoteTitle) {
        this.NoteTitle = NoteTitle;
    }

    public String getNoteTitle(){
        return NoteTitle;

    }

    public void setNoteDate(String NoteDate) {
        this.NoteDate = NoteDate;
    }

    public String getNoteDate(){
        return NoteDate;
    }

    public void setNoteDescription(String NoteDescription) {
        this.NoteDescription = NoteDescription;
    }

    public String getNoteDescription() {
        return NoteDescription;
    }

    public void setNoteTime(String NoteTime) {
        this.NoteTime = NoteTime;
    }

    public String getNoteTime() {
        return NoteTime;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NoteID: "+NoteID);
        builder.append("\n");
        builder.append("NoteTitle: "+NoteTitle);
        builder.append("\n");
        builder.append("NoteDescription: "+NoteDescription);
        builder.append("\n");
        builder.append("Date: "+NoteDate);
        builder.append("\n");
        builder.append("Time: "+NoteTime);
        builder.append("\n\n");
        return builder.toString();
    }

}
