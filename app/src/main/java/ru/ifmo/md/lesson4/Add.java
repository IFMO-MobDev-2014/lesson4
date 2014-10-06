package ru.ifmo.md.lesson4;

public class Add extends BinaryOperation {
    public Add(Expression3 arg1, Expression3 arg2) {
        super(arg1, arg2);
    }

    public double operation(double x, double y) {
        return x + y;
    }
}
