package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.CalculationException;

public class Subtract extends BinaryExpression3 {
    @Override
    public int getPriority() {
        return 3;
    }

    public Subtract(Expression3 firstArg, Expression3 lastArg) {
        super(firstArg, lastArg, '-');
    }

    @Override
    protected double operation(double x, double y) throws CalculationException {
        return x - y;
    }
}
