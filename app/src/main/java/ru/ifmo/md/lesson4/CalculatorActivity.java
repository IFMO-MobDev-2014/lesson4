package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.ifmo.md.lesson4.Expression.Parser.ParsingException;

public class CalculatorActivity extends Activity {

    CalculationEngine calc;
    EditText exprField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        calc = CalculationEngineFactory.defaultEngine();
        exprField = (EditText) findViewById(R.id.expr_input);
        exprField.setInputType(InputType.TYPE_NULL);
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            exprField.setRawInputType(InputType.TYPE_CLASS_TEXT);
            exprField.setTextIsSelectable(true);
        }
    }

    public void makeCalculations(View view) {
        String expr = exprField.getText().toString();
        try {
            double res = calc.calculate(expr);
            exprField.setText(Double.toString(res));
            exprField.setSelection(exprField.getText().length());
        } catch (CalculationException e) {
            exprField.setText("INVALID");
        }
    }

    public void insertChar(View view) {
        CharSequence ch = ((Button) view).getText();
        int start = exprField.getSelectionStart();
        exprField.getText().insert(start, ch);
    }

    public void deleteChar(View view) {
        int start = exprField.getSelectionStart();
        if(start > 0) {
            exprField.getText().delete(start - 1, start);
        }
    }

    public void resetField(View view) {
        exprField.getText().clear();
    }
}
