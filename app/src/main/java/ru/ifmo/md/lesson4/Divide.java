package ru.ifmo.md.lesson4;

class Divide extends AbstractBinOp {
    @Override
    public double operation(double a, double b) {
        return a / b;
    }
    public Divide(Expression a, Expression b) {
        super(a, b);
    }
}
