package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class CalculationActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        textView = (TextView) findViewById(R.id.textView2);
    }

    @Override
    protected  void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    CalculationEngine calculator = CalculationEngineFactory.defaultEngine();
    String expression = "";
    boolean newExpr = false;

    public void clickButton(View view) {
        Button button = (Button) view;
        String value = button.getText().toString();
        DecimalFormat df = new DecimalFormat("#.000000");
        if (value.equals("B")) {
            expression = expression.substring(0, expression.length()-1);
        } else if (value.equals("C")) {
            expression = "";
        } else if (value.equals("=")) {
            try {
                expression = df.format(calculator.calculate(expression));
                newExpr = true;
            } catch(CalculationException e) {
                expression = e.getMessage();
            }
        } else {
            if (newExpr) {
                expression = "";
                newExpr = false;
            }
            expression += value;
        }
        textView.setText(expression.toCharArray(),0,expression.length());
    }

}
