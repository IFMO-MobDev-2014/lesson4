package ru.ifmo.md.lesson4.handlers;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.ifmo.md.lesson4.R;
import ru.ifmo.md.lesson4.engine.CalculationEngine;
import ru.ifmo.md.lesson4.engine.CalculationEngineFactory;
import ru.ifmo.md.lesson4.exceptions.CalculationException;

public class MainActivity extends Activity {
    private TextView inputField;
    private CalculationEngine engine;
    private String currentExpression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = (TextView) findViewById(R.id.input);
        engine = CalculationEngineFactory.defaultEngine();
    }

    public void onClearClick(View view) {
        inputField.setText("0");
    }

    public void addElement(View view) {
        if (inputField.getText().equals("0"))
            inputField.setText("");
        inputField.setText(inputField.getText() + ((Button) view).getText().toString());
        this.tryRenderResult();
    }

    public void tryRenderResult() {
        try {
            ((TextView) findViewById(R.id.result_view)).setText(Double.toString(
                    engine.calculate(inputField.getText().toString())));
        } catch (CalculationException e) {
            ((TextView) findViewById(R.id.result_view)).setText("Enter an expression..");
        }
    }
}