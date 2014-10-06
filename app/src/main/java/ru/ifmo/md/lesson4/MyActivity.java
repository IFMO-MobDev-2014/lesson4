package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vitalik on 06.10.14.
 */
public class MyActivity extends Activity {

    EditText editText;
    public void setClick(Button button, final String str) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = editText.getSelectionStart();
                editText.setText(editText.getText().toString().substring(0, editText.getSelectionStart()) + str +
                        editText.getText().toString().substring(editText.getSelectionEnd()));
                editText.setSelection(a + 1);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText)findViewById(R.id.editText);
        editText.setCursorVisible(true);
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
        Button button[] = new Button[20];
        button[1] = (Button)findViewById(R.id.button1);
        button[2] = (Button)findViewById(R.id.button2);
        button[3] = (Button)findViewById(R.id.button3);
        button[4] = (Button)findViewById(R.id.button4);
        button[5] = (Button)findViewById(R.id.button5);
        button[6] = (Button)findViewById(R.id.button6);
        button[7] = (Button)findViewById(R.id.button7);
        button[8] = (Button)findViewById(R.id.button8);
        button[9] = (Button)findViewById(R.id.button9);
        button[0] = (Button)findViewById(R.id.button0);
        button[10] = (Button)findViewById(R.id.button_left_bracket);
        button[11] = (Button)findViewById(R.id.button_right_bracket);
        button[12] = (Button)findViewById(R.id.button_div);
        button[13] = (Button)findViewById(R.id.button_minus);
        button[14] = (Button)findViewById(R.id.button_mul);
        button[15] = (Button)findViewById(R.id.button_plus);
        button[16]  = (Button)findViewById(R.id.button_point);
        button[17]  = (Button)findViewById(R.id.button_clear);
        button[18]  = (Button)findViewById(R.id.button_del);
        button[19]  = (Button)findViewById(R.id.button_equal);


        for (int i = 0; i < 10; i++)
            setClick(button[i], "" + i);
        setClick(button[10], "(");
        setClick(button[11], ")");
        setClick(button[12], "/");
        setClick(button[13], "-");
        setClick(button[14], "*");
        setClick(button[15], "+");
        setClick(button[16], ".");
        button[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        button[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().length() == 0 || editText.getSelectionStart() == 0)
                    return;
                int a = editText.getSelectionStart();
                editText.setText(editText.getText().toString().substring(0, editText.getSelectionStart() - 1)  +
                        editText.getText().toString().substring(editText.getSelectionEnd()));
                editText.setSelection(a - 1);
            }
        });
        button[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    editText.setText("" +  CalculationEngineFactory.defaultEngine().calculate(editText.getText().toString()));
                } catch (CalculationException e) {
                    editText.setText(e.getMessage());
                }
                editText.setSelection(editText.getText().toString().length());
            }
        });
        editText.setText("0");
    }
}
