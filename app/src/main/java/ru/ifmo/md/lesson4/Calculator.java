package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Calculator extends Activity {

    String expression;
    TextView textView;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        textView = (TextView) findViewById(R.id.textView2);
        expression = "";
    }


    public void calc(View view) {
        try {
            flag = true;
            double ret = CalculationEngineFactory.defaultEngine().calculate(textView.getText().toString());
            textView.setText(Double.toString(ret));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(char a) {
        if (flag) {
            textView.setText("");
            flag = false;
        }
        textView.setText(textView.getText() + Character.toString(a));
    }

    public void one(View view) {
        add('1');
    }

    public void two(View view) {
        add('2');
    }

    public void three(View view) {
        add('3');
    }

    public void four(View view) {
        add('4');
    }

    public void five(View view) {
        add('5');
    }

    public void six(View view) {
        add('6');
    }

    public void seven(View view) {
        add('7');
    }

    public void eight(View view) {
        add('8');
    }

    public void nine(View view) {
        add('9');
    }

    public void zero(View view) {
        add('0');
    }

    public void plus(View view) {
        add('+');
    }

    public void subtract(View view) {
        add('-');
    }

    public void multiply(View view) {
        add('*');
    }

    public void divide(View view) {
        add('/');
    }

    public void dot(View view) {
        add('.');
    }

    public void leftBrace(View view) {
        add('(');
    }

    public void rightBrace(View view) {
        add(')');
    }

    public void delete(View view) {
        String a = textView.getText().toString();
        if (a.length() > 0)
            textView.setText(a.substring(0, a.length() - 1));
    }
}
