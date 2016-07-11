package com.deenysoft.schoolbox.dashboard.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deenysoft.schoolbox.cosmos.app.CosmosApplication;
import com.deenysoft.schoolbox.dashboard.model.AssignmentBoxItem;
import com.deenysoft.schoolbox.dashboard.model.CourseBoxItem;
import com.deenysoft.schoolbox.dashboard.model.ExamBoxItem;
import com.deenysoft.schoolbox.dashboard.model.NoteBoxItem;
import com.deenysoft.schoolbox.dashboard.model.PresentationBoxItem;
import com.deenysoft.schoolbox.dashboard.model.QuizBoxItem;
import com.deenysoft.schoolbox.dashboard.model.SchoolBoxItem;
import com.deenysoft.schoolbox.dashboard.model.TestBoxItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamsadam on 07/06/16.
 */
public class SchoolBoxDBManager {

    private static SQLiteDatabase mSQLiteDatabase;
    private static SchoolBoxDatabase mSchoolBoxDatabase;
    private static SchoolBoxDBManager mSchoolBoxDBManagerInstance;

    public static SchoolBoxDBManager getInstance() {

        if( mSchoolBoxDBManagerInstance == null ) {
            synchronized (SchoolBoxDBManager.class) {
                if (mSchoolBoxDBManagerInstance == null) {
                    mSchoolBoxDBManagerInstance = new SchoolBoxDBManager();
                }
            }
        }
        return mSchoolBoxDBManagerInstance;
    }


    public SchoolBoxDBManager() {
        mSchoolBoxDatabase = new SchoolBoxDatabase(CosmosApplication.getContext());
        open();
    }

    public void open() throws android.database.SQLException {
        mSQLiteDatabase = mSchoolBoxDatabase.getWritableDatabase();
    }

    public static void release() {
        close();
    }

    public static void close() {
        if( mSQLiteDatabase != null) {
            mSQLiteDatabase.close();
            mSQLiteDatabase = null;
        }
        if(mSchoolBoxDatabase != null) {
            mSchoolBoxDatabase.close();
            mSchoolBoxDatabase = null;
        }
    }


    // Add SchoolBoxItem values into the sqlite database.
    public long addSchoolItem(SchoolBoxItem mSchoolBoxItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        //values.put(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_ID, mSchoolBoxItem.getSchoolID());
        values.put(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_CARDNO, mSchoolBoxItem.getSchoolCardNo());
        values.put(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_TITLE, mSchoolBoxItem.getSchoolTitle());
        values.put(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_MAJOR, mSchoolBoxItem.getSchoolMajor());
        values.put(SchoolBoxDBTable.SCHOOL_FIELD.START_DATE, mSchoolBoxItem.getStartDate());
        values.put(SchoolBoxDBTable.SCHOOL_FIELD.END_DATE, mSchoolBoxItem.getEndDate());
        values.put(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_LOCATION, mSchoolBoxItem.getSchoolLocation());
        values.put(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_STATUS, mSchoolBoxItem.getSchoolStatus());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.SCHOOL_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }

