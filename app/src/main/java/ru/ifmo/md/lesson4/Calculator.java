package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Calculator extends Activity {

    Expression expr = new Expression();
    TextView text;
    boolean exRes = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        text = ((TextView) findViewById(R.id.textResult));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator, menu);
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

    public void settingText() {
        text.setText(expr.getText());
    }

    public void settingText(Double res) {
        text.setText(res.toString());
    }

    public void but14 (View view) {
        try {
            settingText(CalculationEngineFactory.defaultEngine().calculate(expr.getText()));
        } catch (final CalculationException e) {
            exRes = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Calculator.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        if (exRes) expr.toEmpty();
        else exRes = true;
    }

    public void but0 (View view) {
        expr.add("1");
        settingText();
    }

    public void but1 (View view) {
        expr.add("2");
        settingText();
    }

    public void but2 (View view) {
        expr.add("3");
        settingText();
    }

    public void but3 (View view) {
        expr.add("+");
        settingText();
    }

    public void but4 (View view) {
        expr.poll();
        settingText();
    }

    public void but5 (View view) {
        expr.add("4");
        settingText();
    }

    public void but6 (View view) {
        expr.add("5");
        settingText();
    }

    public void but7 (View view) {
        expr.add("6");
        settingText();
    }

    public void but8 (View view) {
        expr.add("-");
        settingText();
    }

    public void but9 (View view) {
        expr.add("/");
        settingText();
    }

    public void but10 (View view) {
        expr.add("7");
        settingText();
    }

    public void but11 (View view) {
        expr.add("8");
        settingText();
    }

    public void but12 (View view) {
        expr.add("9");
        settingText();
    }

    public void but13 (View view) {
        expr.add("*");
        settingText();
    }

    public void but15 (View view) {
        expr.add(".");
        settingText();
    }

    public void but16 (View view) {
        expr.add("0");
        settingText();
    }

    public void but17 (View view) {
        expr.add("(");
        settingText();
    }

    public void but18 (View view) {
        expr.add(")");
        settingText();
    }
}
