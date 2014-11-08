package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
    private int cursor = 0;
    private double lastTouchedClr = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);
        editView = (TextView)findViewById(R.id.editView);
        calculator = CalculationEngineFactory.defaultEngine();
        ((Button)findViewById(R.id.clrBtn)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    lastTouchedClr = System.nanoTime();
                    return true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    double now = System.nanoTime();
                    if ((now - lastTouchedClr) / 1000000 >= 700) {
                        nowStr = "";
                        editView.setText("_");
                    }
                    return true;
                }
                return false;
            }
        });
        editView.setText("_");
    }
    public void goClick(View view) {
        if (editView.getText().equals("Ошибка вычисления"))
            nowStr = "";

        switch (view.getId()) {
            case R.id.oneBtn:
                insertAtCursor(nowStr, '1');
                break;
            case R.id.twoBtn:
                insertAtCursor(nowStr, '2');
                break;
            case R.id.threeBtn:
                insertAtCursor(nowStr, '3');
                break;
            case R.id.fourBtn:
                insertAtCursor(nowStr, '4');
                break;
            case R.id.fiveBtn:
                insertAtCursor(nowStr, '5');
                break;
            case R.id.sixBtn:
                insertAtCursor(nowStr, '6');
                break;
            case R.id.sevenBtn:
                insertAtCursor(nowStr, '7');
                break;
            case R.id.eightBtn:
                insertAtCursor(nowStr, '8');
                break;
            case R.id.nineBtn:
                insertAtCursor(nowStr, '9');
                break;
            case R.id.zeroBtn:
                insertAtCursor(nowStr, '0');
                break;
            case R.id.plusBtn:
                insertAtCursor(nowStr, '+');
                break;
            case R.id.minusBtn:
                insertAtCursor(nowStr, '-');
                break;
            case R.id.mulBtn:
                insertAtCursor(nowStr, '*');
                break;
            case R.id.divBtn:
                insertAtCursor(nowStr, '/');
                break;
            case R.id.backBtn:
                if (nowStr.length() > 0)
                    nowStr = nowStr.substring(0, nowStr.length() - 1);
                break;
            case R.id.dotBtn:
                insertAtCursor(nowStr, '.');
                break;
            case R.id.eqBtn:
                cursor = 0;
                try {
                    nowStr = "" + calculator.calculate(nowStr);
                } catch (CalculationException e) {
                    nowStr = "Ошибка вычисления";
                }
                break;
            case R.id.lBracketBtn:
                insertAtCursor(nowStr, '(');
                break;
            case R.id.rBracketBtn:
                insertAtCursor(nowStr, ')');
                break;
            case R.id.prevBtn:
                cursor++;
                cursor = Math.min(nowStr.length(), cursor);
                break;
            case R.id.nextBtn:
                cursor--;
                cursor = Math.max(0, cursor);
                break;
            case R.id.endBtn:
                cursor = 0;
                break;
        }
        editView.setText(nowStr.substring(0, nowStr.length() - cursor) + "_" + nowStr.substring(nowStr.length() - cursor));
    }
    private void insertAtCursor(String str, char c) {
        nowStr = str.substring(0, str.length() - cursor) + c + str.substring(str.length() - cursor);
    }
}
