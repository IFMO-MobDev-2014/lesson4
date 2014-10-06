package ru.ifmo.md.lesson4;

public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(Expression3 e) {
        super(e);
    }

    public double operation(double x) {
        return -x;
    }
}
