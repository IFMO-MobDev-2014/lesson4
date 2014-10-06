package ru.ifmo.md.lesson4.Calculation;

public class Multiply extends AbstractOperation {

    public Multiply(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    protected double operation(double a, double b) {
        return a * b;
    }
}
