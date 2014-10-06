package ru.ifmo.md.lesson4;

class Multiply extends AbstractBinOp {
    @Override
    public double operation(double a, double b) {
        return a * b;
    }
    public Multiply(Expression a, Expression b) {
        super(a, b);
    }
}
