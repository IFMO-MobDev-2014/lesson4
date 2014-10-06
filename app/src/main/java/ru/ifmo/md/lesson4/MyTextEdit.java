package ru.ifmo.md.lesson4;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyTextEdit extends EditText {
    public MyTextEdit(Context context) {
        super(context);
    }

    public MyTextEdit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyTextEdit(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        MainActivity.setCursor(selStart, selEnd);
    }
}