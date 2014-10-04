package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public abstract class BinaryOperator implements Expression {
    protected Expression arg1, arg2;
    protected int priority;

    public BinaryOperator(Expression x, Expression y) {
        arg1 = x;
        arg2 = y;
    }

    public BigDecimal evaluate() throws Exception{
        BigDecimal ret1 = arg1.evaluate();
        BigDecimal ret2 = arg2.evaluate();

        return calculate(ret1, ret2);
    }

    public int getPriority() {
        return priority;
    }

    public String toString() {
        String result = "";
        if (arg1.getPriority() < priority)
            result += "(" + arg1.toString() + ")";
        else
            result += arg1.toString() + " ";
        result += getSymbol() + " ";
        if (arg2.getPriority() <= priority)
            result += "(" + arg2.toString() + ")";
        else
            result += arg2.toString();

        return result;
    }

    protected abstract BigDecimal calculate(BigDecimal x, BigDecimal y)
            throws DivisionByZero, NumberFormatException;
    protected abstract String getSymbol();
}
