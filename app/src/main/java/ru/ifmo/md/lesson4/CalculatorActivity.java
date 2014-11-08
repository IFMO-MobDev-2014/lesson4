package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends Activity {
    private String expression;
    private AntonCalculateEngine engine;

    private TextView input;
    private TextView result;

    private static double eps = 1e-9;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        this.expression = "";
        this.engine = new AntonCalculateEngine();

        this.input = (TextView) findViewById(R.id.input);
        this.result = (TextView) findViewById(R.id.result);

        Button clearBtn = (Button) findViewById(R.id.clear);
        clearBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClear();
                return true;
            }
        });
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
            result.setText(expression.isEmpty() ? "" : "...");
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

    private void onParentheses() {
        AntonCalculateEngine.Lexem lastOp = engine.getLastOp();
        String whatParenthesis = null;

        if (lastOp == null) {
            whatParenthesis = "(";
        } else {
            switch (lastOp) {
                case LBRACKET:
                case OPERATOR:
                    whatParenthesis = "(";
                    break;
                case RBRACKET:
                case OPERAND:
                    whatParenthesis = ")";
                    break;
            }
        }

        setExpression(expression + whatParenthesis);
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
            onDelete();
        } else if (button.getId() == R.id.parentheses) {
            onParentheses();
        } else {
            setExpression(expression + buttonText);
        }
    }
}