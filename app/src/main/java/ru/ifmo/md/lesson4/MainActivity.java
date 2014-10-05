package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Objects;

import ru.ifmo.md.lesson4.parser.ExpressionParser;

/**
 * Created by german on 05.10.14.
 */
public class MainActivity extends Activity {
    ArrayList<Button> buttons = new ArrayList<Button>();
    EditText editText;
    String s = "";

    @Override
    public Object onRetainNonConfigurationInstance() {
        return s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText) findViewById(R.id.editText);
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }

        Object savedObject = getLastNonConfigurationInstance();
        if (savedObject != null) {
            s = (String)savedObject;
            editText.setText("");
        } else {

        }


        TableLayout layout = (TableLayout)findViewById(R.id.table);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof TableRow) {
                for (int j = 0; j < ((TableRow)view).getChildCount(); j++) {
                    View viewInTableRow = ((TableRow)view).getChildAt(j);
                    if (viewInTableRow instanceof Button) {
                        Button button = ((Button)viewInTableRow);
                        if (button.getId() != R.id.clearButton && button.getId() != R.id.equalButton) {
                            buttons.add(button);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < buttons.size(); i++) {
            final Button button = buttons.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(button.getText() + "CLICKED");
                    int position = editText.getSelectionStart();
                    s = s.substring(0, position) + button.getText() + s.substring(position);
                    editText.setText(s);
                    editText.setSelection(position + 1);
                }
            });
        }

        final Button equalButton = (Button)findViewById(R.id.equalButton);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    s = "" + ExpressionParser.parse(s).evaluate();
                } catch (CalculationException e) {
                    s = e.getMessage();
                }
                editText.setText(s);
                editText.setSelection(s.length());
            }
        });

        final Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = "";
                editText.setText(s);
            }
        });

        final Button eraseButton = (Button)findViewById(R.id.eraseButton);
        eraseButton.setText("<-");
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = editText.getSelectionStart();
                if (position == 0) {
                    return;
                }
                s = s.substring(0, position - 1) + s.substring(position);
                editText.setText(s);
                editText.setSelection(position - 1);
            }
        });
    }
}
