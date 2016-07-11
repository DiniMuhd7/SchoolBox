package com.deenysoft.schoolbox.dashboard.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.deenysoft.schoolbox.model.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamsadam on 08/06/16.
 */
public class SchoolBoxItem {

    public static final String TAG = "SchoolBoxItem";

    String SchoolCardNo;
    String SchoolTitle;
    String SchoolMajor;
    String SchoolStatus;
    String StartDate;
    String EndDate;
    String SchoolLocation;


    public void setSchoolTitle(String SchoolTitle) {
        this.SchoolTitle = SchoolTitle;
    }

    public String getSchoolTitle(){
        return SchoolTitle;
    }

    public void setSchoolMajor(String SchoolMajor) {
        this.SchoolMajor = SchoolMajor;
    }

    public String getSchoolMajor(){
        return SchoolMajor;
    }

    public void setSchoolStatus(String SchoolStatus) {
        this.SchoolStatus = SchoolStatus;
    }

    public String getSchoolStatus(){
        return SchoolStatus;
    }

    public void setSchoolCardNo(String SchoolCardNo) {
        this.SchoolCardNo = SchoolCardNo;
    }

    public String getSchoolCardNo(){
        return SchoolCardNo;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setSchoolLocation(String SchoolLocation) {
        this.SchoolLocation = SchoolLocation;
    }

    public String getSchoolLocation(){
        return SchoolLocation;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("School CardNo: "+SchoolCardNo);
        builder.append("\n");
        builder.append("School Title: "+SchoolTitle);
        builder.append("\n");
        builder.append("Faculty: "+SchoolMajor);
        builder.append("\n");
        builder.append("StartDate: "+StartDate);
        builder.append("\n");
        builder.append("EndDate: "+EndDate);
        builder.append("\n");
        builder.append("Location: "+SchoolLocation);
        builder.append("\n");
        builder.append("Status: "+SchoolStatus);
        builder.append("\n\n");
        return builder.toString();
    }

}
