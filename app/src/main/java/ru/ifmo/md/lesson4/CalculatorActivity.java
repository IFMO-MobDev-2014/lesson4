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

import parser.DivisionByZeroException;

public class CalculatorActivity extends Activity {

    EditText field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        field = (EditText) findViewById(R.id.field);

        disableSoftInputFromAppearing(field);
        field.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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

    public void onClickClear(View button) {
        field.setText("");
    }

    public void onClickText(View button) {
        field.setText(field.getText().insert(field.getText().length(), ((Button)button).getText()));
    }

    public void onClickCalculate(View button) {
        try {
            field.setText(Double.toString(CalculationEngineFactory.defaultEngine().calculate(field.getText().toString())));
        } catch (DivisionByZeroException e) {
            field.setText("Error: division by zero. Press C.");
        } catch (CalculationException e) {
            field.setText("Error: invalid input. Press C.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
