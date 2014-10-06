package ru.ifmo.md.lesson4.exception;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class UnexpectedEndException extends LocatedCalculationException {
    public UnexpectedEndException(int location) {
        super("Unexpected end", location);
    }
}
