package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Calculator extends Activity {

    TextView resView;
    GridLayout lay;
    CalculationEngine calc = CalculationEngineFactory.defaultEngine();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        lay = (GridLayout) findViewById(R.id.grid);
        resView = (TextView) findViewById(R.id.resView);
        String someText = "+-/*.()";
        for (int i = 0; i < 17; i++) {
            Button tmp = new Button(lay.getContext());
            String text = "" + i;
            if (i >= 10) {
                text = "" + someText.charAt(i - 10);
            }
            tmp.setText(text);
            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resView.append(((Button) view).getText());
                }
            });
            lay.addView(tmp);
        }
        Button eval = new Button(lay.getContext());
        eval.setText("=");
        Button erase = new Button(lay.getContext());
        erase.setText("E");
        eval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Log.i("some", resView.getText().toString());
                    resView.setText("" + calc.calculate(resView.getText().toString()));
                } catch (CalculationException e) {
                    e.printStackTrace();
                }
            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resView.setText("");
            }
        });
        lay.addView(eval);
        lay.addView(erase);
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
}
