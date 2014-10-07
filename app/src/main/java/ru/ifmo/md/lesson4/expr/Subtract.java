package ru.ifmo.md.lesson4.expr;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by mariashka on 10/5/14.
 */
public class Subtract extends BinaryExpr {
    public Subtract(Expression left, Expression right) {
       super(left, right);
    }

    @Override
    public double eval() throws CalculationException {
        return arg1.eval() - arg2.eval();
    }
}
