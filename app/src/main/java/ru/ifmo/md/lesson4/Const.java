package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public class Const implements Expression {
    private BigDecimal var;

    public Const(BigDecimal n) {
        var = n;
    }

    public BigDecimal evaluate() {
        return var;
    }

    public int getPriority() {
        return 4;
    }

    public String toString() {
        return var.toString();
    }
}
