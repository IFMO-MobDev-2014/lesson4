package ru.ifmo.md.lesson4.Expression;

import ru.ifmo.md.lesson4.Expression.Exceptions.CalculationException;

public abstract class BinaryExpression3 implements Expression3 {
    protected Expression3 firstArg, lastArg;
    final protected char opSign;

    abstract protected double operation(double a, double b) throws CalculationException;

    protected BinaryExpression3(Expression3 firstArg, Expression3 lastArg, char opSign) {
        this.firstArg = firstArg;
        this.lastArg = lastArg;
        this.opSign = opSign;
    }

    @Override
    public double evaluate(double x, double y, double z) throws CalculationException {
        return operation(firstArg.evaluate(x, y, z), lastArg.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return (firstArg.getPriority() <= getPriority() ? firstArg.toString() : "(" + firstArg.toString() + ") ") +
                " " + String.valueOf(opSign) + " " +
                (lastArg.getPriority() < getPriority() ? lastArg.toString() : "(" + lastArg.toString() + ")");
    }

    @Override
    public boolean equals(Expression3 x) {
        return getClass().isInstance(x) &&
                ((BinaryExpression3) x).firstArg.equals(firstArg) &&
                ((BinaryExpression3) x).lastArg.equals(lastArg);
    }

    @Override
    public int getPriority() {
        return -1;
    }
}
