package ru.ifmo.md.lesson4.expr;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by mariashka on 10/5/14.
 */
public class Const implements Expression{
    final private double value;

    public Const(double value) {
        this.value = value;
    }
    @Override
    public double eval() throws CalculationException {
        return this.value;
    }
}
