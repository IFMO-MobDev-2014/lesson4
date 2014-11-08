package ru.ifmo.md.lesson4.expression;

import java.util.Map;

public abstract class UnaryOperator implements Expression {
    protected final Expression exp;
    protected final String operationName;

    public UnaryOperator(Expression exp, String operationName) {
        this.exp = exp;
        this.operationName = operationName;
    }

    @Override
    public final double calculate() {
        return operation(exp.calculate());
    }

    protected abstract double operation(double a);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnaryOperator that = (UnaryOperator) o;

        return exp.equals(that.exp) && operationName.equals(that.operationName);
    }
}