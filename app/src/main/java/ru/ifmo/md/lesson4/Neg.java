package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public class Neg extends UnaryOperator{
    public Neg(Expression a) {
        super(a);
    }

    protected BigDecimal calculate(BigDecimal x) {
        return x.negate();
    }

    protected String getSymbol() {
        return "-";
    }
}
