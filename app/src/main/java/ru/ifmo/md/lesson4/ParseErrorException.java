package ru.ifmo.md.lesson4;

/**
 * Created by DimaPhil on 06.10.2014.
 */
public class ParseErrorException extends SyntaxErrorException {
    public ParseErrorException(String message) {
        super(message);
    }
}
