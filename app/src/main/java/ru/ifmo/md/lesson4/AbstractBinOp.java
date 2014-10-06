package ru.ifmo.md.lesson4;

public abstract  class AbstractBinOp implements Expression {
    private final Expression op1;
    private final Expression op2;
    abstract double operation(double a, double b) throws ArithmeticException;
    public double evaluate() throws ArithmeticException {
        return operation(op1.evaluate(), op2.evaluate());
    }
    AbstractBinOp(Expression first, Expression second) {
        op1 = first;
        op2 = second;
    }
}
