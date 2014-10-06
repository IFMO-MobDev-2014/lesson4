package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MyActivity extends Activity {
    EditText input;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //input = (EditText)findViewById(R.id.inputExpr);
        //result = (TextView)findViewById(R.id.result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void myListener(View view) {
        String button = (String)((Button)findViewById(view.getId())).getText();
        TextView text = (TextView)findViewById(R.id.editText);

        if (button.equals("=")) {
            try {
                CharSequence seq = text.getText();
                String expression = "";
                for (int i = 0; i < seq.length(); i++) {
                    expression += seq.charAt(i);
                }
                text.setText(Double.toString(CalculationEngineFactory.defaultEngine().calculate(expression)));
            } catch (CalculationException e) {
                text.setText("Error");
            }
        } else if (button.equals("D")) {
            String last = text.getText().toString();
            if (last.length() > 0)
                last = last.substring(0, last.length() - 1);
            text.setText(last);
        } else if (button.equals("C")) {
            text.setText("");
        } else {
            text.append(button);
        }
    }
}
