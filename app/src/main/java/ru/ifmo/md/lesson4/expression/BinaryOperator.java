package ru.ifmo.md.lesson4.expression;

import java.util.Map;

public abstract class BinaryOperator implements Expression {
    //TODO: javadoc
    protected final Expression left, right;
    protected final String operationName;

    public BinaryOperator(Expression left, Expression right, String operationName) {
        this.left = left;
        this.right = right;
        this.operationName = operationName;
    }

    @Override
    public final double calculate() {
        return operation(left.calculate(), right.calculate());
    }

    protected abstract double operation(double a, double b);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BinaryOperator that = (BinaryOperator) o;

        return left.equals(that.left) && operationName.equals(that.operationName) &&
                right.equals(that.right);
    }
}