package ru.ifmo.md.lesson4;

/**
 * Created by timur on 02.01.15.
 */

public abstract class AbstractBinaryOperation implements Expression {
    private final Expression first, second;

    AbstractBinaryOperation(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    abstract double operation(double a, double b) throws ArithmeticException;

    public double evaluate() throws ArithmeticException {
        return operation(first.evaluate(), second.evaluate());
    }
}
