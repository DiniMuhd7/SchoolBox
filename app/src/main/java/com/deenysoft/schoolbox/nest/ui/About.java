package com.deenysoft.schoolbox.nest.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.AvatarView;
import com.deenysoft.schoolbox.widget.ThemeUtil;

/**
 * Created by shamsadam on 28/05/16.
 */
public class About extends BaseActivity {

    private Button mButton1, mButton2, mButton3, mButton4;
    private ImageView mImageButton;
    private String deenURL = "https://twitter.com/deenadem";
    private String creditGithub = "https://github.com/PaoloRotolo/AppIntro";
    private String deenGithub = "https://github.com/Deen-Adam";
    private String freepikURL = "http://www.freepik.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);
        setContentView(R.layout.sub_about);
        setupToolbar();


        mButton1 = (Button) findViewById(R.id.twitterButton);
        mButton2 = (Button) findViewById(R.id.creditGithubButton);
        mButton3 = (Button) findViewById(R.id.deenGithubButton);
        mButton4 = (Button) findViewById(R.id.freepikButton);

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


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.twitterButton:
                        // Do Something
                        Intent mTwitter = new Intent(Intent.ACTION_VIEW);
                        mTwitter.setData(Uri.parse(deenURL));
                        startActivity(mTwitter);
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not be implemented");
                }
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.creditGithubButton:
                        // Do Something
                        Intent mGithub = new Intent(Intent.ACTION_VIEW);
                        mGithub.setData(Uri.parse(creditGithub));
                        startActivity(mGithub);
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not be implemented");
                }
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.deenGithubButton:
                        // Do Something
                        Intent mDeenGithub = new Intent(Intent.ACTION_VIEW);
                        mDeenGithub.setData(Uri.parse(deenGithub));
                        startActivity(mDeenGithub);
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not be implemented");
                }
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.freepikButton:
                        // Do Something
                        Intent mFreePik = new Intent(Intent.ACTION_VIEW);
                        mFreePik.setData(Uri.parse(freepikURL));
                        startActivity(mFreePik);
                        break;
                    default:
                        throw new UnsupportedOperationException("The onClick method has not be implemented");
                }
            }
        });


        final AvatarView avatarView = (AvatarView) findViewById(R.id.avatar);
        final AvatarView avatarView1 = (AvatarView) findViewById(R.id.avatar1);
        final AvatarView avatarView2 = (AvatarView) findViewById(R.id.avatar2);
        avatarView.setAvatar(R.drawable.avatar_4_raster);
        avatarView1.setAvatar(R.drawable.avatar_3_raster);
        avatarView2.setAvatar(R.drawable.schoolbox);


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
