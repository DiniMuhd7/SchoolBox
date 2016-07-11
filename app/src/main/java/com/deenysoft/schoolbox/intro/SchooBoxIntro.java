package com.deenysoft.schoolbox.intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.deenysoft.schoolbox.dashboard.DashboardActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.nest.ui.quote.ListActivity;

/**
 * Created by shamsadam on 13/04/16.
 */
public class SchooBoxIntro extends AppIntro {
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SampleSlide.newInstance(R.layout.intro1));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        addSlide(SampleSlide.newInstance(R.layout.intro4));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#2962FF"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        //setVibrate(true);
        //setVibrateIntensity(30);
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSkipPressed() {

        loadMainActivity();
        //Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {

        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }


    public void getStarted(View v){
        loadMainActivity();
    }
}
