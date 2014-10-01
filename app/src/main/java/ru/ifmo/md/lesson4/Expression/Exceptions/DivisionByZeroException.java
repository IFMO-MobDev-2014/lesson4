package ru.ifmo.md.lesson4.Expression.Exceptions;

public class DivisionByZeroException extends CalculationException {
    public DivisionByZeroException() {
        super("division by zero");
    }
}
