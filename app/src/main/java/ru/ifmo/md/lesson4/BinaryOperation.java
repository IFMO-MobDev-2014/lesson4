package ru.ifmo.md.lesson4;

public abstract class BinaryOperation implements Expression3 {
    protected Expression3 arg1, arg2;

    public BinaryOperation(Expression3 arg1, Expression3 arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public abstract double operation(double x, double y) throws ArithmeticException;

    public double evaluate(double x, double y, double z) throws ArithmeticException {
        return operation(arg1.evaluate(x, y, z), arg2.evaluate(x, y, z));
    }
}
