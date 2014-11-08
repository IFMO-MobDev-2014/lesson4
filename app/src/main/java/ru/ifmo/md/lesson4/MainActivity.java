package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class MainActivity extends Activity {
    private int width, height;
    private static int cursorBegin = 0, cursorEnd = 0;
    private MyTextEdit textIn;
    private TextView textOut;
    private String curr = "", history = "";
    private AdapterView.OnItemClickListener keyboardListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            String com = KeyboardAdapter.keys[position];
            if (com.equals("C")) {
                clearCurr();
            }
            else if (com.equals("←")) {
                delFromCurr();
            }
            else {
                addToCurr(com);
            }
        }
    };
    private View.OnClickListener equalsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                String result;
                double dResult = CalculationEngineFactory.defaultEngine().calculate(curr);
                if (dResult % 1 == 0)
                    result = Integer.toString((int)dResult);
                else
                    result = Double.toString(dResult);
                history += (curr + " = " + result + "<br><br>");
                curr = result;
                textIn.setText(curr);
                textIn.setSelection(textIn.getText().length());
                textOut.setText(Html.fromHtml(history));
                scrollDown();
            } catch (CalculationException e) {
                history += (curr + "<br><font color=\"0xff0000\">" + getString(R.string.error) + "</font><br><br>");
                textOut.setText(Html.fromHtml(history));
                scrollDown();
            }
        }
    };

    private void scrollDown() {
        final int scrollAmount = textOut.getLayout().getLineTop(textOut.getLineCount()) - textOut.getHeight();
        if (scrollAmount > 0)
            textOut.scrollTo(0, scrollAmount);
        else
            textOut.scrollTo(0, 0);
    }

    private void inputRefresh(int begin, int end) {
        textIn.setText(curr);
        textIn.setSelection(begin, end);
        textOut.setText(Html.fromHtml(history + curr));
    }

    private void addToCurr(String s) {
        int tmpBegin = cursorBegin + 1, tmpEnd = cursorBegin + 1;
        curr = curr.substring(0, cursorBegin) + s + curr.substring(cursorEnd, curr.length());
        inputRefresh(tmpBegin, tmpEnd);
    }

    private void delFromCurr() {
        int tmpBegin, tmpEnd;
        if (cursorBegin != cursorEnd) {
            curr = curr.substring(0, cursorBegin) + curr.substring(cursorEnd, curr.length());
            tmpEnd = tmpBegin = cursorBegin;
        }
        else {
            curr = curr.substring(0, Math.max(cursorBegin - 1, 0)) + curr.substring(cursorBegin, curr.length());
            tmpBegin = Math.max(cursorBegin - 1, 0);
            tmpEnd = Math.max(cursorBegin - 1, 0);
        }
        inputRefresh(tmpBegin, tmpEnd);
    }

    private void clearCurr() {
        curr = "";
        inputRefresh(0, 0);
    }

    public static void setCursor(int begin, int end) {
        cursorEnd = end;
        cursorBegin = begin;
    }

    public static Point getScreenSize(Activity act) {
        Point size = new Point();
        DisplayMetrics metrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        size.x = metrics.widthPixels;
        size.y = metrics.heightPixels;
        return size;
    }

    private void initUI() {
        textOut = (TextView) findViewById(R.id.textOut);
        RelativeLayout.LayoutParams outTextParams = new RelativeLayout.LayoutParams(width, 4 * height / 10);
        textOut.setLayoutParams(outTextParams);
        textOut.setMovementMethod(new ScrollingMovementMethod());

        textIn = (MyTextEdit) findViewById(R.id.textIn);
        RelativeLayout.LayoutParams textInParams = new RelativeLayout.LayoutParams(4 * width / 5, height / 10);
        textInParams.addRule(RelativeLayout.BELOW, R.id.textOut);
        textInParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, R.id.textOut);
        textIn.setLayoutParams(textInParams);
        textIn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        TextView equals = (TextView) findViewById(R.id.equals);
        RelativeLayout.LayoutParams equalsParams = new RelativeLayout.LayoutParams(width / 5, height / 10);
        equalsParams.addRule(RelativeLayout.RIGHT_OF, R.id.textIn);
        equalsParams.addRule(RelativeLayout.BELOW, R.id.textOut);
        equals.setLayoutParams(equalsParams);
        equals.setText("=");
        equals.setTextColor(getResources().getColor(R.color.holo_blue_dark));
        equals.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        equals.setGravity(Gravity.CENTER);
        equals.setOnClickListener(equalsListener);

        GridView keyboard = (GridView) findViewById(R.id.keyboard);
        RelativeLayout.LayoutParams keyboardParams = new RelativeLayout.LayoutParams(width, height / 2);
        keyboardParams.addRule(RelativeLayout.BELOW, R.id.textIn);
        keyboardParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, R.id.textIn);
        keyboard.setLayoutParams(keyboardParams);
        keyboard.setAdapter(new KeyboardAdapter(this, width / 5, height / 10));
        keyboard.setOnItemClickListener(keyboardListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        width = MainActivity.getScreenSize(this).x;
        height = MainActivity.getScreenSize(this).y;

        initUI();

    }
}
