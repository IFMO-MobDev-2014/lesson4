package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Home on 02.10.2014.
 */

public class Main extends Activity {

    TextView field;

    public void defaultOnClick(View view) {
        Button thisButton = (Button) view;
        if (field.getText().toString().equals("Error") || field.getText().toString().equals("Infinity") || field.getText().toString().equals("-Infinity"))
            field.setText("");
        if (thisButton.getText().equals("=")) {
            String s = field.getText().toString();
            s = s.replaceAll("×", "*").replaceAll("−", "-").replaceAll("÷", "/");
            try {
                field.setText(Double.toString(CalculationEngineFactory.defaultEngine().calculate(s)));
            } catch (CalculationException e) {
                field.setText("Error");
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (thisButton.getText().equals("( )")) {
            Character c;
            String s = field.getText().toString();
            if (s.isEmpty())
                c = '#';
            else
                c = s.charAt(s.length() - 1);
            if (c == '+' || c == '−' || c == '×' || c == '÷' || c == '(' || c == '#')
                field.setText(field.getText().toString().concat("("));
            else {
                int balance = 0;
                for (int i = 0; i < s.length(); i++)
                    if (s.charAt(i) == '(')
                        balance++;
                    else if (s.charAt(i) == ')')
                        balance--;
                if (balance > 0)
                    field.setText(field.getText().toString().concat(")"));
            }
        } else if (thisButton.getText().equals("←")) {
            if (!field.getText().toString().isEmpty())
                field.setText(field.getText().toString().substring(0, field.getText().toString().length() - 1));
        } else if (thisButton.getText().equals("C")) {
            field.setText("");
        } else {
            field.setText(field.getText().toString().concat(thisButton.getText().toString()));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        field = (TextView) findViewById(R.id.textView);

    }

}
