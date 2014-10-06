package ru.ifmo.md.lesson4.parser.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 15.04.14
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
public class LogException extends EvaluateException {
    public LogException() {
        super();
    }

    public LogException(String message) {
        super(message);
    }
}