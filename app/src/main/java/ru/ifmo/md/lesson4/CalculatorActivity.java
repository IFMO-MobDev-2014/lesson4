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
    private boolean errorState;

    private TextView result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        this.expression = "";
        this.engine = CalculationEngineFactory.defaultEngine();
        this.errorState = false;

        this.result = (TextView) findViewById(R.id.result);
    }

    private void setExpression(String expression) {
        this.expression = expression;
        result.setText(expression);
    }

    private void onEquals() {
        try {
            double result = engine.calculate(expression);
            setExpression(Double.toString(result));
            errorState = Double.isInfinite(result) || Double.isNaN(result);
        } catch (CalculationException e) {
            setExpression("Error");
            errorState = true;
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

    private void validateState() {
        if (errorState) {
            setExpression("");
            errorState = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        validateState();

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
            setExpression(expression + buttonText);
        }
    }
}