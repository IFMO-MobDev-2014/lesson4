package ru.ifmo.md.lesson4.Expression.Exceptions;

import ru.ifmo.md.lesson4.CalculationException;

public class IllegalEvalArgumentException extends CalculationException {
    public IllegalEvalArgumentException() {
        super("illegal function argument");
    }
}
