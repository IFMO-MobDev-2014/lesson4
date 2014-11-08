package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.CalculationException;

public abstract class UnaryExpression3 implements Expression3 {
    protected Expression3 exp;
    final protected char opSign;

    protected UnaryExpression3(Expression3 exp, char opSign) {
        this.exp = exp;
        this.opSign = opSign;
    }

    abstract protected double operation(double a) throws CalculationException;

    @Override
    public double evaluate(double x, double y, double z) throws CalculationException {
        return operation(exp.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return String.valueOf(opSign) + (getPriority() < exp.getPriority() ? "(" + exp.toString() + ")" : exp.toString());
    }

    @Override
    public boolean equals(Expression3 x) {
        return getClass().isInstance(x) && ((UnaryExpression3) x).exp.equals(exp);
    }

    @Override
    public int getPriority() {
        return -1;
    }
}
