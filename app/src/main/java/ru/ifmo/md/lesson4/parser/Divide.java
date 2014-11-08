package ru.ifmo.md.lesson4.parser;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by german on 04.10.14.
 */
public class Divide extends BinaryOperation {
    Divide(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    public double evaluate() throws CalculationException {
        double divisor = second.evaluate();
        if (Math.abs(divisor) <= 1E-6) {
            throw new CalculationException("Division by zero");
        }
        return first.evaluate() / second.evaluate();
    }
}
