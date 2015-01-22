package ru.ifmo.md.lesson4.expressionParser;

public abstract class BinaryExpression implements Expression3 {
    protected Expression3 left, right;
    
    public BinaryExpression(Expression3 left, Expression3 right) {
        this.left = left;
        this.right = right;
    }

    protected abstract double mEvaluate(double left, double right);

    public double evaluate() {
        return mEvaluate(left.evaluate(), right.evaluate());
    }
}