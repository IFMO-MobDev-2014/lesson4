package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.ifmo.md.lesson4.MyCalcEngine.ParseException;

import static ru.ifmo.md.lesson4.CalcActivity.State.*;


public class CalcActivity extends Activity {
    private String currentFormula = "";
    private TextView textView;
    private State state;

    enum State {
        ERROR,
        NORMAL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_activity);
        textView = (TextView) findViewById(R.id.calcResult);
        state = NORMAL;
    }

    public void onButtonClick(View view) {
        Button pressedButton = (Button) view;
        String text = pressedButton.getText().toString();
        int id = pressedButton.getId();
        if (id == R.id.equals) {
            CalculationEngine engine = CalculationEngineFactory.defaultEngine();
            try {
                currentFormula = String.valueOf(engine.calculate(currentFormula));
            } catch (CalculationException e) {
                state = ERROR;
                currentFormula = "Error";
            } catch (ParseException e){
                state = ERROR;
                currentFormula = currentFormula + " is not a valid formula";
            }
            textView.setText(currentFormula);
            textView.invalidate();
        } else if (id == R.id.deleteAll) {
            clearInput();
        } else if (id == R.id.deleteSymbol) {
            deleteSymbol();
        } else {
            updateFormula(text);
        }
    }

    private void clearInput() {
        currentFormula = "";
        if (state == ERROR)
            state = NORMAL;
        textView.setText(currentFormula);
        textView.invalidate();

    }

    private void deleteSymbol() {
        if (!currentFormula.isEmpty()) {
            currentFormula = currentFormula.substring(0, currentFormula.length() - 1);
        }
        if (state == ERROR)
            state = NORMAL;
        textView.setText(currentFormula);
        textView.invalidate();
    }

    private void updateFormula(String symbol) {
        TextView textView = (TextView) findViewById(R.id.calcResult);
        if (state == NORMAL) {
            currentFormula += symbol;
        } else {
            currentFormula = symbol;
            state = NORMAL;
        }
        textView.setText(currentFormula);
        textView.invalidate();
    }
}

