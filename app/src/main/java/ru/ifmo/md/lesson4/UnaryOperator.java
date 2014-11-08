package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public abstract class UnaryOperator implements Expression {
    Expression arg;

    public UnaryOperator(Expression x) {
        arg = x;
    }

    public BigDecimal evaluate() throws Exception{
        return calculate(arg.evaluate());
    }

    public int getPriority() {
        return 3;
    }

    public String toString() {
        return getSymbol() + arg.toString();
    }

    protected abstract String getSymbol();
    protected abstract BigDecimal calculate(BigDecimal x) throws NumberFormatException;
}
