package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculateActivity extends Activity {
    private String expression;
    private CalculationEngine calculator;
    private boolean errorState;

    private TextView TextView1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        this.expression = "";
        this.calculator = CalculationEngineFactory.defaultEngine();
        this.errorState = false;

        this.TextView1 = (TextView) findViewById(R.id.TextView1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        char keyName = (char) event.getUnicodeChar();
        if (keyName == '0' || keyName == '1' || keyName == '2' || keyName == '3' || keyName == '4' ||
                keyName == '5' || keyName == '6' || keyName == '7' || keyName == '8' || keyName == '9' ||
                keyName == '+' || keyName == '-' || keyName == '*' || keyName == '/' || keyName == '.' ||
                keyName == '(' || keyName == ')') {
            this.expression = expression + keyName;
            TextView1.setText(expression);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_C) {
            this.expression = "";
            TextView1.setText("");
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (!expression.isEmpty()) {
                this.expression = expression.substring(0, expression.length() - 1);
                TextView1.setText(expression);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_EQUALS) {
            try {
                double result = calculator.calculate(expression);
                this.expression = Double.toString(result);
                TextView1.setText(expression);
                errorState = Double.isInfinite(result) || Double.isNaN(result);
            } catch (CalculationException e) {
                this.expression = "Error";
                TextView1.setText("Error");
                errorState = true;
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void buttonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        if (errorState) {
            this.expression = "";
            TextView1.setText("");
            errorState = false;
        }

        if (button.getId() == R.id.equals) {
            try {
                double result = calculator.calculate(expression);
                this.expression = Double.toString(result);
                TextView1.setText(expression);
                errorState = Double.isInfinite(result) || Double.isNaN(result);
            } catch (CalculationException e) {
                this.expression = "Error";
                TextView1.setText("Error");
                errorState = true;
            }
        } else if (button.getId() == R.id.clear) {
            this.expression = "";
            TextView1.setText("");
        } else if (button.getId() == R.id.delete) {
            if (!expression.isEmpty()) {
                this.expression = expression.substring(0, expression.length() - 1);
                TextView1.setText(expression);
            }
        } else {
            this.expression = expression + buttonText;
            TextView1.setText(expression);
        }

    }

}
