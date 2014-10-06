package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class CalculationActivity extends Activity {

    Button textView;
    // A little cheat for nice showing text. I couldn't create TextView with transparent background.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        textView = (Button) findViewById(R.id.button);
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

    public void clickButton(View view) {
        Button button = (Button) view;
        String value = button.getText().toString();
        DecimalFormat df = new DecimalFormat("#.00000");
        if (value.equals("B")) {
            expression = expression.substring(0, expression.length()-1);
        } else if (value.equals("C")) {
            expression = "";
        } else if (value.equals("=")) {
            try {
                double result = calculator.calculate((expression));
                if (Double.toString(result % 1).length() > 8) {
                    expression = df.format(result);
                } else {
                    expression = Double.toString(result);
                }
            } catch(CalculationException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                expression = "";
            }
        } else {
            expression += value;
        }
        textView.setText(expression.toCharArray(),0,expression.length());
    }

}
