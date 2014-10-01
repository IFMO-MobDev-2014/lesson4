package ru.ifmo.md.lesson4.Expression.Exceptions;

import ru.ifmo.md.lesson4.CalculationException;

public class OverflowException extends CalculationException {
    public OverflowException() {
        super("overflow");
    }
}
