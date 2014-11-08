package ru.ifmo.md.lesson4.parser.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 14.04.14
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class OverflowException extends EvaluateException {
    public OverflowException() {
        super();
    }

    public OverflowException(String message) {
        super(message);
    }
}
