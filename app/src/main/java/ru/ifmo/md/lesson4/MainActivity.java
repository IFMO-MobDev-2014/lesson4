package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import ru.ifmo.md.lesson4.logic.ExceptionsHandle.ParserException;
import ru.ifmo.md.lesson4.logic.ExceptionsHandle.EvaluationException;
import ru.ifmo.md.lesson4.logic.Expression;
import ru.ifmo.md.lesson4.logic.ExpressionParser;

public class MainActivity extends Activity {


    EditText etInputExpression;
    TextView tvResult;
    DeleteButtonAction deleteButtonAction = DeleteButtonAction.DELETE_LAST_SYMBOL;
    Button deleteButton;

    enum DeleteButtonAction {
        DELETE_LAST_SYMBOL,
        DELETE_EVERYTHING
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInputExpression = (EditText) findViewById(R.id.etInputExpression);
        tvResult = (TextView) findViewById(R.id.tvResult);
        deleteButton = (Button) findViewById(R.id.buttonDelete);
    }

    private void setInputExpression(String newInputExpression) {
        etInputExpression.setText(newInputExpression);
    }

    private String getInputExpression() {
        return String.valueOf(etInputExpression.getText());
    }

    private String getResultTextView() {
        return String.valueOf(tvResult.getText());
    }

    private void setResultTextView(String result) {
        tvResult.setText(result);
    }

    public void onKeyboardClicked(View view) {
        int buttonID = view.getId();
        String newSymbols;
        switch (buttonID) {
            case R.id.buttonDigit0: newSymbols = "0"; break;
            case R.id.buttonDigit1: newSymbols = "1"; break;
            case R.id.buttonDigit2: newSymbols = "2"; break;
            case R.id.buttonDigit3: newSymbols = "3"; break;
            case R.id.buttonDigit4: newSymbols = "4"; break;
            case R.id.buttonDigit5: newSymbols = "5"; break;
            case R.id.buttonDigit6: newSymbols = "6"; break;
            case R.id.buttonDigit7: newSymbols = "7"; break;
            case R.id.buttonDigit8: newSymbols = "8"; break;
            case R.id.buttonDigit9: newSymbols = "9"; break;
            case R.id.buttonPoint:  newSymbols = "."; break;
            case R.id.buttonOpen:   newSymbols = "("; break;
            case R.id.buttonClose:  newSymbols = ")"; break;
            case R.id.buttonPower:  newSymbols = "^"; break;
            case R.id.buttonPlus:   newSymbols = "+"; break;
            case R.id.buttonMinus:  newSymbols = "-"; break;
            case R.id.buttonMultiply: newSymbols = "*"; break;
            case R.id.buttonDivide: newSymbols = "/"; break;
            case R.id.buttonDelete: {
                if (deleteButtonAction == DeleteButtonAction.DELETE_LAST_SYMBOL) {
                    String current = getInputExpression();
                    if (!current.isEmpty())
                        current = current.substring(0, current.length() - 1);
                    if (current.isEmpty())
                        current = "0";
                    setInputExpression(current);
                } else {
                    setInputExpression("0");
                }
                return;
            }
            case R.id.buttonCalculate: {
                deleteButtonAction = DeleteButtonAction.DELETE_EVERYTHING;
                deleteButton.setText(R.string.deleteAll);
                ExpressionParser parser = new ExpressionParser();
                Expression expression;
                double result;
                try {
                    expression = parser.parse(getInputExpression());
                    try {
                        result = expression.evaluate();
                    } catch (EvaluationException e) {
                        setResultTextView(e.getMessage());
                        return;
                    }
                } catch (ParserException e) {
                    setResultTextView(e.getMessage());
                    return;
                }
                setResultTextView(BigDecimal.valueOf(result).toString());
                return;
            }
            default: {
                newSymbols = "0";
            }
        }
        if (deleteButtonAction == DeleteButtonAction.DELETE_EVERYTHING) {
            deleteButtonAction = DeleteButtonAction.DELETE_LAST_SYMBOL;
            deleteButton.setText(R.string.deleteLast);
            String currentText = getResultTextView();
            if (!currentText.isEmpty())
                currentText = "Ans = " + currentText;
            tvResult.setText(currentText);
            setInputExpression("0");
        }
        if (!getInputExpression().equals("0"))
            newSymbols = getInputExpression() + newSymbols;
        setInputExpression(newSymbols);
    }

}
