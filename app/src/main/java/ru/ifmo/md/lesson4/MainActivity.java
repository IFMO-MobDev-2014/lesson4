package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends Activity {
    private TextView expressionText;
    private final static CalculationEngine calculationEngine = CalculationEngineFactory.defaultEngine();
    private final static DecimalFormat formatter = new DecimalFormat("#0");

    private void setOnClickListener(final View view, final String text) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionText.setText(expressionText.getText() + text);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formatter.setMaximumFractionDigits(6);
        setContentView(R.layout.activity_main);
        Button resultButton = (Button) findViewById(R.id.resultButton);
        expressionText = (TextView) findViewById(R.id.expressionText);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Double result = calculationEngine.calculate(expressionText.getText().toString());
                    expressionText.setText(formatter.format(result));
                } catch (CalculationException e) {
                    expressionText.setText("Error");
                }
            }
        });
        setOnClickListener(findViewById(R.id.buttonZero), "0");
        setOnClickListener(findViewById(R.id.oneButton), "1");
        setOnClickListener(findViewById(R.id.twoButton), "2");
        setOnClickListener(findViewById(R.id.threeButton), "3");
        setOnClickListener(findViewById(R.id.fourButton), "4");
        setOnClickListener(findViewById(R.id.fiveButton), "5");
        setOnClickListener(findViewById(R.id.sixButton), "6");
        setOnClickListener(findViewById(R.id.sevenButton), "7");
        setOnClickListener(findViewById(R.id.eightButton), "8");
        setOnClickListener(findViewById(R.id.nineButton), "9");
        setOnClickListener(findViewById(R.id.buttonPlus), "+");
        setOnClickListener(findViewById(R.id.buttonMinus), "-");
        setOnClickListener(findViewById(R.id.buttonMultiply), "*");
        setOnClickListener(findViewById(R.id.buttonPower), "^");
        setOnClickListener(findViewById(R.id.buttonDivide), "/");
        setOnClickListener(findViewById(R.id.buttonLeftBracket), "(");
        setOnClickListener(findViewById(R.id.buttonRightBracket), ")");
        findViewById(R.id.eraseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expressionText.getText().length() > 0) {
                    expressionText.setText(expressionText.getText().subSequence(0, expressionText.length() - 1));
                }
            }
        });
        findViewById(R.id.clearButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionText.setText("");
            }
        });
    }
}
