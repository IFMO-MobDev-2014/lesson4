package ru.ifmo.md.lesson4;

class Subtract extends AbstractBinOp {
    @Override
    public double operation(double a, double b) {
        return a - b;
    }
    public Subtract(Expression a, Expression b) {
        super(a, b);
    }
}
