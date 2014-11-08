package ru.ifmo.md.lesson4;

/*
 * Coś się kończy, coś się zaczyna
 */
public abstract class Action implements Expression {
    private Expression a;
    private Expression b;

    public Action(Expression a, Expression b) {
        this.a = a;
        this.b = b;
    }

    public double evaluate() throws CalculationException {
        return complete(a.evaluate(), b.evaluate());
    }

    public abstract double complete(double a1, double b1) throws CalculationException;
}
