package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public class Multiply extends BinaryOperator {
    public Multiply(Expression a1, Expression a2) {
        super(a1, a2);
        priority = 2;
    }

    protected BigDecimal calculate(BigDecimal x, BigDecimal y){
        return  x.multiply(y);
    }

    public String getSymbol() {
        return "*";
    }
}
