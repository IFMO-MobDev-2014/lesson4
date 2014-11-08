package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setCursorVisible(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void addSymbol(View view) {
        Button curButton = (Button) view;
        EditText expr = (EditText) findViewById(R.id.editText);
        expr.setText(expr.getText().toString() + curButton.getText());
    }

    public void clearEditText(View view) {
        EditText expr = (EditText) findViewById(R.id.editText);
        expr.setText("");
    }

    public void backspaceEditText(View view) {
        EditText expr = (EditText) findViewById(R.id.editText);
        String text = expr.getText().toString();
        if (!text.isEmpty()) {
            text = text.substring(0, text.length() - 1);
        }
        expr.setText(text);
    }

    public void evaluateExpression(View view) {
        EditText expr = (EditText) findViewById(R.id.editText);
        String text = expr.getText().toString();
        try {
            calcIt clt = new calcIt();
            String res = clt.Evaluate(text);
            expr.setText(res);
        } catch (CalculationException err) {
            Toast toast = Toast.makeText(this, "Oops", Toast.LENGTH_SHORT);
            toast.show();
            clearEditText(findViewById(R.id.button3));
        }
    }
}
