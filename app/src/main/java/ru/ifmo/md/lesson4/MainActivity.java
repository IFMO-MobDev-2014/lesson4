package ru.ifmo.md.lesson4;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    EditText op;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        op = (EditText)findViewById(R.id.editText);
        tv = (TextView)findViewById(R.id.textView2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private static final String ERROR_MSG = "Error!";
    private void addText(String add) {
        tv.setText("");
        String str = op.getText().toString();
        op.setText(str + add);
    }
    public void click1(View view) {
        addText("1");
    }
    public void click2(View view) {
        addText("2");
    }
    public void click3(View view) {
        addText("3");
    }
    public void click4(View view) {
        addText("4");
    }
    public void click5(View view) {
        addText("5");
    }
    public void click6(View view) {
        addText("6");
    }
    public void click7(View view) {
        addText("7");
    }
    public void click8(View view) {
        addText("8");
    }
    public void click9(View view) {
        addText("9");
    }
    public void click0(View view) {
        addText("0");
    }
    public void clickAdd(View view) {
        addText("+");
    }
    public void clickSub(View view) {
        addText("-");
    }
    public void clickMul(View view) {
        addText("*");
    }
    public void clickDiv(View view) {
        addText("/");
    }
    public void clickLBr(View view) {
        addText("(");
    }
    public void clickRBr(View view) {
        addText(")");
    }
    public void clickDot(View view) {
        addText(".");
    }

    public void clickBack(View view) {
        tv.setText("");
        String str = op.getText().toString();
        if (!str.isEmpty()) {
            op.setText(str.substring(0, str.length() - 1));
        }
    }

    public void clickClear(View view) {
        tv.setText("");
        op.setText("");
    }

    private CalculationEngine calc = CalculationEngineFactory.defaultEngine();
    public void clickCount(View view) {

        double result = 0.0;
        try {
            result = calc.calculate(op.getText().toString());
        } catch (CalculationException e) {
            tv.setText(ERROR_MSG);
            return;
        }

        tv.setText("" + result);
    }
}
