package ru.ifmo.md.lesson4.logic;


import ru.ifmo.md.lesson4.logic.ExceptionsHandle.DivisionByZeroException;
import ru.ifmo.md.lesson4.logic.ExceptionsHandle.EvaluationException;

/**
 * Created by Сергей on 15.03.14.
 */
public class Divide extends AbstractBinaryOperation implements Expression {

    @Override
    protected double implOperation(double first, double second) throws EvaluationException {
        if (second == 0)
            throw new DivisionByZeroException();
        return first / second;
    }

    @Override
    public String toString() {
        return "new Divide(" + firstArgument.toString() + ", " + secondArgument.toString() + ")";
    }

    public Divide(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }
}
