package ru.ifmo.md.lesson4.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.ifmo.md.lesson4.R;

/**
 * Appends string to input field when clicked.
 *
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class StringAppenderButton extends Button {
    private final String value;

    public StringAppenderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StringAppenderButton,
                0, 0);

        this.value = a.getString(R.styleable.StringAppenderButton_value);

        setText(value);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView inputField = (TextView)
                        ((Activity) getContext()).findViewById(R.id.inputField);

                inputField.append(value);
            }
        });
    }
}
