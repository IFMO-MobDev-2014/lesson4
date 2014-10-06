package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by nagibator2005 on 2014-10-06.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnChar(View view) {
        String val = ((Button)view).getText().toString();
        EditText editText = (EditText) findViewById(R.id.editText);
        int start = editText.getSelectionStart(), end = editText.getSelectionEnd();
        String editVal = editText.getText().toString();
        String newText = editVal.substring(0, start) + val + editVal.substring(end);
        editText.setText(newText);
        editText.setSelection(start + 1);
    }

    public void clearText(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("");
    }

    public void calculate(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String editVal = editText.getText().toString();
        String result;
        try {
            result = CalculationEngineFactory.defaultEngine().calculate(editVal) + "";
        } catch(CalculationException e) {
            result = "Error";
        }
        editText.setText(result);
    }
}