package ru.ifmo.md.lesson4.logic;

import ru.ifmo.md.lesson4.logic.ExceptionsHandle.DivisionByZeroException;
import ru.ifmo.md.lesson4.logic.ExceptionsHandle.EvaluationException;
import ru.ifmo.md.lesson4.logic.ExceptionsHandle.OverflowException;

/**
 * Created by Сергей on 15.04.14.
 */
public class Power implements Expression {
    private Expression argument;
    private Expression power;

    @Override
    public double evaluate() throws EvaluationException {
        double argumentResult = argument.evaluate();
        double powerResult = power.evaluate();
        if (argumentResult == 0 && powerResult < 0)
            throw new DivisionByZeroException("Illegal power for zero: " + powerResult);
        double result = Math.pow(argumentResult, powerResult);
        if (Double.isInfinite(result))
            throw new OverflowException();
        return result;
    }

    @Override
    public String toString() {
        return "new Power(" + argument.toString() + ", " + power.toString() + ")";
    }

    public Power(Expression argument, Expression power) {
        this.argument = argument;
        this.power = power;
    }

}
