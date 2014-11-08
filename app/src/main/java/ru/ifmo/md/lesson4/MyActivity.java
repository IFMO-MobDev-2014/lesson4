package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {
    private TextView txt;
    private String expr = "0";
    CalculationEngine engine = CalculationEngineFactory.defaultEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        txt = (TextView) findViewById(R.id.textView);
    }

    public void myOnClick(View view) {
        if (expr.length() < 25) {
            Button btn = (Button) view;
            if (expr == "0") expr = btn.getText().toString();
            else expr += btn.getText();
            txt.setText(expr);
        }
    }

    public void onClickDel(View view) {
        if (expr.length() <= 1) {
            expr = "0";
        } else {
            expr = expr.substring(0, expr.length() - 1);
        }
        txt.setText(expr);
    }

    public void onClickDelAll(View view) {
        expr = "0";
        txt.setText(expr);
    }

    public void OnClickCalc(View view) {
        try {
            expr = Double.toString(engine.calculate(expr));
        } catch (CalculationException e) {
            Toast t = Toast.makeText(getApplicationContext(), "Некорректное выражение!", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
        }
        txt.setText(expr);
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
}
