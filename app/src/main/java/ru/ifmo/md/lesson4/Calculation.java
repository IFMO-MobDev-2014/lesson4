package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Яна on 05.10.2014.
 */
public class Calculation extends Activity {

    String current;
    TextView expression;
    int balance = 0;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation);
        expression = (TextView) findViewById(R.id.Expression);
        current = "";
    }

    public void onClickDigit(View view) {
        Button button = (Button)view;
        current += button.getText();
        expression.setText(current);
    }

    public void onClickOpenBracket(View view) {
        boolean flag = true;
        int size = current.length();
        if (size > 0) {
            char tmp = current.charAt(size - 1);
            if ((tmp >= '0' && tmp <= '9') || tmp == '.') {
                flag = false;
            }
        }
        if (flag) {
            current += '(';
            balance++;
        }
        expression.setText(current);
    }

    public void onClickCloseBracket(View view) {
        boolean flag = true;
        int size = current.length();
        if (balance == 0)
            flag = false;
        if (size == 0)
            flag = false;
        else if (size > 0) {
            char tmp = current.charAt(size - 1);
            if (tmp == '+' || tmp == '*' || tmp == '/' || tmp == '-' || tmp == '.' || tmp == '(') {
                flag = false;
            }
        }
        if (flag) {
            current += ')';
            balance--;
        }
        expression.setText(current);
    }

    public void onClickOperations(View view) {
        Button button = (Button)view;
        String operation = button.getText().toString();
        boolean flag = true;
        int size = current.length();
        if (size == 0)
            flag = false;
        else if (size > 0) {
            char tmp = current.charAt(size - 1);
            if (tmp == '(') {
                flag = operation.equals("-");
            } else if (tmp == '+' || tmp == '*' || tmp == '/' || tmp == '-' || tmp == '.') {
                flag = false;
            }
        }
        if (flag)
            current += operation;
        expression.setText(current);
    }

    public void onClickDelete(View view) {
        if (current.length() > 0) {
            current = current.substring(0, current.length() - 1);
        }
        expression.setText(current);
    }

    public void onClickC(View view) {
        current = "";
        expression.setText(current);
    }

    public void onClickEquals(View view) {
        double ans = 0;
        boolean flag = true;
        try {
            ans = CalculationEngineFactory.defaultEngine().calculate(current);
        } catch (CalculationException e) {
            Toast toast = Toast.makeText(getApplicationContext(), e.s, Toast.LENGTH_SHORT);
            toast.show();
            flag = false;
        }
        if (flag)
            current = ans + "";
        else
            current = "";
        expression.setText(current);
    }
}
