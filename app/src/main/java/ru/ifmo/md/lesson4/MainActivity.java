package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
    CountDownTimer mTimer = null;

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
        }

        TableLayout layout = (TableLayout)findViewById(R.id.table);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof TableRow) {
                for (int j = 0; j < ((TableRow)view).getChildCount(); j++) {
                    View viewInTableRow = ((TableRow)view).getChildAt(j);
                    if (viewInTableRow instanceof Button) {
                        Button button = ((Button)viewInTableRow);
                        if (button.getId() != R.id.clearButton && button.getId() != R.id.equalButton
                                && button.getId() != R.id.eraseButton) {
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
                int positionStart = editText.getSelectionStart();
                int positionEnd = editText.getSelectionEnd();
                deleteSubstring(positionStart, positionEnd);
            }
        });

        final Rect rect = new Rect(0, 0, 0, 0);
        eraseButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                System.out.println("TRARAR");
                mTimer = new CountDownTimer(9999999, 200) {
                    @Override
                    public void onTick(long l) {
                        int positionStart = editText.getSelectionStart();
                        int positionEnd = editText.getSelectionEnd();
                        deleteSubstring(positionStart, positionEnd);
                    }
                    @Override
                    public void onFinish() {

                    }
                };
                mTimer.start();
                return true;
            }
        });

        eraseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    if (!rect.contains(view.getLeft() + (int)motionEvent.getX(),
                            view.getTop() + (int)motionEvent.getY())) {
                        if (mTimer != null) {
                            mTimer.cancel();
                        }
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (mTimer != null) {
                        mTimer.cancel();
                    }
                }
                return false;
            }
        });
    }

    private void deleteSymbol(int index) {
        s = s.substring(0, index) + s.substring(index + 1, s.length());
        editText.setText(s);
        editText.setSelection(index);
    }

    // delete substring [l; r)
    private void deleteSubstring(int l, int r) {
        if (r == 0) {
            return;
        }
        if (l == r) {
            l--;
        }
        s = s.substring(0, l) + s.substring(r, s.length());
        editText.setText(s);
        editText.setSelection(l);
    }
}
