package ru.ifmo.katunina.ctddev.Calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import ru.ifmo.ctddev.katunina.Main.ExpressionParser;
import ru.ifmo.ctddev.katunina.Main.ParsingException;
import ru.ifmo.md.lesson4.R;

/**
 * Created by Евгения on 04.10.2014.
 */
public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_layout);
        findViewsByIds();
    }

    TextView tv ;
    private void findViewsByIds() {
        tv = ((TextView) findViewById(R.id.textView));
    }


    public void onArithmeticClick(View view) {
        tv.append(((Button) view).getText());
    }

    public void onEqualClick(View view) {
        double result = 0;
        try {
            result = ExpressionParser.parseDoubleExpression(tv.getText().toString()).evaluate(0.,0.,0.);
        } catch (ParsingException e) {
            Toast.makeText(this,R.string.invalid_expression,Toast.LENGTH_SHORT).show();
        }
        tv.setText(new DecimalFormat("#.############################################").format(result));

    }

    public void onDeleteSymbolClick(View view) {
        String tvString = tv.getText().toString();
        tv.setText(tvString.substring(0,tvString.length()-1));
    }

    public void onDeleteAllSymbolsClick(View view) {
        tv.setText("");
    }
}
