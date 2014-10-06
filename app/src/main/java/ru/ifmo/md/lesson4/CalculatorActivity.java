package ru.ifmo.md.lesson4;

import android.content.pm.ActivityInfo;
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
import android.widget.Toast;

import java.util.HashMap;


public class CalculatorActivity extends FragmentActivity {
    private int mOrientation;
    private EditText mTextView;
    private View.OnClickListener mListener;
    private String mText = "";

    private Pair[] codes = new Pair[]{
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
            Pair.create(R.id.button_op_bracket, R.string.button_op_bracket),
            Pair.create(R.id.button_cl_bracket, R.string.button_cl_bracket),
            Pair.create(R.id.button_delete, R.string.button_delete),
            Pair.create(R.id.button_delete_all, R.string.button_delete_all),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        setContentView(R.layout.activity_calculator);

        setupViews();
    }

    private void setupViews() {
        mOrientation = getResources().getConfiguration().orientation;
        mTextView = (EditText) findViewById(R.id.editText);
        mListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked(view);
            }
        };
        for (Pair p : codes) {
            int id = (Integer)p.first;
            Button b = (Button) findViewById(id);
            b.setOnClickListener(mListener);
        }
    }

    private void buttonClicked(View view) {
        int id = view.getId();
        String text = null;
        for (Pair p : codes) {
            int bId = (Integer)p.first;
            int str = (Integer)p.second;
            if (bId == id) {
                text = getString(str);
                break;
            }
        }
        switch (id) {
            case R.id.button_delete:
                if (mText.length() > 0) {
                    int len = 1;
                    if (mText.endsWith("Infinity")) len = 8;
                    else if (mText.endsWith("NaN")) len = 3;
                    mText = mText.substring(0, mText.length() - len);
                }
                break;
            case R.id.button_delete_all:
                mText = "";
                break;
            case R.id.button_equals:
                calculate();
                return;
            default:
                String tmp = text;
                if (id == R.id.button_multiply) tmp = "*";
                else if (id == R.id.button_divide) tmp = "/";
                else if (id == R.id.button_minus) tmp = "-";
                mText = mText + tmp;
                break;
        }
        setTextView();
        Log.d("TAG", text);
    }

    private void calculate() {
        double result;
        try {
            result = CalculationEngineFactory.defaultEngine().calculate(mText);
        } catch (CalculationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        mText = Double.toString(result);
        setTextView();
        mTextView.setSelection(0);
    }

    private void setTextView() {
        mTextView.setText(mText.replace("*", "×").replace("/", "÷")
                .replace("Infinity", "∞"));
        mTextView.setCursorVisible(true);
    }


}
