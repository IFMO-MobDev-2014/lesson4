package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public class Add extends BinaryOperator {
    public Add(Expression a1, Expression a2) {
        super(a1, a2);
        priority = 1;
    }

    protected BigDecimal calculate(BigDecimal x, BigDecimal y) {
        return x.add(y);
    }

    protected String getSymbol() {
        return "+";
    }
}
