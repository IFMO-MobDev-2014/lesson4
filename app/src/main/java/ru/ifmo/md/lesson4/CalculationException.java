package ru.ifmo.md.lesson4;

public class CalculationException extends Exception {

    String message;

    public CalculationException() {
        super();
    }

    public CalculationException(String message) {
        super(message);
        this.message = message;
    }

    String getMess() {
        return message;
    }
}
