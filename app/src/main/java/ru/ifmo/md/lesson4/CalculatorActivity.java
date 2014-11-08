package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class CalculatorActivity extends Activity {
    private TextView textView;
    private CalculationEngine engine;
    private DecimalFormat df = new DecimalFormat("#");

    OnClickListener printButtons = new OnClickListener() {
        @Override
        public void onClick(View v) {
            textView.append(((Button) v).getText());
        }
    };

    OnClickListener equalButton = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = textView.getText().toString();
            try {
                textView.setText(df.format(engine.calculate(s)));
            } catch (CalculationException e) {
                Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };

    OnClickListener clearButton = new OnClickListener() {
        @Override
        public void onClick(View v) {
            textView.setText("");
        }
    };

    OnClickListener deleteButton = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (textView.getText().length() != 0) {
                textView.setText(textView.getText().subSequence(0, textView.getText().length() - 1));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        df.setMaximumFractionDigits(15);
        engine = CalculationEngineFactory.defaultEngine();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.main_table);
        TableRow tableRow;
        textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.equal).setOnClickListener(equalButton);
        findViewById(R.id.clear).setOnClickListener(clearButton);
        findViewById(R.id.delete).setOnClickListener(deleteButton);
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            tableRow = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < tableRow.getChildCount(); j++) {
                if (!tableRow.getChildAt(j).hasOnClickListeners()) {
                    tableRow.getChildAt(j).setOnClickListener(printButtons);
                }
            }
        }
    }
}
