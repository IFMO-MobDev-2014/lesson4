package ru.ifmo.md.lesson4.expressionParser;

public class Divide extends BinaryExpression {
    public Divide(Expression3 left, Expression3 right) {
        super(left, right);
    }

    protected double mEvaluate(double left, double right) {
        return left / right;
    }
}
