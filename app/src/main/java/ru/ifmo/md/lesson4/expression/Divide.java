package ru.ifmo.md.lesson4.expression;

/**
 * Created by andreikapolin on 06.10.14.
 */
public
class Divide extends BinaryOperation {
    public Divide(Expression3 first, Expression3 second) {
        super(first, second);
    }

    @Override
    public double evaluate() {
        return first.evaluate() / second.evaluate();
    }
}