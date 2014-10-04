package ru.ifmo.md.lesson4;

public class CalculationException extends Exception {
    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
