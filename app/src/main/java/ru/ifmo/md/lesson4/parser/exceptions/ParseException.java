package ru.ifmo.md.lesson4.parser.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: izban
 * Date: 14.04.14
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class ParseException extends MyException {
    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }
}
