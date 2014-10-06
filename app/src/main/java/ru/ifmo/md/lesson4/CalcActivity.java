package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CalcActivity extends Activity {
    TextView result;
    Double res;
    int balance = 0;
    boolean dot = false;
    boolean evaluated = false;
    CalculationEngine engine = CalculationEngineFactory.defaultEngine();
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        result = (TextView) findViewById(R.id.result);
        result.setText("");
        final Button delButton = (Button) findViewById(R.id.del);
        delButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                result.setText("");
                balance = 0;
                dot = false;
                evaluated = false;
                return true;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_calc);
    }

    public void outToast(String message) {
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buttonClick(View view) {
        Button button = (Button) view;
        String expr;
        if (evaluated) {
            expr = "";
        } else {
            expr = result.getText().toString();
        }
        evaluated = false;
        Log.i("OK", " OK");

        int id = button.getId();
        switch (id) {
            case R.id.del:
                if (expr.length() == 0) {
                    return;
                }
                if (expr.endsWith("(")) {
                    balance--;
                } else if (expr.endsWith(")")) {
                    balance++;
                } else if (expr.endsWith(".")) {
                    dot = false;
                }
                expr = expr.substring(0, expr.length());
                break;
            case R.id.eval:
                try {
                    res = engine.calculate(expr);
                    result.setText(res.toString());
                    evaluated = true;
                    return;
                } catch (CalculationException e) {
                    outToast(e.getMessage());
                }
                break;
            case R.id.dot:
                if (!dot) {
                    expr += ".";
                }
                break;
            case R.id.par:
                if (expr.length() > 0 && (expr.endsWith(")") || Character.isDigit(expr.charAt(expr.length() - 1)))) {
                    if (balance == 0) {
                        outToast("can't insert a right bracket: all brackets are closed");
                    } else {
                        balance--;
                        expr += ")";
                    }
                } else {
                    balance++;
                    expr += "(";
                }
                dot = false;
                break;
            case R.id.add:
                Log.i("OK", " OKa");
                if (expr.length() > 0 && (expr.endsWith(")") || Character.isDigit(expr.charAt(expr.length() - 1)))) {
                    expr += "+";
                }
                dot = false;
                break;
            case R.id.sub:
                expr += "-";
                dot = false;
                break;
            case R.id.mul:
                if (expr.length() > 0 && (expr.endsWith(")") || Character.isDigit(expr.charAt(expr.length() - 1)))) {
                    expr += "*";
                }
                dot = false;
                break;
            case R.id.div:
                if (expr.length() > 0 && (expr.endsWith(")") || Character.isDigit(expr.charAt(expr.length() - 1)))) {
                    expr += "/";
                }
                dot = false;
                break;
            default:
                if (expr.length() == 0 || !expr.endsWith(")")) {
                    expr += button.getText().toString();
                }
        }

        result.setText(expr);
    }
}
