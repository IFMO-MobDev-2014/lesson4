package ru.ifmo.md.lesson4.logic.ExceptionsHandle;

/**
 * Created by Сергей on 15.04.14.
 */
public class ParserException extends Exception {

    public ParserException(String message, int position) {
        super(message + " at position " + position);
    }
}
