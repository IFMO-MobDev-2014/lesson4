package ru.ifmo.md.lesson4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ButtonsFragment extends Fragment {
    public static final String SAVED_INSTANCE_BUTTON_NAMES = "ru.ifmo.md.lesson4.ButtonsFragment.SAVED_INSTANCE_BUTTON_NAMES";
    private int buttonNames;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_BUTTON_NAMES, buttonNames);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_BUTTON_NAMES))
            this.buttonNames = savedInstanceState.getInt(SAVED_INSTANCE_BUTTON_NAMES);
    }

    public void setButtonNames(int buttonNames) {
        this.buttonNames = buttonNames;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TableLayout layout = (TableLayout) inflater.inflate(R.layout.calc_table, container, false);
        String[] buttonNames = getResources().getStringArray(this.buttonNames);
        int buttonsInRow = getResources().getInteger(R.integer.buttons_in_row);
        for (int i = 0; i < buttonNames.length; i += buttonsInRow) {
            TableRow row = (TableRow) inflater.inflate(R.layout.calc_row, layout, false);
            for (int j = i; j < i + buttonsInRow; j++) {
                Button button = (Button) inflater.inflate(R.layout.calc_button, row, false);
                button.setText(buttonNames[j]);
                row.addView(button);
            }
            layout.addView(row);
        }
        return layout;
    }
}
