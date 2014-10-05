package ru.ifmo.md.lesson4.expression;

public final class Plus extends BinaryOperator {
    public Plus(Expression left, Expression right) {
        super(left, right, "+");
    }

    @Override
    protected double operation(double a, double b) {
        return a + b;
    }
}
