package ru.ifmo.md.lesson4.exception;

public class CalculationException extends Exception {
    public CalculationException() {
    }

    public CalculationException(String detailMessage) {
        super(detailMessage);
    }
}
