package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        String expression;
        double result;
        EditText inputText = (EditText) findViewById(R.id.editText);
        TextView txt = (TextView) findViewById(R.id.textView);
        Button btn = (Button) view;
        CalculationEngine calculationEngine = CalculationEngineFactory.defaultEngine();

        expression = inputText.getText().toString();
        if(btn.getText().equals("c")){
            expression = "";
        }else if (btn.getText().equals("←")) {
           expression = expression.substring(0,expression.length() - 1);
        } else if(btn.getText().equals("=") && !expression.equals("")) {
            try {
                result = calculationEngine.calculate(expression);
                txt.setText(Double.toString(result));
            } catch (CalculationException e) {
                e.printStackTrace();
                txt.setText("ERROR: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                txt.setText("ERROR!!!");
                Log.e("adf", "ASFFAF");
            }

            txt.invalidate();
        } else {
            expression += btn.getText();
        }
        inputText.setText(expression);
        inputText.invalidate();

    }
}
