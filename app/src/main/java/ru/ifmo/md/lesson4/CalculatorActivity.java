package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class CalculatorActivity extends Activity {
    public static final char INFINITY = (char) 0x221E;

    private String curExpr = "";
    private CalculationEngine calc = CalculationEngineFactory.defaultEngine();

    private TextView input, result;

    private DecimalFormat fmt;

    private void activityConfigure() {
        setContentView(R.layout.activity_calculator);

        input = (TextView) findViewById(R.id.input);
        result = (TextView) findViewById(R.id.result);

        input.setText(curExpr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove activity title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        fmt = new DecimalFormat("#");
        fmt.setMaximumFractionDigits(10);

        activityConfigure();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        activityConfigure();
    }

    private void calculate() {
        try {
            if (curExpr.isEmpty()) {
                result.setText("");
            } else {
                result.setText("=" + fmt.format(calc.calculate(curExpr)));
            }
        } catch (CalculationException e) {
            result.setText("ERROR");
        }
    }

    public void buttonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        if (button.getId() == R.id.equals) {
            calculate();
        } else if (button.getId() == R.id.clear) {
            curExpr = "";
        } else if (button.getId() == R.id.delete) {
            curExpr = curExpr.substring(0, curExpr.length() - 1);
        } else {
            curExpr += buttonText;
        }
        input.setText(curExpr);
    }

    public void resultClick(View v) {
        // append result to input if possible
        boolean canAppend = false;
        if (!curExpr.isEmpty()) {
            char last = curExpr.charAt(curExpr.length() - 1);
            canAppend = last != '.' && last != INFINITY && last != ')' && !(last >= '0' && last <= '9');
        } else {
            canAppend = true;
        }
        // get result number
        String resultStr = result.getText().toString();
        if (resultStr.isEmpty() || resultStr.charAt(0) != '=') {
            canAppend = false;
        } else {
            resultStr = resultStr.substring(1); // trim =
        }

        if (canAppend) {
            curExpr += resultStr;
            input.setText(curExpr);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        char key = (char) event.getUnicodeChar();
        boolean handled = false;
        if (key >= KeyEvent.KEYCODE_0 && key <= KeyEvent.KEYCODE_9) {
            curExpr += key;
            handled = true;
        } else if (key == KeyEvent.KEYCODE_C) {
            curExpr = "";
            handled = true;
        } else if (key == KeyEvent.KEYCODE_DEL) {
            curExpr = curExpr.substring(0, curExpr.length() - 1);
            handled = true;
        } else if (key == KeyEvent.KEYCODE_EQUALS) {
            calculate();
            handled = true;
        }
        if (handled) {
            input.setText(curExpr);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
