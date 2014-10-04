package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    EditText etInputExpression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInputExpression = (EditText) findViewById(R.id.etInputExpression);
    }

    private void setInputExpression(String newInputExpression) {
        etInputExpression.setText(newInputExpression);
    }

    private String getInputExpression() {
        return String.valueOf(etInputExpression.getText());
    }

    public void onKeyboardClicked(View view) {
        int buttonID = view.getId();
        String addSymbols = null;
        switch (buttonID) {
            case R.id.buttonDigit0: addSymbols = "0"; break;
            case R.id.buttonDigit1: addSymbols = "1"; break;
            case R.id.buttonDigit2: addSymbols = "2"; break;
            case R.id.buttonDigit3: addSymbols = "3"; break;
            case R.id.buttonDigit4: addSymbols = "4"; break;
            case R.id.buttonDigit5: addSymbols = "5"; break;
            case R.id.buttonDigit6: addSymbols = "6"; break;
            case R.id.buttonDigit7: addSymbols = "7"; break;
            case R.id.buttonDigit8: addSymbols = "8"; break;
            case R.id.buttonDigit9: addSymbols = "9"; break;
            case R.id.buttonDigit00:addSymbols = "00"; break;
            case R.id.buttonPoint:  addSymbols = "."; break;
            case R.id.buttonOpen:   addSymbols = "("; break;
            case R.id.buttonClose:  addSymbols = ")"; break;
            case R.id.buttonPower:  addSymbols = "^"; break;
            case R.id.buttonPlus:   addSymbols = "+"; break;
            case R.id.buttonMinus:  addSymbols = "-"; break;
            case R.id.buttonMultiply: addSymbols = "*"; break;
            case R.id.buttonDivide: addSymbols = "/"; break;
            case R.id.buttonClear: {
                setInputExpression("");
                return;
            }
            case R.id.buttonDelete: {
                String current = getInputExpression();
                if (!current.isEmpty())
                    current = current.substring(0, current.length() - 1);
                setInputExpression(current);
                return;
            }
            case R.id.buttonCalculate: {
                IntelligentCalculateEngine myEngine = new IntelligentCalculateEngine();
                try {
                    double result = myEngine.calculate(getInputExpression());
                    setInputExpression(String.valueOf(result));
                } catch (CalculationException e) {
                    setInputExpression("ERROR");
                    e.printStackTrace();
                }
                return;
            }
            default: {}
        }
        setInputExpression(getInputExpression() + addSymbols);
    }

}
