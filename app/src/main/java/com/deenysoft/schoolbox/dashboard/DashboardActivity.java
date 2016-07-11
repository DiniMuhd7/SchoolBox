package com.deenysoft.schoolbox.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.dashboard.adapter.DialogImageAdapter;
import com.deenysoft.schoolbox.dashboard.addbox.AddAssignment;
import com.deenysoft.schoolbox.dashboard.addbox.AddCourse;
import com.deenysoft.schoolbox.dashboard.addbox.AddExam;
import com.deenysoft.schoolbox.dashboard.addbox.AddNote;
import com.deenysoft.schoolbox.dashboard.addbox.AddPresentation;
import com.deenysoft.schoolbox.dashboard.addbox.AddQuiz;
import com.deenysoft.schoolbox.dashboard.addbox.AddSchool;
import com.deenysoft.schoolbox.dashboard.addbox.AddTest;
import com.deenysoft.schoolbox.firebase.AuthLoginActivity;
import com.deenysoft.schoolbox.firebase.stack.ActivityMainStack;
import com.deenysoft.schoolbox.firebase.cloud.UploadMainActivity;
import com.deenysoft.schoolbox.nest.ui.SettingsActivity;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deen Adam on 07/06/16.
 */
public class DashboardActivity extends BaseActivity {

    private FloatingActionButton mFloatingActionButton;
    private ImageView mImageButton;
    private FirebaseAuth mAuth;

    private static final String EXTRA_EDIT = "EDIT";

