package ru.ifmo.md.lesson4;

/**
 * Coś się kończy, coś się zaczyna
 * 31.03.2014
 */
public class Negate implements Expression {
    Expression a;

    public Negate(Expression a) throws CalculationException {
            if (a == null) {
                throw new CalculationException();
            }
            this.a = a;
    }

    public double evaluate() throws CalculationException  {
            return -a.evaluate();
    }
}