    // Retrieve SchoolBoxItem values from the sqlite database.
    public List<SchoolBoxItem> getSchoolItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.SCHOOL_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_CARDNO + " DESC");

        List<SchoolBoxItem> mSchoolBoxItemList = new ArrayList<SchoolBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    SchoolBoxItem mSchoolBoxItem = new SchoolBoxItem();
                    //int SchoolID = cursor.getInt(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_ID));
                    String SchoolCardNo = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_CARDNO));
                    String SchoolTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_TITLE));
                    String SchoolMajor = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_MAJOR));
                    String StartDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.START_DATE));
                    String EndDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.END_DATE));
                    String SchoolLocation = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_LOCATION));
                    String SchoolStatus = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_STATUS));

                    //mSchoolBoxItem.setSchoolID(SchoolID);
                    mSchoolBoxItem.setSchoolCardNo(SchoolCardNo);
                    mSchoolBoxItem.setSchoolTitle(SchoolTitle);
                    mSchoolBoxItem.setSchoolMajor(SchoolMajor);
                    mSchoolBoxItem.setStartDate(StartDate);
                    mSchoolBoxItem.setEndDate(EndDate);
                    mSchoolBoxItem.setSchoolLocation(SchoolLocation);
                    mSchoolBoxItem.setSchoolStatus(SchoolStatus);
                    mSchoolBoxItemList.add(mSchoolBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mSchoolBoxItemList;

    }


    // Add CourseBoxItem values into the sqlite database.
    public long addCourseItem(CourseBoxItem mCourseBoxItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(SchoolBoxDBTable.COURSE_FIELD.COURSE_NO, mCourseBoxItem.getCourseID());
        values.put(SchoolBoxDBTable.COURSE_FIELD.COURSE_TITLE, mCourseBoxItem.getCourseTitle());
        values.put(SchoolBoxDBTable.COURSE_FIELD.COURSE_INSTRUCTOR, mCourseBoxItem.getCourseInstructor());
        values.put(SchoolBoxDBTable.COURSE_FIELD.COURSE_DATE, mCourseBoxItem.getCourseDate());
        values.put(SchoolBoxDBTable.COURSE_FIELD.COURSE_VENUE, mCourseBoxItem.getCourseVenue());
        values.put(SchoolBoxDBTable.COURSE_FIELD.COURSE_TIME, mCourseBoxItem.getCourseTime());
        values.put(SchoolBoxDBTable.COURSE_FIELD.COURSE_STATUS, mCourseBoxItem.getCourseStatus());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.COURSE_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }

    // Retrieve CourseBoxItem values from the sqlite database.
    public List<CourseBoxItem> getCourseItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.COURSE_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.COURSE_FIELD.COURSE_NO + " DESC");

        List<CourseBoxItem> mCourseBoxItemList = new ArrayList<CourseBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    CourseBoxItem mCourseBoxItem = new CourseBoxItem();
                    String CourseID = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_NO));
                    String CourseTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_TITLE));
                    String CourseInstructor = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_INSTRUCTOR));
                    String CourseDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_DATE));
                    String CourseVenue = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_VENUE));
                    String CourseTime = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_TIME));
                    String CourseStatus = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_STATUS));

                    mCourseBoxItem.setCourseID(CourseID);
                    mCourseBoxItem.setCourseTitle(CourseTitle);
                    mCourseBoxItem.setCourseInstructor(CourseInstructor);
                    mCourseBoxItem.setCourseDate(CourseDate);
                    mCourseBoxItem.setCourseVenue(CourseVenue);
                    mCourseBoxItem.setCourseTime(CourseTime);
                    mCourseBoxItem.setCourseStatus(CourseStatus);
                    mCourseBoxItemList.add(mCourseBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mCourseBoxItemList;

    }


    // Add QuizBoxItem values into the sqlite database.
    public long addQuizItem(QuizBoxItem mQuizBoxItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_NO, mQuizBoxItem.getQuizID());
        values.put(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_TITLE, mQuizBoxItem.getQuizTitle());
        values.put(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_VENUE, mQuizBoxItem.getQuizVenue());
        values.put(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_TIME, mQuizBoxItem.getQuizTime());
        values.put(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_DATE, mQuizBoxItem.getQuizDate());
        values.put(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_STATUS, mQuizBoxItem.getQuizStatus());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.QUIZ_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }


    // Retrieve QuizBoxItem values from the sqlite database.
    public List<QuizBoxItem> getQuizItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.QUIZ_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.QUIZ_FIELD.QUIZ_NO + " DESC");

        List<QuizBoxItem> mQuizBoxItemList = new ArrayList<QuizBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    QuizBoxItem mQuizBoxItem = new QuizBoxItem();
                    String QuizID = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_NO));
                    String QuizTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_TITLE));
                    String QuizVenue = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_VENUE));
                    String QuizTime = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_TIME));
                    String QuizDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_DATE));
                    String QuizStatus = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_STATUS));

                    mQuizBoxItem.setQuizID(QuizID);
                    mQuizBoxItem.setQuizTitle(QuizTitle);
                    mQuizBoxItem.setQuizVenue(QuizVenue);
                    mQuizBoxItem.setQuizTime(QuizTime);
                    mQuizBoxItem.setQuizDate(QuizDate);
                    mQuizBoxItem.setQuizStatus(QuizStatus);
                    mQuizBoxItemList.add(mQuizBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mQuizBoxItemList;

    }


    // Add TestBoxItem values into the sqlite database.
    public long addTestItem(TestBoxItem mTestBoxItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(SchoolBoxDBTable.TEST_FIELD.TEST_NO, mTestBoxItem.getTestID());
        values.put(SchoolBoxDBTable.TEST_FIELD.TEST_TITLE, mTestBoxItem.getTestTitle());
        values.put(SchoolBoxDBTable.TEST_FIELD.TEST_VENUE, mTestBoxItem.getTestVenue());
        values.put(SchoolBoxDBTable.TEST_FIELD.TEST_DATE, mTestBoxItem.getTestDate());
        values.put(SchoolBoxDBTable.TEST_FIELD.TEST_TIME, mTestBoxItem.getTestTime());
        values.put(SchoolBoxDBTable.TEST_FIELD.TEST_STATUS, mTestBoxItem.getTestStatus());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.TEST_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }


    // Retrieve TestBoxItem values from the sqlite database.
    public List<TestBoxItem> getTestItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.TEST_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.TEST_FIELD.TEST_NO + " DESC");

        List<TestBoxItem> mTestBoxItemList = new ArrayList<TestBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    TestBoxItem mTestBoxItem = new TestBoxItem();
                    String TestID = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.TEST_FIELD.TEST_NO));
                    String TestTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.TEST_FIELD.TEST_TITLE));
                    String TestVenue = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.TEST_FIELD.TEST_VENUE));
                    String TestTime = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.TEST_FIELD.TEST_TIME));
                    String TestDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.TEST_FIELD.TEST_DATE));
                    String TestStatus = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.TEST_FIELD.TEST_STATUS));

                    mTestBoxItem.setTestID(TestID);
                    mTestBoxItem.setTestTitle(TestTitle);
                    mTestBoxItem.setTestVenue(TestVenue);
                    mTestBoxItem.setTestTime(TestTime);
                    mTestBoxItem.setTestDate(TestDate);
                    mTestBoxItem.setTestStatus(TestStatus);
                    mTestBoxItemList.add(mTestBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mTestBoxItemList;

    }


    // Add AssignmentBoxItem values into the sqlite database.
    public long addAssignmentItem(AssignmentBoxItem mAssignmentTestBoxItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_NO, mAssignmentTestBoxItem.getAssignmentID());
        values.put(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_TITLE, mAssignmentTestBoxItem.getAssignmentTitle());
        values.put(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_SUB_VENUE, mAssignmentTestBoxItem.getAssignmentSubVenue());
        values.put(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_STATUS, mAssignmentTestBoxItem.getAssignmentStatus());
        values.put(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_DUE_DATE, mAssignmentTestBoxItem.getAssignmentDueDate());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.ASSIGNMENT_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }


    // Retrieve AssignmentBoxItem values from the sqlite database.
    public List<AssignmentBoxItem> getAssignmentItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.ASSIGNMENT_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_NO + " DESC");

        List<AssignmentBoxItem> mAssignmentBoxItemList = new ArrayList<AssignmentBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    AssignmentBoxItem mAssignmentBoxItem = new AssignmentBoxItem();
                    String AssignmentID = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_NO));
                    String AssignmentTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_TITLE));
                    String AssignmentSubVenue = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_SUB_VENUE));
                    String AssignmentDueDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_DUE_DATE));
                    String AssignmentStatus = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_STATUS));

                    mAssignmentBoxItem.setAssignmentID(AssignmentID);
                    mAssignmentBoxItem.setAssignmentTitle(AssignmentTitle);
                    mAssignmentBoxItem.setAssignmentSubVenue(AssignmentSubVenue);
                    mAssignmentBoxItem.setAssignmentDueDate(AssignmentDueDate);
                    mAssignmentBoxItem.setAssignmentStatus(AssignmentStatus);
                    mAssignmentBoxItemList.add(mAssignmentBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mAssignmentBoxItemList;

    }


    // Add PresentationBoxItem values into the sqlite database.
    public long addPresentationItem(PresentationBoxItem mPresentationBoxItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_NO, mPresentationBoxItem.getPresentationID());
        values.put(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_TITLE, mPresentationBoxItem.getPresentationTitle());
        values.put(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_DATE, mPresentationBoxItem.getPresentationDate());
        values.put(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_VENUE, mPresentationBoxItem.getPresentationVenue());
        values.put(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_TIME, mPresentationBoxItem.getPresentationTime());
        values.put(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_STATUS, mPresentationBoxItem.getPresentationStatus());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.PRESENTATION_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }


    // Retrieve PresentationBoxItem values from the sqlite database.
    public List<PresentationBoxItem> getPresentationItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.PRESENTATION_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_NO + " DESC");

        List<PresentationBoxItem> mPresentationBoxItemList = new ArrayList<PresentationBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    PresentationBoxItem mPresenationBoxItem = new PresentationBoxItem();
                    String PresentationID = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_NO));
                    String PresentationTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_TITLE));
                    String PresentationVenue = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_VENUE));
                    String PresentationTime = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_TIME));
                    String PresentationDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_DATE));
                    String PresentationStatus = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_STATUS));

                    mPresenationBoxItem.setPresentationID(PresentationID);
                    mPresenationBoxItem.setPresentationTitle(PresentationTitle);
                    mPresenationBoxItem.setPresentationVenue(PresentationVenue);
                    mPresenationBoxItem.setPresentationTime(PresentationTime);
                    mPresenationBoxItem.setPresentationDate(PresentationDate);
                    mPresenationBoxItem.setPresentationStatus(PresentationStatus);
                    mPresentationBoxItemList.add(mPresenationBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mPresentationBoxItemList;

    }


    // Add NoteBoxItem values into the sqlite database.
    public long addNoteItem(NoteBoxItem mNoteItemBox) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(SchoolBoxDBTable.NOTE_FIELD.NOTE_NO, mNoteItemBox.getNoteID());
        values.put(SchoolBoxDBTable.NOTE_FIELD.NOTE_TITLE, mNoteItemBox.getNoteTitle());
        values.put(SchoolBoxDBTable.NOTE_FIELD.NOTE_DESCRIPTION, mNoteItemBox.getNoteDescription());
        values.put(SchoolBoxDBTable.NOTE_FIELD.NOTE_DATE, mNoteItemBox.getNoteDate());
        values.put(SchoolBoxDBTable.NOTE_FIELD.NOTE_TIME, mNoteItemBox.getNoteTime());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.NOTE_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }


    // Retrieve NoteBoxItem values from the sqlite database.
    public List<NoteBoxItem> getNoteItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.NOTE_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.NOTE_FIELD.NOTE_NO + " DESC");

        List<NoteBoxItem> mNoteBoxItemList = new ArrayList<NoteBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    NoteBoxItem mNoteBoxItem = new NoteBoxItem();
                    String NoteID = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.NOTE_FIELD.NOTE_NO));
                    String NoteTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.NOTE_FIELD.NOTE_TITLE));
                    String NoteDescription = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.NOTE_FIELD.NOTE_DESCRIPTION));
                    String NoteTime = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.NOTE_FIELD.NOTE_TIME));
                    String NoteDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.NOTE_FIELD.NOTE_DATE));

                    mNoteBoxItem.setNoteID(NoteID);
                    mNoteBoxItem.setNoteTitle(NoteTitle);
                    mNoteBoxItem.setNoteDescription(NoteDescription);
                    mNoteBoxItem.setNoteDate(NoteDate);
                    mNoteBoxItem.setNoteTime(NoteTime);
                    mNoteBoxItemList.add(mNoteBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mNoteBoxItemList;

    }


    // Add ExamBoxItem values into the sqlite database.
    public long addExamItem(ExamBoxItem mExamBoxItem) throws android.database.SQLException {
        long insertId = 0;
        ContentValues values = new ContentValues();
        values.put(SchoolBoxDBTable.EXAM_FIELD.EXAM_NO, mExamBoxItem.getExamID());
        values.put(SchoolBoxDBTable.EXAM_FIELD.EXAM_TITLE, mExamBoxItem.getExamTitle());
        values.put(SchoolBoxDBTable.EXAM_FIELD.EXAM_VENUE, mExamBoxItem.getExamVenue());
        values.put(SchoolBoxDBTable.EXAM_FIELD.EXAM_DATE, mExamBoxItem.getExamDate());
        values.put(SchoolBoxDBTable.EXAM_FIELD.EXAM_TIME, mExamBoxItem.getExamTime());
        values.put(SchoolBoxDBTable.EXAM_FIELD.EXAM_STATUS, mExamBoxItem.getExamStatus());
        try {
            insertId = mSQLiteDatabase.insertOrThrow(SchoolBoxDBTable.EXAM_TABLE, null,values);
        } catch (android.database.SQLException ex) {
            throw ex;
        }
        return insertId;
    }


    // Retrieve NoteBoxItem values from the sqlite database.
    public List<ExamBoxItem> getExamItem() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.EXAM_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.EXAM_FIELD.EXAM_NO + " DESC");

        List<ExamBoxItem> mExamBoxItemList = new ArrayList<ExamBoxItem>();

        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    ExamBoxItem mExamBoxItem = new ExamBoxItem();
                    String ExamID = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.EXAM_FIELD.EXAM_NO));
                    String ExamTitle = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.EXAM_FIELD.EXAM_TITLE));
                    String ExamVenue = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.EXAM_FIELD.EXAM_VENUE));
                    String ExamDate = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.EXAM_FIELD.EXAM_DATE));
                    String ExamTime = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.EXAM_FIELD.EXAM_TIME));
                    String ExamStatus = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.EXAM_FIELD.EXAM_STATUS));

                    mExamBoxItem.setExamID(ExamID);
                    mExamBoxItem.setExamTitle(ExamTitle);
                    mExamBoxItem.setExamVenue(ExamVenue);
                    mExamBoxItem.setExamDate(ExamDate);
                    mExamBoxItem.setExamTime(ExamTime);
                    mExamBoxItem.setExamStatus(ExamStatus);
                    mExamBoxItemList.add(mExamBoxItem);
                } while (cursor.moveToNext());
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return mExamBoxItemList;

    }


    // Get SchoolBoxItem ID
    public String getSchoolCardNo(){
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.SCHOOL_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_CARDNO+" DESC ");

        String schoolCardNo = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    schoolCardNo = cursor.getString(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_CARDNO));
                }while (cursor.moveToNext());
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return schoolCardNo;
    }


    // Get the latest SchoolBoxItem EndDate
    public long getLatestSchoolBoxItemEndDate() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.SCHOOL_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.SCHOOL_FIELD.END_DATE+" DESC LIMIT 1");

        long endDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                endDate = cursor.getLong(cursor.getColumnIndex(SchoolBoxDBTable.SCHOOL_FIELD.END_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return endDate;
    }

    // Get the latest CourseBoxItem Date
    public long getLatestCourseBoxItemDate() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.COURSE_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.COURSE_FIELD.COURSE_DATE+" DESC LIMIT 1");

        long courseDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                courseDate = cursor.getLong(cursor.getColumnIndex(SchoolBoxDBTable.COURSE_FIELD.COURSE_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return courseDate;
    }

    // Get the latest QuizBoxItem Date
    public long getLatestQuizBoxItemDate() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.QUIZ_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.QUIZ_FIELD.QUIZ_DATE+" DESC LIMIT 1");

        long quizDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                quizDate = cursor.getLong(cursor.getColumnIndex(SchoolBoxDBTable.QUIZ_FIELD.QUIZ_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return quizDate;
    }

    // Get the latest TestBoxItem Date
    public long getLatestTestBoxItemDate() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.TEST_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.TEST_FIELD.TEST_DATE+" DESC LIMIT 1");

        long testDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                testDate = cursor.getLong(cursor.getColumnIndex(SchoolBoxDBTable.TEST_FIELD.TEST_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return testDate;
    }

    // Get the latest AssignmentBoxItem Date
    public long getLatestAssignmentBoxItemDate() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.ASSIGNMENT_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_DUE_DATE+" DESC LIMIT 1");

        long assignmentDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                assignmentDate = cursor.getLong(cursor.getColumnIndex(SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_DUE_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return assignmentDate;
    }

    // Get the latest PresentationBoxItem Date
    public long getLatestPresentationBoxItemDate() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.PRESENTATION_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_DATE+" DESC LIMIT 1");

        long presentationDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                presentationDate = cursor.getLong(cursor.getColumnIndex(SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return presentationDate;
    }

    // Get the latest NoteBoxItem Date
    public long getLatestNoteBoxItemDate() {
        Cursor cursor = mSQLiteDatabase.query(SchoolBoxDBTable.NOTE_TABLE,
                null, null, null, null, null, SchoolBoxDBTable.NOTE_FIELD.NOTE_DATE+" DESC LIMIT 1");

        long noteDate = 0;
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                noteDate = cursor.getLong(cursor.getColumnIndex(SchoolBoxDBTable.NOTE_FIELD.NOTE_DATE));
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        return noteDate;
    }

}
