package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView inputField;
    private CalculationEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = (TextView) findViewById(R.id.inputField);
        engine = CalculationEngineFactory.defaultEngine();
    }

    public void onClearClick(View view) {
        inputField.setText("");
    }

    public void onEqualClick(View view) {
        try {
            inputField.setText(Double.toString(engine.calculate(inputField.getText().toString())));
        } catch (CalculationException e) {
            e.printStackTrace();
        }
    }
}
