package ru.ifmo.md.lesson4.logic.ExceptionsHandle;

/**
 * Created by Сергей on 15.04.14.
 */
public class OverflowException extends EvaluationException {
    public OverflowException() {
        super("overflow during evaluation");
    }
}
