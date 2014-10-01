package ru.ifmo.md.lesson4.Expression.Exceptions;

public class IllegalEvalArgumentException extends CalculationException {
    public IllegalEvalArgumentException() {
        super("illegal function argument");
    }
}
