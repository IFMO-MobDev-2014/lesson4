package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    private int [] calculatorBank = {
        R.id.number_0,
        R.id.number_1,
        R.id.number_2,
        R.id.number_3,
        R.id.number_4,
        R.id.number_5,
        R.id.number_6,
        R.id.number_7,
        R.id.number_8,
        R.id.number_9,
        R.id.delete,
        R.id.delete_all,
        R.id.divide,
        R.id.multiply,
        R.id.minus,
        R.id.plus,
        R.id.equally,
        R.id.sign,
        R.id.comma,
        R.id.left_bracket,
        R.id.right_bracket,
    };

    private View.OnClickListener listener;
    private String text;
    private EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = "";
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.equally) {
                    double result;
                    try {
                        result = CalculationEngineFactory.defaultEngine().calculate(text);
                    } catch (CalculationException e) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    text = Double.toString(result);
                    editText.setText(text);
                    return;
                } if (id == R.id.delete_all) {
                    text = "";
                } else if (id == R.id.delete) {
                    if (text.length() != 0) {
                        text = text.substring(0, text.length() - 1);
                    }
                    else text = "";
                }
                else if (id == R.id.sign) {
                    boolean isNumber;
                    try {
                        Double.parseDouble(text);
                        isNumber = true;
                    } catch (NumberFormatException e) {
                        isNumber = false;
                    }
                    if (!isNumber) {
                        return;
                    }
                    if (text.length() == 0) {
                        text = "";
                    } else if (text.charAt(0) == '-') {
                        text = text.substring(1, text.length());
                    }
                    else text = '-' + text;
                } else {
                    text = text + ((Button)view).getText();
                }
                editText = (EditText)findViewById(R.id.expression);
                editText.setText(text);
            }
        };
        for (int id : calculatorBank) {
            Button b = (Button) findViewById(id);
            b.setOnClickListener(listener);
        }
    }

}
