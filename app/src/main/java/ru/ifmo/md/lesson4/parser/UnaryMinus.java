package ru.ifmo.md.lesson4.parser;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by german on 04.10.14.
 */
public class UnaryMinus extends UnaryOperation {
    UnaryMinus(Expression expression) {
        super(expression);
    }

    @Override
    public double evaluate() throws CalculationException {
        return -expression.evaluate();
    }
}
