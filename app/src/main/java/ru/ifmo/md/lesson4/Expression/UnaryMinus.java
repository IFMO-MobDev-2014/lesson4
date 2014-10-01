package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.CalculationException;

public class UnaryMinus extends UnaryExpression3 {

    @Override
    public int getPriority() {
        return 0;
    }

    public UnaryMinus(Expression3 exp) {
        super(exp, '-');
    }

    @Override
    protected double operation(double a) throws CalculationException {
        return -a;
    }
}
