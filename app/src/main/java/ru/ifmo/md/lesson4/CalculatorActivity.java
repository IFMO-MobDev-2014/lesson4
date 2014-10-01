package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.ifmo.md.lesson4.Expression.Parser.ParsingException;

public class CalculatorActivity extends Activity {

    CalculationEngine calc;
    EditText exprField;
    TextView resField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        calc = CalculationEngineFactory.defaultEngine();
        exprField = (EditText) findViewById(R.id.expr_input);
        resField = (TextView) findViewById(R.id.result_text);
    }

    public void makeCalculations(View view) {
        String expr = exprField.getText().toString();
        try {
            double res = calc.calculate(expr);
            resField.setText(Double.toString(res));
        } catch (CalculationException e) {
            resField.setText(getString(R.string.calc_exception) + ": " + e);
        } catch (ParsingException e) {
            resField.setText(getString(R.string.parsing_exception) + ": " + e);
        }
    }

}
