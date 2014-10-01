package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.CalculationException;

public class Multiply extends BinaryExpression3 {
    @Override
    public int getPriority() {
        return 2;
    }

    public Multiply(Expression3 firstArg, Expression3 lastArg) {
        super(firstArg, lastArg, '*');
    }

    @Override
    protected double operation(double a, double b) throws CalculationException {
        return a * b;
    }
}
