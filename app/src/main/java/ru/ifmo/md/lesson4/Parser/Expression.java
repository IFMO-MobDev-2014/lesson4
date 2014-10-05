package ru.ifmo.md.lesson4.Parser;

import ru.ifmo.md.lesson4.CalculationEngine.CalculationException;

/**
 * Created by Женя on 03.10.2014.
 */
public interface Expression {
    public double evaluate() throws CalculationException;
}
