package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    int i;
    String expression = "";
    TextView text;

    private void addDigit(int num) {
        expression+=num;
        text.setText(expression);
    }

    private void addSign(char sign) {
        expression+=sign;
        text.setText(expression);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button digit[] = new Button[10];
        digit[0] = (Button) findViewById(R.id.digit0);
        digit[1] = (Button) findViewById(R.id.digit1);
        digit[2] = (Button) findViewById(R.id.digit2);
        digit[3] = (Button) findViewById(R.id.digit3);
        digit[4] = (Button) findViewById(R.id.digit4);
        digit[5] = (Button) findViewById(R.id.digit5);
        digit[6] = (Button) findViewById(R.id.digit6);
        digit[7] = (Button) findViewById(R.id.digit7);
        digit[8] = (Button) findViewById(R.id.digit8);
        digit[9] = (Button) findViewById(R.id.digit9);

        Button add = (Button) findViewById(R.id.addition);
        Button subtract = (Button) findViewById(R.id.subtraction);
        Button multiply = (Button) findViewById(R.id.multiplication);
        Button divide = (Button) findViewById(R.id.division);
        Button dot = (Button) findViewById(R.id.dot);
        Button leftBracket = (Button) findViewById(R.id.leftBracket);
        Button rightBracket = (Button) findViewById(R.id.rightBracket);
        Button clearField = (Button) findViewById(R.id.clear);
        Button delToken = (Button) findViewById(R.id.delete);
        text = (TextView) findViewById(R.id.textView);
        text.setText(expression);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addSign('+');
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addSign('-');
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addSign('*');
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addSign('/');
            }
        });

        for (i = 0; i < 10; i++) {
            digit[i].setOnClickListener(new View.OnClickListener() {
                final int x = i;
                @Override
                public void onClick(View v) {
                    addDigit(x);
                }
            });
        }

        leftBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSign('(');
            }
        });

        rightBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addSign(')');
            }
        });

        delToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder(expression);
                sb.deleteCharAt(expression.length()-1);

                expression = sb.toString();
                text.setText(expression);
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addSign('.');
            }
        });

        clearField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                text.setText(expression);
            }
        });

    }

    public void result(View v){
        try {
            final double ans = CalculationEngineFactory.defaultEngine().calculate(expression);
            text.setText(String.valueOf(ans));
            expression = "";
        } catch (CalculationException e){
            Toast.makeText(this, "Exception", Toast.LENGTH_LONG).show();
        }
    }

}
