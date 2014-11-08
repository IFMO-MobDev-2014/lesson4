package ru.ifmo.md.lesson4.Parser;

import ru.ifmo.md.lesson4.CalculationEngine.CalculationException;

/**
 * Created by Женя on 03.10.2014.
 */
public abstract class BinaryOperation implements Expression {
    private Expression first;
    private Expression second;
    public BinaryOperation(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }
    public abstract double operation(double a, double b) throws CalculationException;
    public double evaluate() throws CalculationException {
        return operation(first.evaluate(), second.evaluate());
    }


}
