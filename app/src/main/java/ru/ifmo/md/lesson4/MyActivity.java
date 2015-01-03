package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {
    TextView textView;
    String resultString = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        textView = (TextView) findViewById(R.id.textView);
        refreshText();
    }

    public void onClickChar(View view) {
        if (resultString.length() < 30) {
            Button charButton = (Button) view;
            if (resultString.equals("0") && !charButton.getText().toString().equals(".")) {
                resultString = charButton.getText().toString();
            } else {
                resultString += charButton.getText();
            }
            refreshText();
        }
    }

    public void onClickBackspace(View view) {
        if (resultString.length() <= 1) {
            resultString = "0";
        } else {
            resultString = resultString.substring(0, resultString.length() - 1);
        }
        refreshText();
    }

    public void onClickErase(View view) {
        resultString = "0";
        refreshText();
    }

    public void onClickEvaluate(View view) {
        try {
            resultString = Double.toString(CalculationEngineFactory.defaultEngine().calculate(resultString));
        } catch (CalculationException e) {
            Toast.makeText(this, "Invalid expression", Toast.LENGTH_LONG).show();
        }
        refreshText();
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void refreshText() {
        textView.setText(resultString);
    }
}
