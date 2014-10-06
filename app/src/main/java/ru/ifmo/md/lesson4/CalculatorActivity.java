package ru.ifmo.md.lesson4;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class CalculatorActivity extends FragmentActivity {
    public static final String SAVED_INSTANCE_TOKEN_LENGTHS = "ru.ifmo.md.lesson4.CalculatorActivity.SAVED_INSTANCE_TOKEN_LENGTHS";
    EditText input;
    Stack<Integer> tokenLengths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ViewPager pager = (ViewPager) findViewById(R.id.button_pager);
        pager.setAdapter(new ButtonsAdapter(this, R.array.basic_operations, R.array.engineering_calculator));
        input = (EditText) findViewById(R.id.input_text);
        input.setKeyListener(null);

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_TOKEN_LENGTHS))
            tokenLengths = (Stack<Integer>) savedInstanceState.getSerializable(SAVED_INSTANCE_TOKEN_LENGTHS);
        else
            tokenLengths = new Stack<>();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_INSTANCE_TOKEN_LENGTHS, tokenLengths);
    }

    public void onButtonTap(View button) {
        String text = ((TextView) button).getText().toString();
        if (text.equals("=")) {
            text = input.getText().toString();
            try {
                text = Double.toString(CalculationEngineFactory.defaultEngine().calculate(text));
            } catch (CalculationException e) {
                Toast.makeText(this, String.format(getString(R.string.syntax_error)
                        , e.getExpression().charAt(e.getPosition()),
                        e.getPosition()), Toast.LENGTH_LONG).show();
                return;
            } catch (ArithmeticException e) {
                Toast.makeText(this, getString(R.string.arithmetic_error), Toast.LENGTH_LONG).show();
                return;
            }
            tokenLengths.clear();
            tokenLengths.push(text.length());
            input.setText(text);
        } else {
            tokenLengths.push(text.length());
            input.setText(input.getText() + text);
        }
    }

    public void onBackspace(View button) {
        if (!tokenLengths.isEmpty()) {
            CharSequence text = input.getText();
            input.setText(text.subSequence(0, text.length() - tokenLengths.pop()));
        }
    }
}
