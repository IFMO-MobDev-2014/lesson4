package ru.ifmo.md.lesson4.exception;

public class CalculationException extends Exception {
    CalculationException(String detailMessage) {
        super(detailMessage);
    }
}
