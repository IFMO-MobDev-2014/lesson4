package ru.ifmo.md.lesson4.Expression.Exceptions;

public class OverflowException extends CalculationException {
    public OverflowException() {
        super("overflow");
    }
}
