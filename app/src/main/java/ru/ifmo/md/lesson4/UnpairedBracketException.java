package ru.ifmo.md.lesson4;

/**
 * Created by pva701 on 05.10.14.
 */
public class UnpairedBracketException extends CalculationException {
    private int unpair;
    public UnpairedBracketException(String message, int pos) {
        super(message);
        unpair = pos;
    }

    public int getUnpairedIndex() {
        return unpair;
    }
}
