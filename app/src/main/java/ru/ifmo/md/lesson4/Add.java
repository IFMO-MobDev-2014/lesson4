package ru.ifmo.md.lesson4;

class Add extends AbstractBinOp {
    @Override
    public double operation(double a, double b) {
        return a + b;
    }
    public Add(Expression a, Expression b) {
        super(a, b);
    }
}
