package ru.ifmo.md.lesson4.exception;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class LocatedCalculationException extends CalculationException {
    public LocatedCalculationException(String detailMessage, int location) {
        super(detailMessage + " at symbol " + (location + 1));
    }
}
