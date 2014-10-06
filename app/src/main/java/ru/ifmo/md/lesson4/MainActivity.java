package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import ru.ifmo.mb.lesson4.R;
import ru.ifmo.md.lesson4.Calculation.DBZException;

/**
 * Created by Svet on 04.10.2014.
 */
public class MainActivity extends Activity {
    EditText result;
    GridView buttons;
    CalculationEngine calculator = CalculationEngineFactory.defaultEngine();
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main);

        result = (EditText) findViewById(R.id.result);

        buttons = (GridView) findViewById(R.id.buttons);

        ButtonAdapter buttonAdapter = new ButtonAdapter(this);
        buttons.setAdapter(buttonAdapter);

        calculator = CalculationEngineFactory.defaultEngine();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOnTouchListener();
        setOnItemClickListener();
    }

    private void setOnItemClickListener() {
        buttons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView key = (TextView) view.findViewById(R.id.key);
                String keyText = key.getText().toString();
                String curText = result.getText().toString();
                if (keyText.equals("CE") || keyText.equals("C")) {
                    result.setText("");
                } else if (keyText.equals("Back")) {
                    if (!curText.isEmpty() && !(result.getSelectionStart() == 0)) {
                        int pos = result.getSelectionStart();
                        result.setText(curText.substring(0, pos - 1).concat(curText.substring(pos)));
                        result.setSelection(pos - 1);
                    }
                } else if (keyText.equals("=")) {
                    try {
                        double res = calculator.calculate(curText);
                        String value = Double.toString(res);
                        result.setText(value);
                        result.setSelection(value.length());
                    } catch (CalculationException exc) {
                        showMessage("Wrong input line");
                    } catch (DBZException exc) {
                        showMessage("Division by zero");
                    }
                } else {
                    int pos = result.getSelectionStart();
                    String value = curText.substring(0, pos).concat(keyText).concat(curText.substring(pos));
                    result.setText(value);
                    result.setSelection(pos + 1);
                }
            }
        });
    }

    private void setOnTouchListener() {
        result.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Layout layout = ((EditText) v).getLayout();
                        float x = event.getX() + result.getScrollX();
                        int offset = layout.getOffsetForHorizontal(0, x);
                        if (offset > 0)
                            if (x > layout.getLineMax(0))
                                result.setSelection(offset);     // touch was at end of text
                            else
                                result.setSelection(offset - 1);
                        break;
                }
                return true;
            }
        });
    }

    private void showMessage(String message) {
        final String m = message;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, m, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
