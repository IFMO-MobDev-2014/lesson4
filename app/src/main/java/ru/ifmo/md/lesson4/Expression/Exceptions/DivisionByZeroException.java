package ru.ifmo.md.lesson4.Expression.Exceptions;

import ru.ifmo.md.lesson4.CalculationException;

public class DivisionByZeroException extends CalculationException {
    public DivisionByZeroException() {
        super("division by zero");
    }
}
