package com.deenysoft.schoolbox.dashboard.addbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.dashboard.DashboardActivity;
import com.deenysoft.schoolbox.dashboard.DashboardAssignmentFragment;
import com.deenysoft.schoolbox.dashboard.DashboardCourseFragment;
import com.deenysoft.schoolbox.dashboard.DashboardExamFragment;
import com.deenysoft.schoolbox.dashboard.DashboardNoteFragment;
import com.deenysoft.schoolbox.dashboard.DashboardPresentationFragment;
import com.deenysoft.schoolbox.dashboard.DashboardQuizFragment;
import com.deenysoft.schoolbox.dashboard.DashboardSchoolFragment;
import com.deenysoft.schoolbox.dashboard.DashboardTestFragment;
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDBManager;
import com.deenysoft.schoolbox.dashboard.model.CourseBoxItem;
import com.deenysoft.schoolbox.nest.ui.SettingsActivity;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;

/**
 * Created by shamsadam on 22/06/16.
 */
public class AddCourse extends BaseActivity {

    private ImageView mImageButton;
    private TextInputEditText inputCourseTitle, inputCourseNo, inputCourseInstructor, inputCourseDate, inputCourseTime, inputCourseVenue;
    private TextInputLayout inputLayoutCourseTitle, inputLayoutCourseNo, inputLayoutCourseInstructor, inputLayoutCourseDate, inputLayoutCourseTime, inputLayoutCourseVenue;
    private AppCompatRadioButton mRadioButtonTaken, mRadioButtonGoing, mRadioButtonNotYet;
    private Button mCommitButton;

    private CourseBoxItem mCourseBoxItem;
    private SchoolBoxDBManager mSchoolBoxDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);

        setContentView(R.layout.add_course_activity);
        setupToolbar();

        // Init
        mRadioButtonTaken = (AppCompatRadioButton) findViewById(R.id.radio_taken);
        mRadioButtonGoing = (AppCompatRadioButton) findViewById(R.id.radio_going);
        mRadioButtonNotYet = (AppCompatRadioButton) findViewById(R.id.radio_not_yet);

        inputLayoutCourseNo = (TextInputLayout) findViewById(R.id.input_layout_course_no);
        inputLayoutCourseTitle = (TextInputLayout) findViewById(R.id.input_layout_course_title);
        inputLayoutCourseInstructor = (TextInputLayout) findViewById(R.id.input_layout_course_instructor);
        inputLayoutCourseDate = (TextInputLayout) findViewById(R.id.input_layout_course_date);
        inputLayoutCourseVenue = (TextInputLayout) findViewById(R.id.input_layout_course_venue);
        inputLayoutCourseTime = (TextInputLayout) findViewById(R.id.input_layout_course_time);

        inputCourseNo = (TextInputEditText) findViewById(R.id.input_course_no);
        inputCourseTitle = (TextInputEditText) findViewById(R.id.input_course_title);
        inputCourseInstructor = (TextInputEditText) findViewById(R.id.input_course_instructor);
        inputCourseDate = (TextInputEditText) findViewById(R.id.input_course_date);
        inputCourseTime = (TextInputEditText) findViewById(R.id.input_course_time);
        inputCourseVenue = (TextInputEditText) findViewById(R.id.input_course_venue);

        // Objects Init
        mCourseBoxItem = new CourseBoxItem();
        mSchoolBoxDBManager = new SchoolBoxDBManager();

        mCommitButton = (Button) findViewById(R.id.commitButton);
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Course Info
                String mInputCourseNo = inputCourseNo.getText().toString().trim();
                String mInputCourseTitle = inputCourseTitle.getText().toString().trim();
                String mInputCourseInstructor = inputCourseInstructor.getText().toString().trim();
                String mInputCourseDate = inputCourseDate.getText().toString().trim();
                String mInputCourseTime = inputCourseTime.getText().toString().trim();
                String mInputCourseVenue = inputCourseVenue.getText().toString().trim();
                if (!mInputCourseNo.isEmpty() && !mInputCourseTitle.isEmpty() && !mInputCourseInstructor.isEmpty() && !mInputCourseDate.isEmpty() && !mInputCourseTime.isEmpty() && !mInputCourseVenue.isEmpty()) {
                    // Pass to CourseBoxItem Class
                    mCourseBoxItem.setCourseID(mInputCourseNo);
                    mCourseBoxItem.setCourseTitle(mInputCourseTitle);
                    mCourseBoxItem.setCourseInstructor(mInputCourseInstructor);
                    mCourseBoxItem.setCourseDate(mInputCourseDate);
                    mCourseBoxItem.setCourseTime(mInputCourseTime);
                    mCourseBoxItem.setCourseVenue(mInputCourseVenue);
                    // Push to SchoolBoxDBManager SQLite Database
                    mSchoolBoxDBManager.addCourseItem(mCourseBoxItem); // Stored
                    //Toast.makeText(AddCourse.this, ""+mSchoolBoxDBManager.getCourseItem(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddCourse.this, "Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddCourse.this, DashboardActivity.class));
                } else {
                    Toast.makeText(AddCourse.this, "Error! Text field should not be left blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_taken:
                        if (checked) {
                            String mStatus = mRadioButtonTaken.getText().toString().trim();
                            // Pass to CourseBoxItem Class
                            mCourseBoxItem.setCourseStatus(mStatus);
                            Toast.makeText(AddCourse.this, mCourseBoxItem.getCourseStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_going:
                        if (checked) {
                            String mStatus = mRadioButtonGoing.getText().toString().trim();
                            // Pass to CourseBoxItem Class
                            mCourseBoxItem.setCourseStatus(mStatus);
                            Toast.makeText(AddCourse.this, mCourseBoxItem.getCourseStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonNotYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_not_yet:
                        if (checked) {
                            String mStatus = mRadioButtonNotYet.getText().toString().trim();
                            // Pass to CourseBoxItem Class
                            mCourseBoxItem.setCourseStatus(mStatus);
                            Toast.makeText(AddCourse.this, mCourseBoxItem.getCourseStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });


        mImageButton = (ImageView) findViewById(R.id.share);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Add data to the intent, the receiving app will decide what to do with it.
                intent.putExtra(Intent.EXTRA_SUBJECT, "Get SchoolBox App for Android");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, try SchoolBox App for Android. It provides great educational contents. Get it on Google Play - https://play.google.com/store/apps/developer?id=Deenysoft+Inc");

                startActivity(Intent.createChooser(intent, "How do you want to share?"));
                finish();
            }
        });
    }


    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_school:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_course:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_quiz:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_test:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_note:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_exam:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_assignment:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_presentation:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

}
