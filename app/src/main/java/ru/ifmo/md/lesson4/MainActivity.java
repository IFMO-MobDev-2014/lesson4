package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    private EditText input;
    private TextView result;
    private CalculatorEngine calculatorEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText)findViewById(R.id.inputExpr);
        result = (TextView)findViewById(R.id.result);
        calculatorEngine = new CalculatorEngine();

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoardOnTabClicked(v);
            }
        });

    }


    public void onButtonClick(View v) {
        Button button = (Button)v;
        if (button.getId() == R.id.C) {
            input.setText("");
            result.setText("");
        } else if (button.getId() == R.id.delete) {
            String str = input.getText().toString();
            int begin = input.getSelectionStart();
            int end = input.getSelectionEnd();
            if (begin != end) {
                str = str.substring(0, begin) + str.substring(end, str.length());
                input.setText(str);
                input.setSelection(begin);
            } else if (begin > 0) {
                str = str.substring(0, begin - 1) + str.substring(end, str.length());
                input.setText(str);
                input.setSelection(begin - 1);
                result.setText("");
            }
        } else if (button.getId() == R.id.equal) {
            String expr = input.getText().toString();
            int start = input.getSelectionStart();
            int end = input.getSelectionEnd();
            try {
                double res = calculatorEngine.calculate(expr);
                result.setText(String.format("%.4f", res));
            } catch (DivisionByZeroException e) {
                result.setText(messageError(e.getMessage(), "red"));
                input.setText(messageError(expr, e.getBeginOfError(), e.getEndOfError(), "red"));
            } catch (UnexpectedExpressionException e) {
                if (!input.getText().toString().isEmpty()) {
                    result.setText(messageError(e.getMessage(), "red"));
                    input.setText(messageError(expr, e.getBeginOfError(), e.getEndOfError(), "red"));
                }
            } catch (UnpairedBracketException e) {
                result.setText(messageError(e.getMessage(), "red"));
                input.setText(messageError(expr, e.getUnpairedIndex(), e.getUnpairedIndex() + 1, "red"));
            } catch (CalculationException e) {
                result.setText(messageError("Error", "red"));
            } finally {
                input.setSelection(start, end);
            }
        } else {
            String str = input.getText().toString();
            int begin = input.getSelectionStart();
            int end = input.getSelectionEnd();
            str = str.substring(0, begin) + button.getText() + str.substring(end, str.length());
            input.setText(str);
            input.setSelection(begin + 1);
            result.setText("");
        }
    }

    private Spanned messageError(String message, int l, int r, String color) {
        return Html.fromHtml(message.substring(0, l) + "<font color=\"" + color + "\">" +
                message.substring(l, r) + "</font>" + message.substring(r, message.length()));
    }

    private Spanned messageError(String message, String color) {
        return Html.fromHtml("<font color=\"" + color + "\">" + message + "</font>");
    }

    private void hideSoftKeyBoardOnTabClicked(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
