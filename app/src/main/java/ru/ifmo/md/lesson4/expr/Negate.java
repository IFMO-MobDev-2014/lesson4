package ru.ifmo.md.lesson4.expr;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by mariashka on 10/5/14.
 */
public class Negate extends UnaryExpr {
     public Negate(Expression arg) {
        super(arg);
    }

    @Override
    public double eval() throws CalculationException {
        return -(arg.eval());
    }
}
