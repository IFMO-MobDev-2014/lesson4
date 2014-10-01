package ru.ifmo.md.lesson4.Expression.Parser;

public class UndefinedFunctionException extends ParsingException {
    public UndefinedFunctionException(String fname) {
        super("Undefined function: " + fname);
    }
}
