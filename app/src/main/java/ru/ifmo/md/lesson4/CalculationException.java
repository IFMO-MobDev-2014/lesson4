package ru.ifmo.md.lesson4;

public class CalculationException extends Exception {
    public String s;

    CalculationException(String s) {
        super(s);
        this.s = s;
    }
}
