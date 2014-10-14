package ru.ifmo.md.lesson4.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.R;
import ru.ifmo.md.lesson4.exception.CalculationException;

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
            inputField.setText(Double.toString(
                    engine.calculate(inputField.getText().toString())));
        } catch (CalculationException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error))
                    .setMessage(e.getMessage());
            builder.create().show();
        }
    }

    public void onBackspaceClick(View view) {
        String oldText = inputField.getText().toString();
        if (oldText.length() > 0) {
            inputField.setText(oldText.substring(0, oldText.length() - 1));
        }
    }
}
