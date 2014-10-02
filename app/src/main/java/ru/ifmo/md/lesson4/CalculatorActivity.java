package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
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

    public void setExpression(String expression) {
        this.expression = expression;
        result.setText(expression);
    }

    public void buttonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        if (errorState) {
            setExpression("");
            errorState = false;
        }

        if (button.getId() == R.id.equals) {
            try {
                double result = engine.calculate(expression);
                setExpression(Double.toString(result));
                errorState = false;
            } catch (CalculationException e) {
                setExpression("Error");
                errorState = true;
            }
        } else if (button.getId() == R.id.clear) {
            setExpression("");
        } else if (button.getId() == R.id.delete) {
            if (!expression.isEmpty()) {
                setExpression(expression.substring(0, expression.length() - 1));
            }
        } else {
            setExpression(expression + buttonText);
        }
    }
}