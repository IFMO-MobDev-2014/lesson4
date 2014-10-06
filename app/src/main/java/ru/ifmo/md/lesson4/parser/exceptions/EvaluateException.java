package ru.ifmo.md.lesson4.parser.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 15.04.14
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class EvaluateException extends MyException {
    public EvaluateException() {
        super();
    }

    public EvaluateException(String message) {
        super(message);
    }
}
