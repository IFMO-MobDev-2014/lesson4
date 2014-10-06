package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CalcActivity extends Activity {
    public static final String ERROR = "Calculation Error";
    TextView text;
    CalculationEngine engine;

    public void onClick(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.buttonReset) {
            text.setText("".toCharArray(), 0, 0);
            return;
        }
        if (button.getId() == R.id.buttonDel) {
            if (text.getText().length() != 0) {
                text.setText(text.getText().toString().toCharArray(), 0, text.getText().length() - 1);
            }
            return;
        }
        if (button.getId() == R.id.buttonCount) {
            String answer = "";
            try {
                answer = String.valueOf(engine.calculate(text.getText().toString()));
            } catch (CalculationException e) {
                Toast.makeText(getApplicationContext(), ERROR, Toast.LENGTH_SHORT).show();
            }
            text.setText(answer.toCharArray(), 0, answer.length());
            return;
        }
        text.setText((text.getText().toString() + button.getText()).toCharArray(), 0, text.getText().length() + 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        text = (TextView) findViewById(R.id.textView);
        engine = CalculationEngineFactory.defaultEngine();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calc, menu);
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