    public static void start(Activity activity, Boolean edit) {
        Intent starter = new Intent(activity, DashboardActivity.class);
        starter.putExtra(EXTRA_EDIT, edit);
        //noinspection unchecked
        ActivityCompat.startActivity(activity,
                starter,
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);
        setContentView(R.layout.dashboard_activity);
        setupToolbar();

        mAuth = FirebaseAuth.getInstance();

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {
                    //  Launch app intro
                    Intent i = new Intent(DashboardActivity.this, AuthLoginActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();
                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);
                    //  Apply changes
                    e.apply();

                }

            }
        });

        // Start the thread
        t.start();


        // Create the School Fragment and add it as main container for the activity.
        FragmentManager mFragManagerSchool = getSupportFragmentManager();
        if (mFragManagerSchool.findFragmentById(android.R.id.content) == null) {
            DashboardSchoolFragment DashSchoolFrag = new DashboardSchoolFragment();
            mFragManagerSchool.beginTransaction().replace(R.id.dash_container, DashSchoolFrag).commit();
        }


        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fab_add:
                        //Calling function Alert Dialog
                        AlertDialog();;
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not be implemented");
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



    // Function AlertDialog
    public void AlertDialog() {
        // List of numbers up to five. -- Not Use | No longer Needed --
        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 1; i < 6; i++) {
            mList.add(i);
        }
        //gridView.setAdapter(new ArrayListAdapter(this, android.R.layout.simple_list_item_1, mList));  -- Not Use --


        // Prepare Dialog View and Grid View
        View mView = getLayoutInflater().inflate(R.layout.dashboard_dialog, null);
        GridView mGridView = (GridView) mView.findViewById(R.id.gridView);
        mGridView.setAdapter(new DialogImageAdapter(this));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do Something
                switch (position) {
                    case 0:
                        startActivity(new Intent(DashboardActivity.this, AddSchool.class));
                        break;
                    case 1:
                        startActivity(new Intent(DashboardActivity.this, AddCourse.class));
                        break;
                    case 2:
                        startActivity(new Intent(DashboardActivity.this, AddTest.class));
                        break;
                    case 3:
                        startActivity(new Intent(DashboardActivity.this, AddQuiz.class));
                        break;
                    case 4:
                        startActivity(new Intent(DashboardActivity.this, AddAssignment.class));
                        break;
                    case 5:
                        startActivity(new Intent(DashboardActivity.this, AddPresentation.class));
                        break;
                    case 6:
                        startActivity(new Intent(DashboardActivity.this, AddExam.class));
                        break;
                    case 7:
                        startActivity(new Intent(DashboardActivity.this, AddNote.class));
                        break;
                    default:
                }
            }
        });

        // Set view to alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(mView);
        //builder.setTitle("ADD");
        builder.show();

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check auth on Activity start
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(DashboardActivity.this, AuthLoginActivity.class));
        }
    }


    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
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
                openDrawer();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_school:
                // Create the School Fragment and add it as main container for the activity.
                FragmentManager mFragManagerSchool = getSupportFragmentManager();
                if (mFragManagerSchool.findFragmentById(android.R.id.content) == null) {
                    DashboardSchoolFragment DashSchoolFrag = new DashboardSchoolFragment();
                    mFragManagerSchool.beginTransaction().replace(R.id.dash_container, DashSchoolFrag).commit();
                }
                return true;
            case R.id.action_course:
                // Create the Course Fragment and add it as main container for the activity.
                FragmentManager mFragManagerCourse = getSupportFragmentManager();
                if (mFragManagerCourse.findFragmentById(android.R.id.content) == null) {
                    DashboardCourseFragment DashCourseFrag = new DashboardCourseFragment();
                    mFragManagerCourse.beginTransaction().replace(R.id.dash_container, DashCourseFrag).commit();
                }
                return true;
            case R.id.action_quiz:
                // Create the Quiz Fragment and add it as main container for the activity.
                FragmentManager mFragManagerQuiz = getSupportFragmentManager();
                if (mFragManagerQuiz.findFragmentById(android.R.id.content) == null) {
                    DashboardQuizFragment DashQuizFrag = new DashboardQuizFragment();
                    mFragManagerQuiz.beginTransaction().replace(R.id.dash_container, DashQuizFrag).commit();
                }
                return true;
            case R.id.action_test:
                // Create the Test Fragment and add it as main container for the activity.
                FragmentManager mFragManagerTest = getSupportFragmentManager();
                if (mFragManagerTest.findFragmentById(android.R.id.content) == null) {
                    DashboardTestFragment DashTestFrag = new DashboardTestFragment();
                    mFragManagerTest.beginTransaction().replace(R.id.dash_container, DashTestFrag).commit();
                }
                return true;
            case R.id.action_note:
                // Create the Note Fragment and add it as main container for the activity.
                FragmentManager mFragManagerNote = getSupportFragmentManager();
                if (mFragManagerNote.findFragmentById(android.R.id.content) == null) {
                    DashboardNoteFragment DashNoteFrag = new DashboardNoteFragment();
                    mFragManagerNote.beginTransaction().replace(R.id.dash_container, DashNoteFrag).commit();
                }
                return true;
            case R.id.action_exam:
                // Create the Exam Fragment and add it as main container for the activity.
                FragmentManager mFragManagerExam = getSupportFragmentManager();
                if (mFragManagerExam.findFragmentById(android.R.id.content) == null) {
                    DashboardExamFragment DashExamFrag = new DashboardExamFragment();
                    mFragManagerExam.beginTransaction().replace(R.id.dash_container, DashExamFrag).commit();
                }
                return true;
            case R.id.action_assignment:
                // Create the Assignment Fragment and add it as main container for the activity.
                FragmentManager mFragManagerAssignment = getSupportFragmentManager();
                if (mFragManagerAssignment.findFragmentById(android.R.id.content) == null) {
                    DashboardAssignmentFragment DashAssignmentFrag = new DashboardAssignmentFragment();
                    mFragManagerAssignment.beginTransaction().replace(R.id.dash_container, DashAssignmentFrag).commit();
                }
                return true;
            case R.id.action_presentation:
                // Create the Presentation Fragment and add it as main container for the activity.
                FragmentManager mFragManagerPresentation = getSupportFragmentManager();
                if (mFragManagerPresentation.findFragmentById(android.R.id.content) == null) {
                    DashboardPresentationFragment DashPresentationFrag = new DashboardPresentationFragment();
                    mFragManagerPresentation.beginTransaction().replace(R.id.dash_container, DashPresentationFrag).commit();
                }
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_dash;
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }



}
