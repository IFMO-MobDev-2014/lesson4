package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyActivity extends Activity {

    private EditText expr;
    private EditText result;
    private Button back_;
    private LongClickOnBackspaceButton t = null;
    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        expr = (EditText) findViewById(R.id.inputExpression);
        result = (EditText) findViewById(R.id.result);
        back_ = (Button) findViewById(R.id.back);

        h = new Handler() {
            public void  handleMessage(android.os.Message msg) {
                back();
            };
        };

        back_.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && t == null) {
                t = new LongClickOnBackspaceButton(h);
            }

            if (event.getAction() == MotionEvent.ACTION_UP && t != null) {
                t.f = true;
                t = null;
            }

            return false;
        }});

        expr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(expr.getWindowToken(), 0);
            }
        });

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
            expr.setText(String.format("%f", Double.parseDouble(result.getText().toString())));
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

    private void back() {
        int prevCursor = expr.getSelectionStart();
        if (prevCursor == 0)
            return;
        String str = expr.getText().toString();
        String prefix = str.substring(0, expr.getSelectionStart() - 1);
        String suffix = str.substring(expr.getSelectionStart());
        expr.setText(prefix + suffix);
        expr.setSelection(prevCursor - 1);
    }

    public void cancel(View view) {
        expr.setText("");
    }
}
