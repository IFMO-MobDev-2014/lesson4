package ru.ifmo.md.lesson4.expressionParser;

public class Mod extends BinaryExpression {
    public Mod(Expression3 left, Expression3 right) {
        super(left, right);
    }

    private double mod(double left, double right) {
        double result = left % right;
        if (result < 0) result += right;
        return result;
    } 

    private void check(double left, double right) {
        if (right < 0) throw new ArithmeticException("Division by module by negative number");
        if (right == 0) throw new OverflowException("Division by zero");
    }

    protected double mEvaluate(double left, double right) {
        check(left, right);
        return mod(left, right);
    }
}
