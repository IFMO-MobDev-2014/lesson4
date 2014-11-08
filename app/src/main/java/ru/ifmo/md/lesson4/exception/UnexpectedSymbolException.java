package ru.ifmo.md.lesson4.exception;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class UnexpectedSymbolException extends LocatedCalculationException {
    public UnexpectedSymbolException(String expected, String actual, int location) {
        super(actual + " found, but " + expected + " expected", location);
    }
}
