package ru.ifmo.md.lesson4.expression;

public final class UnaryMinus extends UnaryOperator {
    public UnaryMinus(Expression exp) {
        super(exp, "-");
    }

    @Override
    protected double operation(double a) {
        return -a;
    }
}
