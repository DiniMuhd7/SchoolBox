package com.deenysoft.schoolbox.nest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by shamsadam on 06/04/16.
 */
public class Community extends AppCompatActivity {

    AdView mAdView;
    private WebView mBrowser;
    private String deenURL = "https://www.facebook.com/schoolboxlite/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        //Show a toast message to the user
        Toast.makeText(this, "Processing... Please Wait", Toast.LENGTH_LONG).show();


        // Setting Up Banner Ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        // load and display ads
        mAdView.loadAd(adRequest);


        // Setting up full functioning Webview
        mBrowser = (WebView) findViewById(R.id.webView1);
        mBrowser.setInitialScale(1);
        mBrowser.getSettings().setJavaScriptEnabled(true);
        mBrowser.getSettings().setLoadWithOverviewMode(true);
        mBrowser.getSettings().setUseWideViewPort(true);
        mBrowser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mBrowser.setScrollbarFadingEnabled(false);

        mBrowser.setWebChromeClient(new WebChromeClient());
        mBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        mBrowser.loadUrl(deenURL);


    }


    // [START add_lifecycle_methods]
    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    // [END add_lifecycle_methods]


}
