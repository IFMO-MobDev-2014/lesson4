package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;


public class CalculatorActivity extends FragmentActivity
    implements BaseButtonsFragment.OnFragmentInteractionListener,
               AdvancedButtonsFragment.OnFragmentInteractionListener {
    private int mOrientation;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

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
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        int pages;
        switch (mOrientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                pages = 2;
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                pages = 1;
                break;
            default:
                pages = 2;
                break;
        }
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onButtonClicked(int id) {
        Log.d("TAG", "" + id);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private int mPages;

        public MyFragmentPagerAdapter(FragmentManager fm, int pages) {
            super(fm);
            mPages = pages;
        }

        @Override
        public Fragment getItem(int position) {
            switch (mPages) {
                case 1:
                    return BaseButtonsFragment.newInstance();
                case 2:
                    switch (position) {
                        case 0:
                            return BaseButtonsFragment.newInstance();
                        case 1:
                            return AdvancedButtonsFragment.newInstance();
                    }
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mPages;
        }
    }

}
