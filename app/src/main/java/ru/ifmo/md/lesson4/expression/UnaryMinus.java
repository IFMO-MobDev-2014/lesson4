package ru.ifmo.md.lesson4.expression;

/**
 * Created by andreikapolin on 06.10.14.
 */
public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(Expression3 expression) {
        super(expression);
    }

    @Override
    public double evaluate() {
        return -expression.evaluate();
    }
}