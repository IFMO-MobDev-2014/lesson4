package ru.ifmo.md.lesson4;

import android.content.CursorLoader;
import android.util.Log;

public class DummyCalculateEngine implements CalculationEngine {

    @Override
    public double calculate(String expression) throws CalculationException {
        Parse res = new Parse(expression);
        try {
            res.t.join();
        } catch(InterruptedException e) {}

        if (!res.exception.isEmpty())
            throw new CalculationException(res.exception);
        else
            return res.result;
    }
}
