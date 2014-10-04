package ru.ifmo.md.lesson4.logic.ExceptionsHandle;

/**
 * Created by Сергей on 15.04.14.
 */
public class DivisionByZeroException extends EvaluationException {
    public DivisionByZeroException() {
        super("Division by zero occurred");
    }

    public DivisionByZeroException(String s) {
        super(s);
    }
}
