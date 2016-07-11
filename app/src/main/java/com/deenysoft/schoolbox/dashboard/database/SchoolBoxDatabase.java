package com.deenysoft.schoolbox.dashboard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shamsadam on 07/06/16.
 */
public class SchoolBoxDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "schoolbox.db";

    public SchoolBoxDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Tables
        db.execSQL("Create table if not exists " + SchoolBoxDBTable.SCHOOL_TABLE +
                "(" + SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_CARDNO + " TEXT," +
                SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_TITLE + " TEXT," +
                SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_MAJOR + " TEXT," +
                SchoolBoxDBTable.SCHOOL_FIELD.START_DATE + " DATE," +
                SchoolBoxDBTable.SCHOOL_FIELD.END_DATE + " DATE," +
                SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_LOCATION + " TEXT," +
                SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_STATUS + " TEXT," +
                "UNIQUE ("+SchoolBoxDBTable.SCHOOL_FIELD.SCHOOL_ID+
                ")"+");");

        db.execSQL("Create table if not exists " + SchoolBoxDBTable.COURSE_TABLE +
                "(" + SchoolBoxDBTable.COURSE_FIELD.COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.COURSE_FIELD.COURSE_NO + " TEXT," +
                SchoolBoxDBTable.COURSE_FIELD.COURSE_TITLE + " TEXT," +
                SchoolBoxDBTable.COURSE_FIELD.COURSE_INSTRUCTOR + " TEXT," +
                SchoolBoxDBTable.COURSE_FIELD.COURSE_VENUE + " TEXT," +
                SchoolBoxDBTable.COURSE_FIELD.COURSE_TIME + " TEXT, " +
                SchoolBoxDBTable.COURSE_FIELD.COURSE_DATE + " DATE, " +
                SchoolBoxDBTable.COURSE_FIELD.COURSE_STATUS + " TEXT, " +
                "UNIQUE ("+SchoolBoxDBTable.COURSE_FIELD.COURSE_ID+
                ")"+");");

        db.execSQL("Create table if not exists " + SchoolBoxDBTable.QUIZ_TABLE +
                "(" + SchoolBoxDBTable.QUIZ_FIELD.QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.QUIZ_FIELD.QUIZ_NO + " TEXT," +
                SchoolBoxDBTable.QUIZ_FIELD.QUIZ_TITLE + " TEXT," +
                SchoolBoxDBTable.QUIZ_FIELD.QUIZ_DATE + " DATE," +
                SchoolBoxDBTable.QUIZ_FIELD.QUIZ_VENUE + " TEXT, " +
                SchoolBoxDBTable.QUIZ_FIELD.QUIZ_TIME + " TEXT, " +
                SchoolBoxDBTable.QUIZ_FIELD.QUIZ_STATUS + " TEXT, " +
                "UNIQUE ("+SchoolBoxDBTable.QUIZ_FIELD.QUIZ_ID+
                ")"+");");

        db.execSQL("Create table if not exists " + SchoolBoxDBTable.TEST_TABLE +
                "(" + SchoolBoxDBTable.TEST_FIELD.TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.TEST_FIELD.TEST_NO + " TEXT," +
                SchoolBoxDBTable.TEST_FIELD.TEST_TITLE + " TEXT," +
                SchoolBoxDBTable.TEST_FIELD.TEST_DATE + " DATE," +
                SchoolBoxDBTable.TEST_FIELD.TEST_VENUE + " TEXT, " +
                SchoolBoxDBTable.TEST_FIELD.TEST_TIME + " TEXT, " +
                SchoolBoxDBTable.TEST_FIELD.TEST_STATUS + " TEXT, " +
                "UNIQUE ("+SchoolBoxDBTable.TEST_FIELD.TEST_ID+
                ")"+");");

        db.execSQL("Create table if not exists " + SchoolBoxDBTable.ASSIGNMENT_TABLE +
                "(" + SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_NO + " TEXT," +
                SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_TITLE + " TEXT," +
                SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_DUE_DATE + " DATE," +
                SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_SUB_VENUE + " TEXT, " +
                SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_STATUS + " TEXT, " +
                "UNIQUE ("+SchoolBoxDBTable.ASSIGNMENT_FIELD.ASSIGNMENT_ID+
                ")"+");");

        db.execSQL("Create table if not exists " + SchoolBoxDBTable.PRESENTATION_TABLE +
                "(" + SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_NO + " TEXT," +
                SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_TITLE + " TEXT," +
                SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_DATE + " DATE," +
                SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_VENUE + " TEXT, " +
                SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_TIME + " TEXT, " +
                SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_STATUS + " TEXT, " +
                "UNIQUE ("+SchoolBoxDBTable.PRESENTATION_FIELD.PRESENTATION_ID+
                ")"+");");

        db.execSQL("Create table if not exists " + SchoolBoxDBTable.NOTE_TABLE +
                "(" + SchoolBoxDBTable.NOTE_FIELD.NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.NOTE_FIELD.NOTE_NO + " TEXT," +
                SchoolBoxDBTable.NOTE_FIELD.NOTE_TITLE+ " TEXT," +
                SchoolBoxDBTable.NOTE_FIELD.NOTE_DESCRIPTION + " TEXT," +
                SchoolBoxDBTable.NOTE_FIELD.NOTE_TIME + " TEXT," +
                SchoolBoxDBTable.NOTE_FIELD.NOTE_DATE + " DATE, " +
                "UNIQUE ("+SchoolBoxDBTable.NOTE_FIELD.NOTE_ID+
                ")"+");");

        db.execSQL("Create table if not exists " + SchoolBoxDBTable.EXAM_TABLE +
                "(" + SchoolBoxDBTable.EXAM_FIELD.EXAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SchoolBoxDBTable.EXAM_FIELD.EXAM_NO + " TEXT," +
                SchoolBoxDBTable.EXAM_FIELD.EXAM_TITLE + " TEXT," +
                SchoolBoxDBTable.EXAM_FIELD.EXAM_VENUE + " TEXT," +
                SchoolBoxDBTable.EXAM_FIELD.EXAM_DATE + " DATE," +
                SchoolBoxDBTable.EXAM_FIELD.EXAM_TIME + " TEXT, " +
                SchoolBoxDBTable.EXAM_FIELD.EXAM_STATUS + " TEXT, " +
                "UNIQUE ("+SchoolBoxDBTable.EXAM_FIELD.EXAM_ID+
                ")"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.SCHOOL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.COURSE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.QUIZ_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.TEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.ASSIGNMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.PRESENTATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.NOTE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SchoolBoxDBTable.EXAM_TABLE);

    }
}
