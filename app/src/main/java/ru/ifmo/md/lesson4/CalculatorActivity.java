package ru.ifmo.md.lesson4;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CalculatorActivity extends FragmentActivity
    implements BaseButtonsFragment.OnFragmentInteractionListener,
               AdvancedButtonsFragment.OnFragmentInteractionListener,
               LandscapeFragment.OnFragmentInteractionListener {
    private int mOrientation;
    private ViewPager mViewPager = null;
    private PagerAdapter mAdapter = null;
    private EditText mTextView;

    public Pair[] codes = new Pair[]{
            Pair.create(R.id.button_0, R.string.button_0),
            Pair.create(R.id.button_1, R.string.button_1),
            Pair.create(R.id.button_2, R.string.button_2),
            Pair.create(R.id.button_3, R.string.button_3),
            Pair.create(R.id.button_4, R.string.button_4),
            Pair.create(R.id.button_5, R.string.button_5),
            Pair.create(R.id.button_6, R.string.button_6),
            Pair.create(R.id.button_7, R.string.button_7),
            Pair.create(R.id.button_8, R.string.button_8),
            Pair.create(R.id.button_9, R.string.button_9),
            Pair.create(R.id.button_plus, R.string.button_plus),
            Pair.create(R.id.button_minus, R.string.button_minus),
            Pair.create(R.id.button_multiply, R.string.button_multiply),
            Pair.create(R.id.button_divide, R.string.button_divide),
            Pair.create(R.id.button_equals, R.string.button_equals),
            Pair.create(R.id.button_point, R.string.button_point),
            Pair.create(R.id.button_sin, R.string.button_sin),
            Pair.create(R.id.button_cos, R.string.button_cos),
            Pair.create(R.id.button_tan, R.string.button_tan),
            Pair.create(R.id.button_ln, R.string.button_ln),
            Pair.create(R.id.button_log, R.string.button_log),
            Pair.create(R.id.button_pi, R.string.button_pi),
            Pair.create(R.id.button_exp, R.string.button_exp),
            Pair.create(R.id.button_power, R.string.button_power),
            Pair.create(R.id.button_op_bracket, R.string.button_op_bracket),
            Pair.create(R.id.button_cl_bracket, R.string.button_cl_bracket),
            Pair.create(R.id.button_sqrt, R.string.button_sqrt)
    };

    public static final int[] advancedButtons = {
            R.id.button_sin, R.id.button_cos, R.id.button_tan,
            R.id.button_ln, R.id.button_log, R.id.button_fact,
            R.id.button_pi, R.id.button_exp, R.id.button_power,
            R.id.button_op_bracket, R.id.button_cl_bracket, R.id.button_sqrt
    };

    public static final int[] baseButtons = {
            R.id.button_0, R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8,
            R.id.button_9, R.id.button_plus, R.id.button_minus,
            R.id.button_multiply, R.id.button_divide, R.id.button_equals,
            R.id.button_point
    };

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
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mAdapter);
        } else if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {

        }

        mTextView = (EditText) findViewById(R.id.editText);
    }

    @Override
    public void onButtonClicked(int id) {
        for (Pair p : codes) {
            if (id == (Integer)p.first) {
                String operation = getString((Integer)p.second);
                Log.d("TAG", operation);
                mTextView.append(operation);
            }
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BaseButtonsFragment.newInstance();
                case 1:
                    return AdvancedButtonsFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
