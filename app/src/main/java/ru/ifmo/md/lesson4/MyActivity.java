package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Шолохов on 06.10.2014.
 */
public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calcBut = (Button)findViewById(R.id.ButtonCalc);
        View.OnClickListener calc = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText expressionField = (EditText)findViewById(R.id.editText);
                TextView resultField = (TextView)findViewById(R.id.resultView);
                String result;
                try {
                    result = Double.toString(CalculationEngineFactory.defaultEngine().calculate(expressionField.getText().toString()));
                } catch (Exception e) {
                    result = "Calculation Exception";
                }
                resultField.setText(result);
            }
        };
        calcBut.setOnClickListener(calc);

        View.OnClickListener wr = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText expressionField = (EditText)findViewById(R.id.editText);
                expressionField.setText(expressionField.getText().toString() + ((Button)v).getText().toString());
            }
        };

        View.OnClickListener reset = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText expressionField = (EditText)findViewById(R.id.editText);
                expressionField.setText("");
            }
        };

        View.OnClickListener delete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText expressionField = (EditText)findViewById(R.id.editText);
                String t = expressionField.getText().toString();
                if (!t.isEmpty()) expressionField.setText(t.subSequence(0, t.length()-1));
            }
        };

        Button but0 = (Button)findViewById(R.id.Button0);
        Button but1 = (Button)findViewById(R.id.Button1);
        Button but2 = (Button)findViewById(R.id.Button2);
        Button but3 = (Button)findViewById(R.id.Button3);
        Button but4 = (Button)findViewById(R.id.Button4);
        Button but5 = (Button)findViewById(R.id.Button5);
        Button but6 = (Button)findViewById(R.id.Button6);
        Button but7 = (Button)findViewById(R.id.Button7);
        Button but8 = (Button)findViewById(R.id.Button8);
        Button but9 = (Button)findViewById(R.id.Button9);
        Button butLS = (Button)findViewById(R.id.ButtonLS);
        Button butRS = (Button)findViewById(R.id.ButtonRS);
        Button butPlus = (Button)findViewById(R.id.ButtonPlus);
        Button butMinus = (Button)findViewById(R.id.ButtonSub);
        Button butMul = (Button)findViewById(R.id.ButtonMul);
        Button butDiv = (Button)findViewById(R.id.ButtonDiv);
        Button butReset = (Button)findViewById(R.id.ButtonReset);
        Button butDel = (Button)findViewById(R.id.ButtonDelete);

        but0.setOnClickListener(wr);
        but1.setOnClickListener(wr);
        but2.setOnClickListener(wr);
        but3.setOnClickListener(wr);
        but4.setOnClickListener(wr);
        but5.setOnClickListener(wr);
        but6.setOnClickListener(wr);
        but7.setOnClickListener(wr);
        but8.setOnClickListener(wr);
        but9.setOnClickListener(wr);
        butLS.setOnClickListener(wr);
        butRS.setOnClickListener(wr);
        butPlus.setOnClickListener(wr);
        butMinus.setOnClickListener(wr);
        butMul.setOnClickListener(wr);
        butDiv.setOnClickListener(wr);
        butReset.setOnClickListener(reset);
        butDel.setOnClickListener(delete);

    }
}
