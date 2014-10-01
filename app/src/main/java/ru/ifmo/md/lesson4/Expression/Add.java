package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.Expression.Exceptions.CalculationException;

public class Add extends BinaryExpression3 {
    @Override
    public int getPriority() {
        return 3;
    }

    public Add(Expression3 firstArg, Expression3 lastArg) {
        super(firstArg, lastArg, '+');
    }

    @Override
    protected double operation(double x, double y) throws CalculationException {
        return x + y;
    }
}
