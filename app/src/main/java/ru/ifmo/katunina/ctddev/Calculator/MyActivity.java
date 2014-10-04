package ru.ifmo.katunina.ctddev.Calculator;

import android.app.Activity;
import android.os.Bundle;

import ru.ifmo.md.lesson4.R;

/**
 * Created by Евгения on 04.10.2014.
 */
public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_layout);
      //  findViewsByIds();
    }
}
