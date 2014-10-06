package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import ru.ifmo.md.lesson4.R;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
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

    public void pressButton(View view) {
        EditText editText1 = (EditText) findViewById(R.id.editText2);
        EditText editText2 = (EditText) findViewById(R.id.editText);

        try {
            String input = editText1.getText().toString();
            input = input.replaceAll("\\s+", "");
            Double x = CalculationEngineFactory.defaultEngine().calculate(input);
            String text = x.toString();
            editText2.setText(text);
        } catch (CalculationException e) {
            e.printStackTrace();
        }
    }
}
