package com.example.alex.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.lesson4.CalculationException;
import com.example.alex.lesson4.R;

public class MyActivity extends Activity {

    int ab=R.id.textView;
    public TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
    }

    public void add0(View view){
        add("0");
    }

    private void add(String i) {
        textView=(TextView) findViewById(ab);
        String str =(String) textView.getText();
        textView.setText(str + i);
    }

    public void add1(View view){
        add("1");
    }
    public void add2(View view){
        add("2");
    }
    public void add3(View view){
        add("3");
    }
    public void add4(View view){
        add("4");
    }
    public void add5(View view){
        add("5");
    }
    public void add6(View view){
        add("6");
    }
    public void add7(View view){
        add("7");
    }
    public void add8(View view){
        add("8");
    }
    public void add9(View view){
        add("9");
    }
    public void addop(View view){
        add("(");
    }
    public void addcl(View view){
        add(")");
    }
    public void adddot(View view){
        add(".");
    }
    public void addplus(View view){
        add("+");
    }
    public void addminus(View view){
        add("-");
    }
    public void addmul(View view){
        add("*");
    }
    public void adddev(View view){
        add("/");
    }

    public void calc(View view){
        textView=(TextView) findViewById(ab);
        String str=(String)textView.getText();
        if (str.equals("")) return;
        double g= 0;
        try {
            g = com.example.alex.lesson4.CalculationEngineFactory.defaultEngine().calculate(str);
            textView.setText(Double.toString(g));
        } catch (CalculationException e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Некорректная строка.", Toast.LENGTH_SHORT);
            toast.show();

        }


    }
    public void clear(View view){
        textView=(TextView) findViewById(ab);
        textView.setText("");
    }
    public void backspace(View view){
        textView=(TextView) findViewById(ab);
        String str=(String)textView.getText().toString();
        if (!str.equals(""))
        textView.setText(str.substring(0,str.length()-1));
    }

}
