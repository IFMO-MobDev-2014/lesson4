package ru.ifmo.md.lesson4.logic;

import ru.ifmo.md.lesson4.logic.ExceptionsHandle.EvaluationException;

/**
 * Created by Сергей on 18.03.14.
 */

public class UnaryMinus implements Expression {
    private Expression negative;

    @Override
    public double evaluate() throws EvaluationException {
        return -negative.evaluate();
    }

    @Override
    public String toString() {
        return "new UnaryMinus(" + negative.toString() + ")";
    }

    public UnaryMinus(Expression argument) {
        setExpression(argument);
    }

    public void setExpression(Expression argument) {
        negative = argument;
    }

}
