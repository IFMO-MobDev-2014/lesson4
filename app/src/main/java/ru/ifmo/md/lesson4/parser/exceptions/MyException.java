package ru.ifmo.md.lesson4.parser.exceptions;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 14.04.14
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class MyException extends CalculationException {
    String message;

    public MyException() {}
    public MyException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
