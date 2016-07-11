package com.deenysoft.schoolbox.nest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;

/**
 * This Activity provides several settings. Activity contains {@link PreferenceFragment} as inner class.
 *
 * Created by Deen Adam on 14.06.2016.
 */
public class SettingsActivity extends BaseActivity {

    private ImageView mImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThemeUtil.onActivityCreateSetTheme(this);

        setContentView(R.layout.sub_activity_setting);
        setupToolbar();

        /*
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentSettings())
                .commit();
        */

        mImageButton=(ImageView) findViewById(R.id.share);
        mImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Add data to the intent, the receiving app will decide what to do with it.
                intent.putExtra(Intent.EXTRA_SUBJECT, "Get SchoolBox App for Android");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, try SchoolBox App for Android. It provides great educational contents. Get it on Google Play - https://play.google.com/store/apps/developer?id=Deenysoft+Inc");

                startActivity(Intent.createChooser(intent, "How do you want to share?"));
                finish();
            }
        }

        );
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


    public static class FragmentSettings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_prefs);

            final ListPreference mListTheme = (ListPreference) getPreferenceManager().findPreference("prefThemeColor");
            SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            final String mListPreference = mSharedPreferences.getString("prefThemeColor", "0");

            //final String[] index = {"Default Theme","Red Theme","Yellow Theme","Purple Theme"};

            // Preference Change Listener could be more useful for checkbox
            mListTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    return true;
                }
            });

            //SharedPreferences.Editor editor = mSharedPreferences.edit();
            //editor.putString("prefThemeColor", mListPreference);
            //editor.commit();

        }

        // Applying Themes
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String mTheme = sharedPreferences.getString("prefThemeColor", "0");
            if (mTheme.equals("0")) {
                ThemeUtil.changeToTheme(getActivity(), ThemeUtil.CUSTOM_THEME_BLUE);
            } else if (mTheme.equals("1")) {
                ThemeUtil.changeToTheme(getActivity(), ThemeUtil.CUSTOM_THEME_CYAN);
            } else if (mTheme.equals("2")){
                ThemeUtil.changeToTheme(getActivity(), ThemeUtil.CUSTOM_THEME_YELLOW);
            } else if (mTheme.equals("3")) {
                ThemeUtil.changeToTheme(getActivity(), ThemeUtil.CUSTOM_THEME_GREEN);
            } else {
                ThemeUtil.changeToTheme(getActivity(), ThemeUtil.CUSTOM_THEME_BLUE);
            }

            Preference preference = findPreference(key);
            if (key.equals("prefThemeColor")) {
                preference.setSummary(((ListPreference) preference).getEntry());
                Toast.makeText(getActivity(), "Enabled", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }


        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }


    }


    // Customizing activities colors
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(SettingsActivity.this);
        String background_chooser = getPrefs
                .getString("prefThemeColor", "0");
        View view = findViewById(R.id.layout_setting);

        if (background_chooser.equals("0")) {
            view.setBackgroundColor(Color.parseColor("#F5F5F5")); //Layout Default Color
        } else if (background_chooser.equals("1")) {
            view.setBackgroundColor(Color.CYAN);
        } else if (background_chooser.equals("2")) {
            view.setBackgroundColor(Color.YELLOW);
        } else if (background_chooser.equals("3")){
            view.setBackgroundColor(Color.GREEN);
        }else {
            view.setBackgroundColor(Color.RED);
        }

        super.onWindowFocusChanged(hasFocus);
    }


}

