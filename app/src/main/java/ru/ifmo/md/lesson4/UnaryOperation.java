package ru.ifmo.md.lesson4;

public abstract class UnaryOperation implements Expression3 {
    protected Expression3 arg;

    public UnaryOperation(Expression3 arg) {
        this.arg = arg;
    }

    public abstract double operation(double x) throws ArithmeticException;

    public double evaluate(double x, double y, double z) throws ArithmeticException {
        return operation(arg.evaluate(x, y, z));
    }
}
