package ru.ifmo.md.lesson4.Parser;

import ru.ifmo.md.lesson4.CalculationEngine.CalculationException;

/**
 * Created by Женя on 03.10.2014.
 */
public class Negate implements Expression {
    private Expression e;
    public Negate(Expression e) {
        this.e = e;
    }
    @Override
    public double evaluate() throws CalculationException {
        return -e.evaluate();
    }
}
