package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculationActivity extends Activity {

    boolean numberPressedAfterResult = false;

    private CalculationEngine calculationEngine;
    private String expression = "";
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculatorscreen);
        calculationEngine = CalculationEngineFactory.defaultEngine();
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onClick(View v) {
        Button button = (Button) v;
        String text = button.getText().toString();
        if (text.equals("1") || text.equals("2") || text.equals("3") || text.equals("4") || text.equals("5") || text.equals("6")
        || text.equals("7") || text.equals("8") || text.equals("9") || text.equals("0") || text.equals(".")) {
            if (numberPressedAfterResult) {
                expression = "";
                numberPressedAfterResult = false;
            }
            expression += text;
            textView.setText(expression);
        }
        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") || text.equals("(") || text.equals(")")) {
            expression += text;
            textView.setText(expression);
            numberPressedAfterResult = false;
        } else if (text.equals("=")) {
            try {
                double result = calculationEngine.calculate(expression);
                textView.setText(Double.toString(result));
                expression = Double.toString(result);
                numberPressedAfterResult = true;
            } catch (CalculationException e) {
                textView.setText(e.getMess());
            }
        } else if (text.equals("C")) {
            if (expression.length() != 0) {
                expression = expression.substring(0, expression.length() - 1);
                textView.setText(expression);
                numberPressedAfterResult = false;
            }
        } else if (text.equals("A")) {
            expression = "";
            textView.setText(expression);
            numberPressedAfterResult = false;
        }
    }
}
