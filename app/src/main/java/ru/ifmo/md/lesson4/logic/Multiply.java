package ru.ifmo.md.lesson4.logic;

import ru.ifmo.md.lesson4.logic.ExceptionsHandle.EvaluationException;

/**
 * Created by Сергей on 15.03.14.
 */
public class Multiply extends AbstractBinaryOperation implements Expression {

    @Override
    protected double implOperation(double first, double second) throws EvaluationException
    {
        return first * second;
    }

    @Override
    public String toString() {
        return "new Multiply(" + firstArgument.toString() + ", " + secondArgument.toString() + ")";
    }

    public Multiply(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }
}
