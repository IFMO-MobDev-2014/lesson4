package ru.ifmo.md.lesson4.exception;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
class LocatedCalculationException extends CalculationException {
    LocatedCalculationException(String detailMessage, int location) {
        super(detailMessage + " at symbol " + (location + 1));
    }
}
