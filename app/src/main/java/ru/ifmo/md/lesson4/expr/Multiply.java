package ru.ifmo.md.lesson4.expr;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by mariashka on 10/5/14.
 */
public class Multiply extends BinaryExpr{
    public Multiply(Expression left, Expression right) {
       super(left, right);
    }

    @Override
    public double eval() throws CalculationException {
        return arg1.eval() * arg2.eval();
    }
}
