package com.deenysoft.schoolbox.widget;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.cosmos.util.CosmosPreferenceManager;


/**
 * Created by shamsadam on 15/05/15.
 */
public abstract class ThemableActivity extends AppCompatActivity {

    private boolean mDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDark = CosmosPreferenceManager.getInstance().getUseDarkTheme();

        // set the theme
        if (mDark) {
            setTheme(R.style.CardView_Dark);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CosmosPreferenceManager.getInstance().getUseDarkTheme() != mDark) {
            restart();
        }
    }

    protected void restart() {
        final Bundle outState = new Bundle();
        onSaveInstanceState(outState);
        final Intent intent = new Intent(this, getClass());
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
