package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by creed on 05.10.14.
 */

public class MainActivity extends Activity {
    final MyCalculateEngine mySuperEngine = new MyCalculateEngine();
    public class CalcButton extends Button {
        public String value;
        public EditText input;
        CalcButton(Context ctx, String value, EditText input){
            super(ctx);
            this.value = value;
            this.input = input;
            this.setText(value);
            this.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT
                    )
            );
            this.setOnClickListener(new CalcOnClickListener());
        }
    }

    public class CalcOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText e = ((CalcButton)view).input;
            String tmp = e.getText().toString();
            String btnName = ((CalcButton)view).value;
            //String newValue = "";
            if (btnName.equals("DelLast")) {
                if (tmp.length() > 0) {
                    e.setText(tmp.substring(0, tmp.length() - 1));
                }
            } else if (btnName.equals("DelAll")) {
                e.setText("");
            } else if (btnName.equals("=")) {
                //Toast.makeText(view.getContext(), "Calculating...", Toast.LENGTH_SHORT).show();
                if (!tmp.isEmpty()) {
                    try {
                        e.setText(Double.toString(mySuperEngine.calculate(tmp)));
                    } catch (CalculationException ce) {
                        e.setText("Parsing error...");
                    }
                }
            } else {
                e.setText(tmp + btnName);
            }
            e.setSelection(e.getText().length());
            //String newVal = this.input.getText().toString();
            //e.setSelection()
            //Toast.makeText(view.getContext(), ((Button) view).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_layout);

        LinearLayout main = (LinearLayout)findViewById(R.id.main);

        /* Adding EditText*/
        EditText input = new EditText(this);
        main.addView(input);

        /* Adding Buttons*/
        TableLayout table = new TableLayout(this);
        for(int i = 0; i < 3; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT
                    )
            );

            for(int j = 0; j < 3; j++) {
                Integer val = 3 * i + j + 1;
                CalcButton btn = new CalcButton(this, val.toString(), input);
                row.addView(btn);
            }
            if (i == 0) {
                row.addView(new CalcButton(this, "DelAll", input));
                row.addView(new CalcButton(this, "DelLast", input));

            } else if (i == 1) {
                row.addView(new CalcButton(this, "+", input));
                row.addView(new CalcButton(this, "-", input));
            } else {
                row.addView(new CalcButton(this, "*", input));
                row.addView(new CalcButton(this, "/", input));
            }

            //System.out.println("Row.height = " +row.getHeight());
            table.addView(row);
            //System.out.println("Childrens = "+((Integer)table.getChildCount()).toString());
        }
        TableRow row = new TableRow(this);
        row.setLayoutParams(
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT
                )
        );
        row.addView(new CalcButton(this, ".", input));
        row.addView(new CalcButton(this, "0", input));
        row.addView(new CalcButton(this, "(", input));
        row.addView(new CalcButton(this, ")", input));
        row.addView(new CalcButton(this, "=", input));
        table.addView(row);
        main.addView(table);

    }
}

