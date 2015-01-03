package ru.ifmo.md.lesson4;

/**
 * Created by timur on 03.01.15.
 */

class Subtract extends AbstractBinaryOperation {
    public Subtract(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    public double operation(double a, double b) {
        return a - b;
    }
}
