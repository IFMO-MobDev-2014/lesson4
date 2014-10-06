package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by Artur on 06.10.2014.
 */
public class CalculatorActivity extends Activity {
    private String exp;
    private CalculationEngine calculator;
    private boolean haveError;
    private TextView result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        this.calculator = CalculationEngineFactory.defaultEngine();
        this.exp = "";
        this.haveError = false;
        this.result = (TextView)findViewById(R.id.result);
    }

    private void set(String exp) {
        this.exp = exp;
        result.setText(exp);
    }

    private void onEquals() {
        try {
            double res = calculator.calculate(exp);
            set(Double.toString(res));
            haveError = Double.isInfinite(res) || Double.isNaN(res);
        } catch (CalculationException e) {
            set("Error");
            haveError = true;
        }
    }

    private void onClear() {
        set("");
    }

    private void onDelete() {
        if (!exp.isEmpty()) {
            set(exp.substring(0, exp.length() - 1));
        }
    }

    private void validateState() {
        if (haveError) {
            set("");
            haveError = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        validateState();
        char keyName = (char) event.getUnicodeChar();
        if (('0' <= keyName && keyName <= '9') || keyName == '+' || keyName == '-' || keyName == '*' || keyName == '/' || keyName == '.' || keyName == '(' || keyName == ')') {
            set(exp + keyName);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_C) {
            onClear();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DEL) {
            onDelete();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_EQUALS) {
            onEquals();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void buttonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        validateState();
        if (button.getId() == R.id.equals) {
            onEquals();
        } else if (button.getId() == R.id.clear) {
            onClear();
        } else if (button.getId() == R.id.delete) {
            onDelete();
        } else {
            set(exp + buttonText);
        }
    }
}