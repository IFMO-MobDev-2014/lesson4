package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CalculatorMainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            expression = savedInstanceState.getString("calcExpression");
        }

        if (expression == null)
            expression = "";

        setContentView(R.layout.activity_calculator_main);

        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("calcExpression", expression);
    }

    private String expression;
    private boolean badResult = false;
    private CalculationEngine calc = CalculationEngineFactory.defaultEngine();

    public void buttonClick(View view) {
        if (!(view instanceof Button))
            return;

        Button btn = (Button) view;
        CharSequence txt = btn.getText();
        if (badResult) {
            badResult = false;
            expression = "";
        }
        if (txt.charAt(0) == 'C') {
            expression = "";
        } else if (txt.charAt(0) == '←') {
            if (!expression.isEmpty())
                expression = expression.substring(0, expression.length() - 1);
        } else if (txt.charAt(0) == '=') {
            try {
                double result = calc.calculate(expression);
                if (Double.isNaN(result)) {
                    badResult = true;
                    textView.setText(R.string.calc_nan);
                } else if (Double.isInfinite(result)) {
                    badResult = true;
                    if (result > 0)
                        textView.setText(R.string.calc_positive_infinity);
                    else
                        textView.setText(R.string.calc_negative_infinity);
                } else {
                    expression = result + "";
                    textView.setText(expression);
                }
            } catch (CalculationException ex) {
                badResult = true;
                textView.setText(R.string.calc_error);
                expression = "";
            }
            return;
        } else {
            expression += txt.charAt(0);
        }

        textView.setText(expression.toCharArray(), 0, expression.length());
    }
}
