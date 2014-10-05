package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyActivity extends Activity {

    EditText expr;
    EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        expr = (EditText) findViewById(R.id.inputExpression);
        result = (EditText) findViewById(R.id.result);
        expr.setInputType(InputType.TYPE_NULL);

        expr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    result.setText(Double.toString(CalculationEngineFactory.defaultEngine().calculate(expr.getText().toString())));
                } catch (CalculationException e) {
                    result.setText("");
                }
            }
        });
    }

    public void calculate(View view) {
        String s = expr.getText().toString();
        try {
            double resultValue = CalculationEngineFactory.defaultEngine().calculate(s);
            result.setText(Double.toString(resultValue));
            expr.setText(result.getText());
        } catch(CalculationException e) {
            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        expr.setSelection(expr.length());
    }

    public void clickOnTextButton(View view) {
        Button a = (Button) view;
        String str = expr.getText().toString();
        String prefix = str.substring(0, expr.getSelectionStart());
        String suffix = str.substring(expr.getSelectionStart());
        int prevCursor = expr.getSelectionStart();
        expr.setText(prefix + a.getText().toString() + suffix);
        expr.setSelection(prevCursor + 1);
    }

    public void back(View view) {
        if (expr.getText().toString().isEmpty())
            return;
        String str = expr.getText().toString();
        String prefix = str.substring(0, expr.getSelectionStart() - 1);
        String suffix = str.substring(expr.getSelectionStart());
        int prevCursor = expr.getSelectionStart();
        expr.setText(prefix + suffix);
        expr.setSelection(prevCursor - 1);
    }

    public void cancel(View view) {
        expr.setText("");
    }
}
