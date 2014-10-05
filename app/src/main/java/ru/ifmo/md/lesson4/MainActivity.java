package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by gshark on 05.10.14.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonC;
    Button buttonDel;
    Button buttonAdd;
    Button buttonDiv;
    Button buttonMul;
    Button buttonSub;
    Button buttonEq;
    Button buttonDot;
    Button buttonBeg;
    Button buttonEnd;

    EditText text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonMul = (Button) findViewById(R.id.buttonMul);
        buttonSub = (Button) findViewById(R.id.buttonSub);
        buttonDiv = (Button) findViewById(R.id.buttonDiv);
        buttonDel = (Button) findViewById(R.id.buttonDel);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonBeg = (Button) findViewById(R.id.buttonBeg);
        buttonEnd = (Button) findViewById(R.id.buttonEnd);
        buttonEq = (Button) findViewById(R.id.buttonEq);
        buttonDot = (Button) findViewById(R.id.buttonDot);

        text = (EditText) findViewById(R.id.text);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonBeg.setOnClickListener(this);
        buttonEnd.setOnClickListener(this);
        buttonEq.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
    }

    String curText = "";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                curText += "0";
                break;
            case R.id.button1:
                curText += "1";
                break;
            case R.id.button2:
                curText += "2";
                break;
            case R.id.button3:
                curText += "3";
                break;
            case R.id.button4:
                curText += "4";
                break;
            case R.id.button5:
                curText += "5";
                break;
            case R.id.button6:
                curText += "6";
                break;
            case R.id.button7:
                curText += "7";
                break;
            case R.id.button8:
                curText += "8";
                break;
            case R.id.button9:
                curText += "9";
                break;
            case R.id.buttonAdd:
                curText += "+";
                break;
            case R.id.buttonSub:
                curText += "-";
                break;
            case R.id.buttonDiv:
                curText += "/";
                break;
            case R.id.buttonMul:
                curText += "*";
                break;
            case R.id.buttonBeg:
                curText += "(";
                break;
            case R.id.buttonEnd:
                curText += ")";
                break;
            case R.id.buttonDot:
                curText += ".";
                break;
            case R.id.buttonDel:
                if (curText.length() > 0) {
                    curText = curText.substring(0, curText.length() - 1);
                }
                break;
            case R.id.buttonC:
                curText = "";
                break;
            case R.id.buttonEq:
                curText = "Res";
                break;
        }
        text.setText(curText.substring(Math.max(0, curText.length() - 9), curText.length()));
    }
}
