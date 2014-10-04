package ru.ifmo.md.lesson4;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Divide extends BinaryOperator{
    public Divide(Expression a1, Expression a2) {
        super(a1, a2);
        priority = 2;
    }

    protected BigDecimal calculate(BigDecimal x, BigDecimal y) throws DivisionByZero, NumberFormatException{
//        if (x.equals(Integer.MIN_VALUE) && y.equals(-1))
//            throw new Overflow();

        BigDecimal ans;
        try {
            ans = x.divide(y, 16, BigDecimal.ROUND_DOWN);
        } catch (Exception e) {
            throw new DivisionByZero();
        }

        return ans;
    }

    protected String getSymbol() {
        return "/";
    }
}
