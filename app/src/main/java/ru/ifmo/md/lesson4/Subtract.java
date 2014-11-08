package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public class Subtract extends BinaryOperator{
    public Subtract(Expression a1, Expression a2) {
        super(a1, a2);
        priority = 1;
    }

    protected BigDecimal calculate(BigDecimal x, BigDecimal y) {
        return x.subtract(y);
    }

    protected String getSymbol() {
        return "-";
    }
}
