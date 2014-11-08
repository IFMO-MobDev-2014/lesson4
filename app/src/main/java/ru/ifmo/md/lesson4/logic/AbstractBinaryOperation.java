package ru.ifmo.md.lesson4.logic;

import ru.ifmo.md.lesson4.logic.ExceptionsHandle.EvaluationException;

/**
 * Created by Сергей on 15.03.14.
 */

public abstract class AbstractBinaryOperation implements Expression {
    protected Expression firstArgument, secondArgument;

    public AbstractBinaryOperation(Expression firstArgument, Expression secondArgument) {
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
    }

    @Override
    public double evaluate() throws EvaluationException {
        return implOperation(firstArgument.evaluate(), secondArgument.evaluate());
    }

    abstract protected double implOperation(double first, double second) throws EvaluationException;
}