package ru.ifmo.md.lesson4.parser.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 10.05.14
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
public class CalculateException extends EvaluateException {
    public CalculateException() {
        super();
    }

    public CalculateException(String message) {
        super(message);
    }
}
