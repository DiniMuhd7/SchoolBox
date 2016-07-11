package com.deenysoft.schoolbox.dashboard.database;

/**
 * Created by shamsadam on 07/06/16.
 */
public class SchoolBoxDBTable {

    // SchoolBox Tables Init
    public static final String SCHOOL_TABLE = "school_table";
    public static final String COURSE_TABLE = "course_table";
    public static final String QUIZ_TABLE = "quiz_table";
    public static final String TEST_TABLE = "test_table";
    public static final String ASSIGNMENT_TABLE = "assignment_table";
    public static final String PRESENTATION_TABLE = "presentation_table";
    public static final String NOTE_TABLE = "note_table";
    public static final String EXAM_TABLE = "exam_table";


    // School Field
    public static class SCHOOL_FIELD {
        public static final String SCHOOL_ID = "_id"; // Primary Key Init
        public static final String SCHOOL_CARDNO = "school_cardno";
        public static final String SCHOOL_TITLE = "school_title";
        public static final String SCHOOL_MAJOR = "school_major";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String SCHOOL_LOCATION = "school_location";
        public static final String SCHOOL_STATUS = "status";

    }

    // Course Field
    public static class COURSE_FIELD {
        public static final String COURSE_ID = "_id"; // Primary Key Init
        public static final String COURSE_NO = "course_no";
        //public static final String SCHOOL_ID = "school_id"; // Foreign Key Init
        public static final String COURSE_TITLE = "course_title";
        public static final String COURSE_INSTRUCTOR = "course_instructor";
        public static final String COURSE_VENUE = "course_venue";
        public static final String COURSE_TIME = "course_time";
        public static final String COURSE_DATE = "course_date";
        public static final String COURSE_STATUS = "course_status";

    }

    // Quiz Field
    public static class QUIZ_FIELD {
        public static final String QUIZ_ID = "_id"; // Primary Key Init
        public static final String QUIZ_NO = "quiz_no";
        //public static final String COURSE_ID = "course_id"; // Foreign Key Init
        public static final String QUIZ_TITLE = "quiz_title";
        public static final String QUIZ_DATE = "quiz_date";
        public static final String QUIZ_VENUE = "quiz_venue";
        public static final String QUIZ_TIME = "quiz_time";
        public static final String QUIZ_STATUS = "quiz_status";

    }

    // Test Field
    public static class TEST_FIELD {
        public static final String TEST_ID = "_id"; // Primary Key Init
        public static final String TEST_NO = "test_no";
        //public static final String COURSE_ID = "course_id"; // Foreign Key Init
        public static final String TEST_TITLE = "test_title";
        public static final String TEST_DATE = "test_date";
        public static final String TEST_VENUE = "test_venue";
        public static final String TEST_TIME = "test_time";
        public static final String TEST_STATUS = "test_status";

    }

    // Assignment Field
    public static class ASSIGNMENT_FIELD {
        public static final String ASSIGNMENT_ID = "_id"; // Primary Key Init
        public static final String ASSIGNMENT_NO = "assignment_no";
        //public static final String COURSE_ID = "course_id"; // Foreign Key Init
        public static final String ASSIGNMENT_TITLE = "assignment_title";
        public static final String ASSIGNMENT_DUE_DATE = "assignment_due_date";
        public static final String ASSIGNMENT_SUB_VENUE = "assignment_submission_venue";
        public static final String ASSIGNMENT_STATUS = "assignment_status";

    }

    // Presentation Field
    public static class PRESENTATION_FIELD {
        public static final String PRESENTATION_ID = "_id"; // Primary Key Init
        public static final String PRESENTATION_NO = "presentation_no";
        //public static final String COURSE_ID = "course_id"; // Foreign Key Init
        public static final String PRESENTATION_TITLE = "presentation_title";
        public static final String PRESENTATION_DATE = "presentation_date";
        public static final String PRESENTATION_VENUE = "presentation_venue";
        public static final String PRESENTATION_TIME = "presentation_time";
        public static final String PRESENTATION_STATUS = "presentation_status";

    }

    // Note Field
    public static class NOTE_FIELD {
        public static final String NOTE_ID = "_id"; // Primary Key Init
        public static final String NOTE_NO = "note_no";
        //public static final String COURSE_ID = "course_id"; // Foreign Key Init
        public static final String NOTE_TITLE = "note_title";
        public static final String NOTE_DESCRIPTION = "note_description";
        public static final String NOTE_DATE = "note_date";
        public static final String NOTE_TIME = "note_time";

    }

    // Exam Field
    public static class EXAM_FIELD {
        public static final String EXAM_ID = "_id"; // Primary Key Init
        public static final String EXAM_NO = "exam_no";
        public static final String EXAM_TITLE = "exam_title";
        public static final String EXAM_VENUE = "exam_venue";
        public static final String EXAM_DATE = "exam_date";
        public static final String EXAM_TIME = "exam_time";
        public static final String EXAM_STATUS = "exam_status";

    }

}
