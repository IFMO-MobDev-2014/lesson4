package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends Activity {
    public TextView text;
    public String exprStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        text = (TextView) findViewById(R.id.textView);
        Button eraseButton = (Button) findViewById(R.id.button12);
        eraseButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                if (exprStr.length() != 0) {
                    exprStr = exprStr.substring(0, exprStr.length() - 1);
                    text.setText(exprStr);
                }
             }
        });
        Button evalB = (Button)findViewById(R.id.button13);
        evalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (exprStr.length() != 0) {
                        Double result = CalculationEngineFactory.defaultEngine().calculate(exprStr);
                        exprStr = result.toString();
                        text.setText(exprStr);
                        if (Double.isInfinite(result) || Double.isNaN(result))
                            exprStr = "";
                    }
                } catch (CalculationException e) {
                    exprStr = "Error";
                    text.setText(exprStr);
                    exprStr = "";
                }
            }
        });

        Button plusB = (Button)findViewById(R.id.button14);
        plusB.setOnClickListener(listenerFactory('+'));
        Button minusB = (Button)findViewById(R.id.button15);
        minusB.setOnClickListener(listenerFactory('-'));
        Button mulB = (Button)findViewById(R.id.button16);
        mulB.setOnClickListener(listenerFactory('*'));
        Button divB = (Button)findViewById(R.id.button17);
        divB.setOnClickListener(listenerFactory('/'));


        Button dotB = (Button)findViewById(R.id.button18);
        dotB.setOnClickListener(listenerFactory('.'));
        Button zeroB = (Button)findViewById(R.id.button22);
        zeroB.setOnClickListener(listenerFactory('0'));
        Button openBrB = (Button)findViewById(R.id.button23);
        openBrB.setOnClickListener(listenerFactory('('));
        Button closeBrB = (Button)findViewById(R.id.button24);
        closeBrB.setOnClickListener(listenerFactory(')'));

        Button oneB = (Button)findViewById(R.id.button25);
        oneB.setOnClickListener(listenerFactory('1'));
        Button twoB = (Button)findViewById(R.id.button26);
        twoB.setOnClickListener(listenerFactory('2'));
        Button threeB = (Button)findViewById(R.id.button27);
        threeB.setOnClickListener(listenerFactory('3'));

        Button fourB = (Button)findViewById(R.id.button28);
        fourB.setOnClickListener(listenerFactory('4'));
        Button fiveB = (Button)findViewById(R.id.button29);
        fiveB.setOnClickListener(listenerFactory('5'));
        Button sixB = (Button)findViewById(R.id.button30);
        sixB.setOnClickListener(listenerFactory('6'));

        Button sevenB = (Button)findViewById(R.id.button31);
        sevenB.setOnClickListener(listenerFactory('7'));
        Button eightB = (Button)findViewById(R.id.button32);
        eightB.setOnClickListener(listenerFactory('8'));
        Button nineB = (Button)findViewById(R.id.button33);
        nineB.setOnClickListener(listenerFactory('9'));

    }

    public View.OnClickListener listenerFactory(final char c)
    {
        return new View.OnClickListener() {
            public void onClick(View v) {
                exprStr += c;
                text.setText(exprStr);
            }
        };
    }
}
