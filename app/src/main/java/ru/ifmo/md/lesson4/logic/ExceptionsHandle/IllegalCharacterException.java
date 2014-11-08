package ru.ifmo.md.lesson4.logic.ExceptionsHandle;

/**
 * Created by Сергей on 15.04.14.
 */
public class IllegalCharacterException extends ParserException {
    public IllegalCharacterException(int position) {
        super("Illegal character", position);
    }
}
