package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends Activity {
    private String expression;
    private CalculationEngine engine;

    private TextView input;
    private TextView result;

    private static double eps = 1e-9;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        this.expression = "";
        this.engine = CalculationEngineFactory.defaultEngine();

        this.input = (TextView) findViewById(R.id.input);
        this.result = (TextView) findViewById(R.id.result);
    }

    private void setExpression(String expression) {
        this.expression = expression;
        input.setText(expression);
        try {
            double res = engine.calculate(expression);
            if (Math.abs(res - Math.floor(res)) <= eps) {
                result.setText("=" + String.format("%.0f", res));
            } else {
                result.setText("=" + res);
            }
        } catch (CalculationException e) {
            result.setText("...");
        }
    }

    private void onClear() {
        setExpression("");
    }

    private void onDelete() {
        if (!expression.isEmpty()) {
            setExpression(expression.substring(0, expression.length() - 1));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        char keyName = (char) event.getUnicodeChar();

        if (keyName == '0' || keyName == '1' || keyName == '2' || keyName == '3' || keyName == '4' ||
            keyName == '5' || keyName == '6' || keyName == '7' || keyName == '8' || keyName == '9' ||
            keyName == '+' || keyName == '-' || keyName == '*' || keyName == '/' || keyName == '.' ||
            keyName == '(' || keyName == ')') {
                setExpression(expression + keyName);
                return true;
        } else if (keyCode == KeyEvent.KEYCODE_C) {
            onClear();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DEL) {
            onDelete();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void buttonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        if (button.getId() == R.id.clear) {
            onClear();
        } else if (button.getId() == R.id.delete) {
            onDelete();
        } else {
            setExpression(expression + buttonText);
        }
    }
}