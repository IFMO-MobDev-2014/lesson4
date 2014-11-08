package ru.ifmo.ctddev.soloveva.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;
import ru.ifmo.md.lesson4.R;

public class CalculationActivity extends Activity {

    private static final NumberFormat FORMAT = NumberFormat.getInstance(Locale.US);
    static {
        FORMAT.setMaximumFractionDigits(5);
        FORMAT.setGroupingUsed(false);
    }

    private static final CalculationEngine CALCULATION_ENGINE = CalculationEngineFactory.defaultEngine();
    private TextView field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        field = (TextView) findViewById(R.id.field);

        findViewById(R.id.delete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                field.setText("");
                return true;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                String text = field.getText().toString();
                int length = text.length();
                if (length > 0) {
                    field.setText(text.substring(0, length - 1));
                }
                break;
            case R.id.calc:
                try {
                    field.setText(FORMAT.format(CALCULATION_ENGINE.calculate(field.getText().toString())));
                } catch (CalculationException e) {
                    Toast.makeText(this, R.string.invalid_expr, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                field.setText(field.getText().toString().concat(((Button) view).getText().toString()));
                break;
        }
    }
}
