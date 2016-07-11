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
import com.deenysoft.schoolbox.dashboard.model.NoteBoxItem;
import com.deenysoft.schoolbox.nest.ui.SettingsActivity;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;

/**
 * Created by shamsadam on 24/06/16.
 */
public class AddNote extends BaseActivity {

    private ImageView mImageButton;
    private TextInputEditText inputNoteTitle, inputNoteNo, inputNoteDescription, inputNoteDate, inputNoteTime;
    private TextInputLayout inputLayoutNoteTitle, inputLayoutNoteNo, inputLayoutNoteDescription, inputLayoutNoteDate, inputLayoutNoteTime;
    private Button mCommitButton;

    private NoteBoxItem mNoteBoxItem;
    private SchoolBoxDBManager mSchoolBoxDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);

        setContentView(R.layout.add_note_activity);
        setupToolbar();

        // Init
        inputLayoutNoteNo = (TextInputLayout) findViewById(R.id.input_layout_note_no);
        inputLayoutNoteTitle = (TextInputLayout) findViewById(R.id.input_layout_note_title);
        inputLayoutNoteDescription = (TextInputLayout) findViewById(R.id.input_layout_note_description);
        inputLayoutNoteDate = (TextInputLayout) findViewById(R.id.input_layout_note_date);
        inputLayoutNoteTime = (TextInputLayout) findViewById(R.id.input_layout_note_time);

        inputNoteNo = (TextInputEditText) findViewById(R.id.input_note_no);
        inputNoteTitle = (TextInputEditText) findViewById(R.id.input_note_title);
        inputNoteDescription = (TextInputEditText) findViewById(R.id.input_note_description);
        inputNoteDate = (TextInputEditText) findViewById(R.id.input_note_date);
        inputNoteTime = (TextInputEditText) findViewById(R.id.input_note_time);


        // Objects Init
        mNoteBoxItem = new NoteBoxItem();
        mSchoolBoxDBManager = new SchoolBoxDBManager();

        mCommitButton = (Button) findViewById(R.id.commitButton);
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Note Info
                String mInputNoteNo = inputNoteNo.getText().toString().trim();
                String mInputNoteTitle = inputNoteTitle.getText().toString().trim();
                String mInputNoteDescription = inputNoteDescription.getText().toString().trim();
                String mInputNoteDate = inputNoteDate.getText().toString().trim();
                String mInputNoteTime = inputNoteTime.getText().toString().trim();
                if (!mInputNoteNo.isEmpty() && !mInputNoteTitle.isEmpty() && !mInputNoteDescription.isEmpty() && !mInputNoteDate.isEmpty() && !mInputNoteTime.isEmpty()) {
                    // Pass to NoteBoxItem Class
                    mNoteBoxItem.setNoteID(mInputNoteNo);
                    mNoteBoxItem.setNoteTitle(mInputNoteTitle);
                    mNoteBoxItem.setNoteDescription(mInputNoteDescription);
                    mNoteBoxItem.setNoteDate(mInputNoteDate);
                    mNoteBoxItem.setNoteTime(mInputNoteTime);
                    // Push to SchoolBoxDBManager SQLite Database
                    mSchoolBoxDBManager.addNoteItem(mNoteBoxItem); // Stored
                    //Toast.makeText(AddNote.this, ""+mSchoolBoxDBManager.getNoteItem(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddNote.this, "Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddNote.this, DashboardActivity.class));
                } else {
                    Toast.makeText(AddNote.this, "Error! Text field should not be left blank", Toast.LENGTH_SHORT).show();
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
