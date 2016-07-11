package com.deenysoft.schoolbox.dashboard.detail;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

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
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDatabase;
import com.deenysoft.schoolbox.dashboard.detail.adapter.PresentationDetailAdapter;
import com.deenysoft.schoolbox.dashboard.model.PresentationBoxItem;
import com.deenysoft.schoolbox.firebase.cloud.UploadMainActivity;
import com.deenysoft.schoolbox.nest.ui.SettingsActivity;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;

/**
 * Created by shamsadam on 26/06/16.
 */
public class PresentationActivityDetail extends BaseActivity {

    private ImageView mImageButton;
    private SchoolBoxDBManager mSchoolBoxDBManager; // Dumped

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_detail_presentation);
        setupToolbar();

        mSchoolBoxDBManager = new SchoolBoxDBManager();

        // SchoolBoxDatabase is a SQLiteOpenHelper class connecting to SQLite
        SchoolBoxDatabase mSchoolBoxDatabase = new SchoolBoxDatabase(this);
        // Get access to the underlying writable database
        SQLiteDatabase mSQLiteDatabase = mSchoolBoxDatabase.getWritableDatabase();
        // Query for items from the database and get a cursor back
        Cursor mCursor = mSQLiteDatabase.rawQuery("SELECT * FROM presentation_table ", null);
        //Cursor mCursor = mSQLiteDatabase.rawQuery("SELECT * FROM school_table WHERE TRIM(school_cardno)='"+mSchoolBoxDBManager.getSchoolCardNo().trim()+"'ORDER BY school_cardno", null); // What a crazy combination i got here!!
        // Find ListView to populate
        ListView mListView = (ListView) findViewById(R.id.present_item_list);
        // Setup adapter using cursor from last step
        PresentationDetailAdapter mPresentationDetailAdapter = new PresentationDetailAdapter(this, mCursor);
        // Attach adapter containing cursor to the ListView
        mListView.setAdapter(mPresentationDetailAdapter);
        mPresentationDetailAdapter.changeCursor(mCursor);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatUploadButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PresentationActivityDetail.this, UploadMainActivity.class));
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

    // Activity Intent Starter
    public static Intent getStartIntent(Context context, PresentationBoxItem mPresentationBoxItem) {
        Intent starter = new Intent(context, PresentationActivityDetail.class);
        starter.putExtra(PresentationBoxItem.TAG, mPresentationBoxItem.getPresentationID());
        return starter;
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
