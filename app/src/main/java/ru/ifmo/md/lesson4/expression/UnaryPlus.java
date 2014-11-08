package ru.ifmo.md.lesson4.expression;

public final class UnaryPlus extends UnaryOperator {
    public UnaryPlus(Expression exp) {
        super(exp, "+");
    }

    @Override
    protected double operation(double a) {
        return a;
    }
}
