package ru.ifmo.md.lesson4.parser;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by german on 04.10.14.
 */
public class Const implements Expression {
    private final double value;

    Const(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() throws CalculationException {
        return value;
    }
}
