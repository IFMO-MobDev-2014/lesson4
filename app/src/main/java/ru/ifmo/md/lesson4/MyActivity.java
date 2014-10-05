package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MyActivity extends Activity {

    EditText expr;
    EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        expr = (EditText) findViewById(R.id.inputExpression);
        result = (EditText) findViewById(R.id.result);
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

    public void calculate(View view) {
        String s = expr.getText().toString();
        try {
            double resultValue = CalculationEngineFactory.defaultEngine().calculate(s);
            result.setText(Double.toString(resultValue));
        } catch(CalculationException e) {
            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
//        result.setText("asdfasdf");
    }

    public void clickOnTextButton(View view) {
        Button a = (Button) view;
        expr.setText(expr.getText() + a.getText().toString());
    }

    public void back(View view) {
        if (expr.getText().toString().isEmpty())
            return;
        String s = expr.getText().toString();
        s = s.substring(0, s.length() - 1);
        expr.setText(s);
    }

    public void cancel(View view) {
        expr.setText("");
    }
}
