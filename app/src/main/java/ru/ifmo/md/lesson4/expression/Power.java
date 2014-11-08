package ru.ifmo.md.lesson4.expression;

public final class Power extends BinaryOperator {
    public Power(Expression left, Expression right) {
        super(left, right, "^");
    }

    @Override
    protected double operation(double a, double b) {
        return Math.pow(a, b);
    }
}
