package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        return (String) tvResult.getText();
    }

    private void setResultTextView(String result) {
        tvResult.setText(result);
    }

    public void onKeyboardClicked(View view) {
        int buttonID = view.getId();
        String addSymbols;
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
            case R.id.buttonPoint:  addSymbols = "."; break;
            case R.id.buttonOpen:   addSymbols = "("; break;
            case R.id.buttonClose:  addSymbols = ")"; break;
            case R.id.buttonPower:  addSymbols = "^"; break;
            case R.id.buttonPlus:   addSymbols = "+"; break;
            case R.id.buttonMinus:  addSymbols = "-"; break;
            case R.id.buttonMultiply: addSymbols = "*"; break;
            case R.id.buttonDivide: addSymbols = "/"; break;
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
                setResultTextView(String.valueOf(result));
                return;
            }
            default: {
                addSymbols = "0";
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
            addSymbols = getInputExpression() + addSymbols;
        setInputExpression(addSymbols);
    }

}
