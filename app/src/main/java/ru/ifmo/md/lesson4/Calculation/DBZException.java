package ru.ifmo.md.lesson4.Calculation;

public class DBZException extends ArithmeticException {

    private String message = "division by zero";

    public DBZException() {
        super();
    }

    @Override
    public String toString() {
        return message;
    }
}
