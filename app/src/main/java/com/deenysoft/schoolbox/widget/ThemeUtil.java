package com.deenysoft.schoolbox.widget;

import android.app.Activity;
import android.content.Intent;

import com.deenysoft.schoolbox.R;

/**
 * Created by shamsadam on 03/06/16.
 */
public class ThemeUtil {

    private static int sTheme;

    public final static int CUSTOM_THEME_BLUE = 0;
    public final static int CUSTOM_THEME_CYAN = 1;
    public final static int CUSTOM_THEME_YELLOW = 2;
    public final static int CUSTOM_THEME_GREEN = 3;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case CUSTOM_THEME_BLUE:
                activity.setTheme(R.style.Topeka_Base);
                break;
            case CUSTOM_THEME_CYAN:
                activity.setTheme(R.style.Topeka_Cyan);
                break;
            case CUSTOM_THEME_YELLOW:
                activity.setTheme(R.style.Topeka_Yellow1);
                break;
            case CUSTOM_THEME_GREEN:
                activity.setTheme(R.style.Topeka_Green1);
                break;
        }
    }

}
