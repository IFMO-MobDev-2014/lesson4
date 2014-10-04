package ru.ifmo.md.lesson4.logic.ExceptionsHandle;

/**
 * Created by Сергей on 15.04.14.
 */
public class EvaluationException extends ArithmeticException {
    public EvaluationException(String s) {
        super(s);
    }
}
