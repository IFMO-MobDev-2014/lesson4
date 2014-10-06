package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

    TextView textView;
    CalculationEngine calculationEngine = CalculationEngineFactory.defaultEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("");
    }

    public void myListener(View view) {
        Button button = (Button) view;
        String str = textView.getText().toString();

        if (str.startsWith("Infinity") || str.startsWith("-Infinity") || str.startsWith("NaN")) {
            textView.setText("");
            return;
        }

        int id = button.getId();
        if (id == R.id.buttonBackSpace) {
            if (str.length() < 2) {
                textView.setText("");
            } else {
                textView.setText(str.substring(0, str.length() - 1));
            }
        } else if (id == R.id.buttonClear) {
            textView.setText("");
        } else if (id == R.id.buttonEvaluate) {
            try {
                textView.setText("" + calculationEngine.calculate(str));
            } catch (CalculationException e) {
                Log.d("CalculationException: ", str);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                textView.setText("");
            }
        } else {
            textView.append(button.getText());
        }
    }
}
