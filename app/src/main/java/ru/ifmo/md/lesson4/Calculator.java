package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Calculator extends Activity {
    TextView resView;
    int resViewBalance = 0;
    GridLayout lay;
    CalculationEngine calc = CalculationEngineFactory.defaultEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        resView = (TextView) findViewById(R.id.resView);
        resView.setText("");
        final Button brackets = (Button) findViewById(R.id.numParentheses);
        brackets.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int balance = resViewBalance;
                for (int i = 0; i < balance; i++) {
                    brackets.callOnClick();
                }
                return true;
            }
        });
    }

    public void myFancyListener(View view) {
        Button x = (Button) view;
        String expr = resView.getText().toString();
        if (expr.startsWith("Infinity")
                || expr.startsWith("NaN")
                || expr.startsWith("-Infinity")) {
            resView.setText("");
            expr = "";
        }
        int id = x.getId();
        if (id == R.id.numBackspace) {
            int length = resView.getText().length() - 1;
            if (length < 0) {
                length = 0;
            }
            if (expr.endsWith("(")) {
                resViewBalance--;
            } else if (expr.endsWith(")")) {
                resViewBalance++;
            }
            resView.setText(resView.getText().
                    subSequence(0, length));
        } else if (id == R.id.numClear) {
            resViewBalance = 0;
            resView.setText("");
        } else if (id == R.id.numExpr) {
            try {
                resView.setText("" + calc.calculate(resView.getText().toString()));
                resViewBalance = 0;
            } catch (CalculationException e) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                String text = e.toString();
                int ind = text.indexOf(' ');
                if (ind == -1) {
                    text = "Something happened";
                } else {
                    text = text.substring(ind);
                }
                Toast.makeText(context, text, duration).show();
            }
        } else if (id == R.id.numParentheses) {
            if (expr.isEmpty()
                    || expr.endsWith("*")
                    || expr.endsWith("/")
                    || expr.endsWith("+")
                    || expr.endsWith("-")
                    || expr.endsWith("(")) {
                resView.append("(");
                resViewBalance++;
            } else if (Character.isDigit(expr.charAt(expr.length() - 1))
                    || expr.endsWith(")")
                    || expr.endsWith(".")) {
                if (resViewBalance != 0) {
                    resView.append(")");
                    resViewBalance--;
                }
            }
        } else {

            if (Character.isDigit(x.getText().charAt(0)) || id == R.id.numDot) {
                if (!expr.endsWith(")")) {
                    resView.append(x.getText());
                }
            } else {
                if (expr.isEmpty()
                        || expr.endsWith("(")
                        || expr.endsWith("+")
                        || expr.endsWith("-")
                        || expr.endsWith("/")
                        || expr.endsWith("*")) {
                    if (id == R.id.numPlus || id == R.id.numMinus) {
                        resView.append(x.getText());
                    }
                } else {
                    resView.append(x.getText());
                }
            }

        }
    }
}
