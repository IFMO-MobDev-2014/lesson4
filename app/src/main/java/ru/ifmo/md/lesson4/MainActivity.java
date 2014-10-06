package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    private TextView textView;
    public void calculate(View view) {
        /*if(textView.getText().equals("∞") || textView.getText().equals("-∞")) {
            return;
        }*/
        try {
            String result = Double.toString(CalculationEngineFactory.defaultEngine().calculate(textView.getText().toString()));
            if(result.equals("Infinity")) {
                result = "∞";
            } else if(result.equals("-Infinity")) {
                result = "-∞";
            }
            textView.setText(result);
        } catch(CalculationException e) {
            textView.setText("ERROR");
        }
    }

    public void onNumberClick(View view) {
        String symbol = ((Button) view).getText().toString();
            if(symbol.equals("×")) {
                symbol = "*";
            } else if(symbol.equals("÷")) {
                symbol = "/";
            } else if(symbol.equals("−")) {
                symbol = "-";
            }
        textView.setText(textView.getText().toString() + symbol);
    }

    public void onCe(View view) {
        textView.setText("");
    }

    public void onBackspace(View view) {
        if(textView.getText().length() > 0) {
            textView.setText(textView.getText().subSequence(0, textView.getText().length() - 1));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }
}
