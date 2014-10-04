package ru.ifmo.md.lesson4;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Snopi on 02.10.2014.
 */
public class MyButtonListener implements View.OnClickListener {
    private static TextView display;
    public MyButtonListener(TextView disp) {
        display = disp;
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        display.append(b.getText());
    }
}
