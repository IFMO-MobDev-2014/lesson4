package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.ifmo.md.lesson4.CalculationEngine.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngine.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationEngine.CalculationException;
import ru.ifmo.md.lesson4.Parser.Parser;

/**
 * Created by Женя on 03.10.2014.
 */
public class CalculationActivity extends Activity{
    private EditText inputExpr;
    private CalculationEngine calculator;
    private String nowStr = "";
    private TextView editView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);
        editView = (TextView)findViewById(R.id.editView);
        calculator = CalculationEngineFactory.defaultEngine();

    }
    public void goClick(View view) {
        if (editView.getText().equals("Ошибка вычисления"))
            nowStr = "";

        switch (view.getId()) {
            case R.id.oneBtn:
                nowStr += "1";
                break;
            case R.id.twoBtn:
                nowStr += "2";
                break;
            case R.id.threeBtn:
                nowStr += "3";
                break;
            case R.id.fourBtn:
                nowStr += "4";
                break;
            case R.id.fiveBtn:
                nowStr += "5";
                break;
            case R.id.sixBtn:
                nowStr += "6";
                break;
            case R.id.sevenBtn:
                nowStr += "7";
                break;
            case R.id.eightBtn:
                nowStr += "8";
                break;
            case R.id.nineBtn:
                nowStr += "9";
                break;
            case R.id.zeroBtn:
                nowStr += "0";
                break;
            case R.id.plusBtn:
                nowStr += "+";
                break;
            case R.id.minusBtn:
                nowStr += "-";
                break;
            case R.id.mulBtn:
                nowStr += "*";
                break;
            case R.id.divBtn:
                nowStr += "/";
                break;
            case R.id.backBtn:
                if (nowStr.length() > 0)
                    nowStr = nowStr.substring(0, nowStr.length() - 1);
                break;
            case R.id.dotBtn:
                nowStr += ".";
                break;
            case R.id.eqBtn:
                try {
                    nowStr = "" + calculator.calculate(nowStr);
                } catch (CalculationException e) {
                    nowStr = "Ошибка вычисления";
                }
                break;
            case R.id.lBracketBtn:
                nowStr += "(";
                break;
            case R.id.rBracketBtn:
                nowStr += ")";
                break;
            case R.id.clrBtn:
                nowStr = "";
                break;
        }
        editView.setText(nowStr);
    }
}
