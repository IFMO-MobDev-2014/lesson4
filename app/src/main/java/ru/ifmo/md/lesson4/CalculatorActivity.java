package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;


public class CalculatorActivity extends Activity {
    private ViewPager mPager = null;
    private int mOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        setContentView(R.layout.activity_calculator);

        setupViews();
    }

    private void setupViews() {
        mOrientation = getResources().getConfiguration().orientation;

        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mPager = (ViewPager) findViewById(R.id.viewPager);
        }

    }

}
