package ru.ifmo.md.lesson4;

public class DivisionByZeroException extends ArithmeticException {
    public DivisionByZeroException(String s) {
        super(s);
    }
}
