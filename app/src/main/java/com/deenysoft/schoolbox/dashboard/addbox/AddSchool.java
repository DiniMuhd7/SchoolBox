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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.deenysoft.schoolbox.dashboard.model.SchoolBoxItem;
import com.deenysoft.schoolbox.nest.ui.SettingsActivity;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;

/**
 * Created by shamsadam on 12/06/16.
 */
public class AddSchool extends BaseActivity {

    private ImageView mImageButton;
    private TextInputEditText inputSchoolName, inputSchoolCardNo, inputSchoolMajor, inputSchoolStartDate, inputSchoolEndDate, inputSchoolLocation;
    private TextInputLayout inputLayoutSchoolName, inputLayoutSchoolCardNo, inputLayoutSchoolMajor, inputLayoutSchoolStartDate, inputLayoutSchoolEndDate, inputLayoutSchoolLocation;
    private AppCompatRadioButton mRadioButtonStudent, mRadioButtonStaff, mRadioButtonGraduated, mRadioButtonResigned;
    private Button mCommitButton;

    private SchoolBoxItem mSchoolBoxItem;
    private SchoolBoxDBManager mSchoolBoxDBManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);
        setContentView(R.layout.add_school_activity);
        setupToolbar();

        // Init
        mRadioButtonStudent = (AppCompatRadioButton) findViewById(R.id.radio_student);
        mRadioButtonStaff = (AppCompatRadioButton) findViewById(R.id.radio_staff);
        mRadioButtonGraduated = (AppCompatRadioButton) findViewById(R.id.radio_graduated);
        mRadioButtonResigned = (AppCompatRadioButton) findViewById(R.id.radio_resigned);

        inputLayoutSchoolCardNo = (TextInputLayout) findViewById(R.id.input_layout_school_id);
        inputLayoutSchoolName = (TextInputLayout) findViewById(R.id.input_layout_school_name);
        inputLayoutSchoolMajor = (TextInputLayout) findViewById(R.id.input_layout_school_major);
        inputLayoutSchoolStartDate = (TextInputLayout) findViewById(R.id.input_layout_school_startdate);
        inputLayoutSchoolEndDate = (TextInputLayout) findViewById(R.id.input_layout_school_enddate);
        inputLayoutSchoolLocation = (TextInputLayout) findViewById(R.id.input_layout_school_location);

        inputSchoolCardNo = (TextInputEditText) findViewById(R.id.input_school_id);
        inputSchoolName = (TextInputEditText) findViewById(R.id.input_school_name);
        inputSchoolMajor = (TextInputEditText) findViewById(R.id.input_school_major);
        inputSchoolStartDate = (TextInputEditText) findViewById(R.id.input_school_startdate);
        inputSchoolEndDate = (TextInputEditText) findViewById(R.id.input_school_enddate);
        inputSchoolLocation = (TextInputEditText) findViewById(R.id.input_school_location);

        // Objects Init
        mSchoolBoxItem = new SchoolBoxItem();
        mSchoolBoxDBManager = new SchoolBoxDBManager();

        mCommitButton = (Button) findViewById(R.id.commitButton);
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get School Info
                String mInputSchoolCardNo = inputSchoolCardNo.getText().toString().trim();
                String mInputSchoolName = inputSchoolName.getText().toString().trim();
                String mInputSchoolMajor = inputSchoolMajor.getText().toString().trim();
                String mInputSchoolStartDate = inputSchoolStartDate.getText().toString().trim();
                String mInputSchoolEndDate = inputSchoolEndDate.getText().toString().trim();
                String mInputSchoolLocation = inputSchoolLocation.getText().toString().trim();
                if (!mInputSchoolCardNo.isEmpty() && !mInputSchoolName.isEmpty() && !mInputSchoolMajor.isEmpty() && !mInputSchoolStartDate.isEmpty() && !mInputSchoolEndDate.isEmpty() && !mInputSchoolLocation.isEmpty()) {
                    // Pass to SchoolBoxItem Class
                    mSchoolBoxItem.setSchoolCardNo(mInputSchoolCardNo);
                    mSchoolBoxItem.setSchoolTitle(mInputSchoolName);
                    mSchoolBoxItem.setSchoolMajor(mInputSchoolMajor);
                    mSchoolBoxItem.setStartDate(mInputSchoolStartDate);
                    mSchoolBoxItem.setEndDate(mInputSchoolEndDate);
                    mSchoolBoxItem.setSchoolLocation(mInputSchoolLocation);
                    // Push to SchoolBoxDBManager SQLite Database
                    mSchoolBoxDBManager.addSchoolItem(mSchoolBoxItem); // Stored
                    //Toast.makeText(AddSchool.this, ""+mSchoolBoxDBManager.getSchoolItem(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddSchool.this, "Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddSchool.this, DashboardActivity.class)); // Launch Activity
                } else {
                    Toast.makeText(AddSchool.this, "Error! Text field should not be left blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_student:
                        if (checked) {
                            String mStatus = mRadioButtonStudent.getText().toString().trim();
                            // Pass to SchoolBoxItem Class
                            mSchoolBoxItem.setSchoolStatus(mStatus);
                            Toast.makeText(AddSchool.this, mSchoolBoxItem.getSchoolStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_staff:
                        if (checked) {
                            String mStatus = mRadioButtonStaff.getText().toString().trim();
                            // Pass to SchoolBoxItem Class
                            mSchoolBoxItem.setSchoolStatus(mStatus);
                            Toast.makeText(AddSchool.this, mSchoolBoxItem.getSchoolStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonGraduated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_graduated:
                        if (checked) {
                            String mStatus = mRadioButtonGraduated.getText().toString().trim();
                            // Pass to SchoolBoxItem Class
                            mSchoolBoxItem.setSchoolStatus(mStatus);
                            Toast.makeText(AddSchool.this, mSchoolBoxItem.getSchoolStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonResigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_resigned:
                        if (checked) {
                            String mStatus = mRadioButtonResigned.getText().toString().trim();
                            // Pass to SchoolBoxItem Class
                            mSchoolBoxItem.setSchoolStatus(mStatus);
                            Toast.makeText(AddSchool.this, mSchoolBoxItem.getSchoolStatus(), Toast.LENGTH_SHORT).show();
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
