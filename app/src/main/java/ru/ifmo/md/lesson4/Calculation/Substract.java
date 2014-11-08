package ru.ifmo.md.lesson4.Calculation;

public class Substract extends AbstractOperation {

    public Substract(Expression a, Expression b) {
        super(a, b);
    }

    @Override
    protected double operation(double a, double b) {
        return a - b;
    }
}
