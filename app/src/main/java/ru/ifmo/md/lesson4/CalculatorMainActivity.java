package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class CalculatorMainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expression = "";
        setContentView(R.layout.calculator_main_activity);
        textView = (TextView) findViewById(R.id.textView);
    }

    private String expression;
    private boolean error = false;
    private CalculationEngine calc = CalculationEngineFactory.defaultEngine();

    public void buttonClick(View view) {
        if (!(view instanceof Button))
            return;

        Button b = (Button) view;
        CharSequence txt = b.getText();
        if (error) {
            error = false;
            expression = "";
        }
        if (txt.charAt(0) == 'C') {
            if (!expression.isEmpty())
                expression = expression.substring(0, expression.length() - 1);
        } else if (txt.charAt(0) == '=') {
            try {
                double result = calc.calculate(expression);
                if (Double.isInfinite(result)) {
                    error = true;
                    if (result > 0)
                        textView.setText(R.string.calc_positive_infinity);
                    else
                        textView.setText(R.string.calc_negative_infinity);
                } else if (Double.isNaN(result)) {
                    error = true;
                    textView.setText(R.string.calc_error);
                } else {
                    expression = new DecimalFormat("#.##########").format(result);
                    textView.setText(expression);
                }
            } catch (CalculationException ex) {
                error = true;
                textView.setText("Error");
            }
            return;
        } else {
            expression += txt.charAt(0);
        }

        textView.setText(expression);
    }
}
