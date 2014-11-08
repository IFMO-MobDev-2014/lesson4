package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;


public class Calculator extends Activity implements OnClickListener {
    GridView grid;
    static final String[] letters = new String[] {
            "(", ")", "C", "+",
            "7", "8", "9", "-",
            "4", "5", "6", "/",
            "1", "2", "3", "*",
            "0", ".", "AC", "=" };



    EditText editExpression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        editExpression = (EditText) findViewById(R.id.editExpression);

        grid = (GridView) findViewById(R.id.gridView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, letters);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String str = (String) ((TextView) v).getText();
                if (str.equals("AC")) {
                    editExpression.setText("");
                } else
                if (str.equals("C")) {
                    editExpression.setText(editExpression.getText().subSequence(0,editExpression.getText().length()-1));
                } else
                if (str.equals("=")) {
                    try {
                        editExpression.setText(Double.toString(CalculationEngineFactory.defaultEngine().calculate(editExpression.getText().toString())));
                    } catch (CalculationException e) {
                        editExpression.setText("Error");
                    }
                } else {
                    editExpression.setText(editExpression.getText()+str);
                }
                //Toast.makeText(getApplicationContext(),
              //  ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gridView:
                try {
                    editExpression.setText(Double.toString(CalculationEngineFactory.defaultEngine().calculate(editExpression.getText().toString())));
                    break;
                } catch (CalculationException e) {
                    editExpression.setText("ERROR");
                    break;
                }
            default:
                break;
        }
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
