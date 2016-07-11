package com.deenysoft.schoolbox.nest.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.account.activity.RegisterActivity;
import com.deenysoft.schoolbox.dashboard.DashboardActivity;
import com.deenysoft.schoolbox.dashboard.DashboardAssignmentFragment;
import com.deenysoft.schoolbox.dashboard.DashboardCourseFragment;
import com.deenysoft.schoolbox.dashboard.DashboardExamFragment;
import com.deenysoft.schoolbox.dashboard.DashboardNoteFragment;
import com.deenysoft.schoolbox.dashboard.DashboardPresentationFragment;
import com.deenysoft.schoolbox.dashboard.DashboardQuizFragment;
import com.deenysoft.schoolbox.dashboard.DashboardSchoolFragment;
import com.deenysoft.schoolbox.dashboard.DashboardTestFragment;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by shamsadam on 05/04/16.
 */
public class SupportNav extends BaseActivity {

    private FloatingActionButton mFloatingActionButton;
    private Button mSignUpButton;
    private Button mCommunityButton;
    private ImageView mImageButton;

    private TextInputLayout mTextInputLayout;
    private TextInputEditText mTextInputEditText;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);

        setContentView(R.layout.support_activity);
        setupToolbar();

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatButton);
        mSignUpButton = (Button) findViewById(R.id.signUPButton);
        mCommunityButton = (Button) findViewById(R.id.communityButton);

        mTextInputLayout = (TextInputLayout) findViewById(R.id.editTextFeed);

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



        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.floatButton:
                        //Calling function alert dialog
                        AlertDialog();
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not be implemented");
                }
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.signUPButton:
                        startActivity(new Intent (SupportNav.this, RegisterActivity.class));
                        //Toast.makeText(getBaseContext(), "It works", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not been implemented");
                }
            }
        });

        mCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.communityButton:
                        startActivity(new Intent (SupportNav.this, Community.class));
                        //Toast.makeText(getBaseContext(), "It works", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not been implemented");
                }
            }
        });
    }


    // Alert Dialog builder function
    public void AlertDialog() {
        // Creating Alert Dialog Builder
        final View v = getLayoutInflater().inflate(R.layout.feedback_dialog, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setView(v)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive action goes here
                        //final EditText mFeedback = (EditText) v.findViewById(R.id.editFeedName);
                        mTextInputEditText = (TextInputEditText) v.findViewById(R.id.editFeedName);
                        String feedBack = mTextInputEditText.getText().toString().trim();
                        if (!feedBack.isEmpty()) {
                            // Sending feedback via email parcel
                            Intent mIntentEmail = new Intent(Intent.ACTION_SEND);
                            mIntentEmail.setType("message/rfc822");
                            mIntentEmail.putExtra(Intent.EXTRA_EMAIL, new String[] { "deenysoft@gmail.com" });
                            mIntentEmail.putExtra(Intent.EXTRA_SUBJECT, "SCHOOL-BOX FEEDBACK");
                            mIntentEmail.putExtra(Intent.EXTRA_TEXT, "" +feedBack);
                            try {
                                startActivity(Intent.createChooser(mIntentEmail, "Choose Email Client"));
                                finish();
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(SupportNav.this, "No email client available.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SupportNav.this, "Error! Text field should not be left blank.", Toast.LENGTH_SHORT).show();
                        }
                    }

                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do Nothing
                    }
                });

        AlertDialog mAlert = mBuilder.create();
        mAlert.show();

    }


    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
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

}
