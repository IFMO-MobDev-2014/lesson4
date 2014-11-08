package ru.ifmo.md.lesson4.parser.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 15.04.14
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public class DivisionByZeroException extends EvaluateException {
    public DivisionByZeroException() {
        super();
    }

    public DivisionByZeroException(String message) {
        super(message);
    }
}
