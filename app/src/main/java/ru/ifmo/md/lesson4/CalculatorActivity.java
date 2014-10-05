package ru.ifmo.md.lesson4;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends FragmentActivity {
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ViewPager pager = (ViewPager) findViewById(R.id.button_pager);
        pager.setAdapter(new ButtonsAdapter(this, R.array.basic_operations, R.array.engineering_calculator));
        input = (EditText) findViewById(R.id.input_text);
        input.setKeyListener(null);
    }

    public void onButtonTap(View button) {
        String text = ((TextView) button).getText().toString();
        if (text.equals("=")) {
            text = input.getText().toString();
            try {
                text = Double.toString(CalculationEngineFactory.defaultEngine().calculate(text));
            } catch (CalculationException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            } finally {
                input.setText(text);
            }
        } else
            input.setText(input.getText() + text);
    }

    public void onBackspace(View button) {
        CharSequence text = input.getText();
        if (text.length() > 0)
            input.setText(text.subSequence(0, text.length() - 1));
    }
}
