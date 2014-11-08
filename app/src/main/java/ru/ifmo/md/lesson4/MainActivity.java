package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView textViewExpression;
    private TextView textViewResult;
    private MyCalculateEngine calculateEngine;
    private String expression = "";
    private AsyncTask calcTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewExpression = (TextView) findViewById(R.id.textViewExpression);
        textViewResult = (TextView) findViewById(R.id.textViewResult);

        final String[] buttons = new String[]{
                "0", "1", "2", "3",
                "4", "5", "6", "7",
                "8", "9", ".", "∞",
                "+", "-", "*", "/",
                "(", ")", "⇐", "C"
        };

        int rowNumber = 0;
        int columnNumber = 0;
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        TableRow tableRow = null;
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1.0f);
        TableRow.LayoutParams rowLayoutParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1.0f);
        for (int i = 0; i < buttons.length; i++) {
            if (columnNumber == 0) {
                tableRow = new TableRow(this);
                tableLayout.addView(tableRow, tableLayoutParams);
            }
            Button button = new Button(this);
            tableRow.addView(button, rowLayoutParams);
            final String text = buttons[i];
            button.setText(text);
            button.setTextSize(40);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicked(text);
                }
            });
            columnNumber = (columnNumber + 1) % 4;
            if (columnNumber == 0) rowNumber++;
        }

        calculateEngine = new MyCalculateEngine();
    }

    public void clicked(String s) {
        if (s.equals("⇐")) {
            if (!expression.isEmpty()) expression = expression.substring(0, expression.length() - 1);
        } else if (s.equals("C")) {
            expression = "";
        } else {
            expression += s;
        }
        textViewExpression.setText(expression);
        if (expression.isEmpty()) {
            textViewResult.setText("");
        } else {
            if (calcTask != null) calcTask.cancel(true);
            calcTask = new CalculationTask().execute(expression);
        }
    }

    private class CalculationTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String s = strings[0];
            double a;
            try {
                a = calculateEngine.calculate(s);
                return Double.toString(a);
            } catch (CalculationException e) {
                Log.e("", "", e);
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult.setText(result);
        }
    }
}
