package com.deenysoft.schoolbox.cosmos.ui;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.widget.ThemeUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by shamsadam on 28/03/16.
 */
public class CosmosFeeder extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final String TAG = "CosmosFeeder";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);
        setContentView(R.layout.cos_activity_feed);


        // Create the ListFragment and add it as main container for the activity.
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(android.R.id.content) == null) {
            CosmosFeederFragment list = new CosmosFeederFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();

        }

    }


    // Request Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, CosmosFeeder.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {}

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {}



}
