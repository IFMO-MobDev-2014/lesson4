package ru.ifmo.md.lesson4;

/**
 * Created by timur on 02.01.15.
 */

class Divide extends AbstractBinaryOperation {
    public Divide(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    public double operation(double a, double b) {
        return a / b;
    }
}
