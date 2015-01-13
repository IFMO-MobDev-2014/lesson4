package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.CalculationException;
import ru.ifmo.md.lesson4.Expression.Exceptions.DivisionByZeroException;

public class Divide extends BinaryExpression3 {
    @Override
    public int getPriority() {
        return 2;
    }

    public Divide(Expression3 firstArg, Expression3 lastArg) {
        super(firstArg, lastArg, '/');
    }

    @Override
    protected double operation(double a, double b) throws CalculationException {
        if(b == 0) {
            throw new DivisionByZeroException();
        }
        return a / b;
    }
}

