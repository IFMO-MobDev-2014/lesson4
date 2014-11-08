package ru.ifmo.md.lesson4.expr;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by mariashka on 10/5/14.
 */
public interface Expression {
    public double eval() throws CalculationException;
}
