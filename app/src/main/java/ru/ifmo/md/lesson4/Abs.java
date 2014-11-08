package ru.ifmo.md.lesson4;

public class Abs extends UnaryOperation {
    public Abs(Expression3 e) {
        super(e);
    }

    public double operation(double x) {
        return x < 0 ? -x : x;
    }
}