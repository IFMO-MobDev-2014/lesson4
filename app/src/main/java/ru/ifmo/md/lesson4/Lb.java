package ru.ifmo.md.lesson4;

public class Lb extends UnaryOperation {
    public Lb(Expression3 e) {
        super(e);
    }

    public double operation(double x) throws ArithmeticException {
        if (x <= 0) {
            throw new InvalidLbArgumentException("lb(" + x + ")");
        }
        return Math.log(x) / Math.log(2);
    }
}
