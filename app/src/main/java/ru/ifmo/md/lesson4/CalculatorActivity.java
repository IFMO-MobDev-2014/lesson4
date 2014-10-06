package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CalculatorActivity extends Activity {
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        text = (EditText) findViewById(R.id.calc);
        disableSoftInputFromAppearing(text);
        text.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
    }


    public void onClickText(View view) {
        text.getText().insert(text.getText().length(), ((Button)view).getText());
    }

    public void onClickCalculate(View view) {
        CalculationEngine calc = CalculationEngineFactory.defaultEngine();
        try {
            double d = calc.calculate(text.getText().toString());
            text.getText().clear();
            String s;
            if (d == (long)d) {
                s = String.format("%d", (long)d);
            }
            else {
                s = String.valueOf(d);
            }
            text.getText().insert(0, s);
        } catch (CalculationException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid expression!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickClear(View view) {
        text.setText("");
    }
}
