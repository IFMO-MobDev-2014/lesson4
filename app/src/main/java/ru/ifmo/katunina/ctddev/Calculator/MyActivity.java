package ru.ifmo.katunina.ctddev.Calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import ru.ifmo.ctddev.katunina.Main.ExpressionParser;
import ru.ifmo.ctddev.katunina.Main.ParsingException;
import ru.ifmo.md.lesson4.CalculateEngine;
import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;
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
    ScrollView svScroll;
    private void findViewsByIds() {
        tv = ((TextView) findViewById(R.id.textView));
        svScroll = ((ScrollView)findViewById(R.id.svScroll));
    }


    public void onArithmeticClick(View view) {
        tv.append(((Button) view).getText());
        svScroll.scrollTo(0, svScroll.getChildAt(0).getHeight());
    }

    CalculationEngine calculator = CalculationEngineFactory.defaultEngine();
    public void onEqualClick(View view) {
        double result = 0;
        try {
            result = calculator.calculate(tv.getText().toString());
        } catch (CalculationException e) {
            Toast.makeText(this,R.string.invalid_expression,Toast.LENGTH_SHORT).show();
        }
        tv.setText(new DecimalFormat("#.############################################").format(result));

    }

    public void onDeleteSymbolClick(View view) {
        String tvString = tv.getText().toString();
        tv.setText(tvString.substring(0, tvString.length() - 1));
    }

    public void onDeleteAllSymbolsClick(View view) {
        tv.setText("");
    }
}
