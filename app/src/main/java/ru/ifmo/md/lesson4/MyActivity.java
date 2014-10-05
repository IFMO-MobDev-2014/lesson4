package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by sultan on 05.10.14.
 */
public class MyActivity  extends Activity{

    private EditText editText;
    private CalculationEngine calculationEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        editText = (EditText) findViewById(R.id.editText);
        calculationEngine = CalculationEngineFactory.defaultEngine();
    }

    private void calculate() {
        try {
            double result = calculationEngine.calculate(editText.getText().toString());
            editText.setText(String.valueOf(result));
        } catch (CalculationException e) {
            editText.setText("Error");
        }
        editText.setSelection(editText.getText().length());
    }

    private void deleteLastCharacter() {
        String text = editText.getText().toString();
        if (text.length() > 0)
        {
            editText.setText(text.substring(0, text.length() - 1));
        }
        editText.setSelection(editText.getText().length());
    }


    public void onButtonClick(View view) {
        switch (view.getId())
        {
            case R.id.button1: editText.append("("); break;
            case R.id.button2: editText.append(")"); break;
            case R.id.button3: editText.setText(""); break;
            case R.id.button4: deleteLastCharacter(); break;
            case R.id.button5: editText.append("7"); break;
            case R.id.button6: editText.append("8"); break;
            case R.id.button7: editText.append("9"); break;
            case R.id.button8: editText.append("/"); break;
            case R.id.button9: editText.append("4"); break;
            case R.id.button10: editText.append("5"); break;
            case R.id.button11: editText.append("6"); break;
            case R.id.button12: editText.append("*"); break;
            case R.id.button13: editText.append("1"); break;
            case R.id.button14: editText.append("2"); break;
            case R.id.button15: editText.append("3"); break;
            case R.id.button16: editText.append("-"); break;
            case R.id.button17: editText.append("0"); break;
            case R.id.button18: editText.append("."); break;
            case R.id.button19: calculate(); break;
            case R.id.button20: editText.append("+"); break;
        }
    }
}
