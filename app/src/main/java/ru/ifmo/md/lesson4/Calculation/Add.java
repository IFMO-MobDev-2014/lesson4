package ru.ifmo.md.lesson4.Calculation;

public class Add extends AbstractOperation{

    public Add(Expression a, Expression b) {
        super(a, b); 
    }

    @Override
    protected double operation(double a, double b) {
        return a + b;
    }
}
