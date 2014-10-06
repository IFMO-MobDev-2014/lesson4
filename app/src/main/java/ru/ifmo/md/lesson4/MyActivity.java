package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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

    public void onClick(View view) {
        String button = (String)((Button)findViewById(view.getId())).getText();
        TextView text = (TextView)findViewById(R.id.textView);

        if (button.equals("=")) {
            try {
                CharSequence sc = text.getText();
                String ss = "";
                for (int i = 0; i < sc.length(); i++) {
                    ss += sc.charAt(i);
                }
                text.setText(Double.toString(CalculationEngineFactory.defaultEngine().calculate(ss)));
            } catch (CalculationException e) {
                text.setText(e.getMessage());
            }
        } else if (button.equals("del")) {
            CharSequence old = text.getText();
            old = old.subSequence(0, old.length() - 1);
            if (old.length() == 0 || Character.isLetter(old.charAt(0))) {
                old = "0";
            }
            text.setText(old);
        } else if (button.equals("C")) {
            text.setText("0");
        } else {
            if (text.getText().length() == 1 && text.getText().charAt(0) == '0' ||
                    Character.isLetter(text.getText().charAt(0))) {
                text.setText("");
            }
            text.append(button);
        }
    }
}
