package ru.ifmo.md.lesson4.exception;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
class LocatedCalculationException extends CalculationException {
    LocatedCalculationException(String detailMessage, int location) {
        super(detailMessage + " at symbol " + (location + 1));
    }
}
