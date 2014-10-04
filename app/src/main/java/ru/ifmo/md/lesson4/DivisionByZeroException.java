package ru.ifmo.md.lesson4;

/**
 * Created by pva701 on 05.10.14.
 */
public class DivisionByZeroException extends CalculationException {
    private int begin;
    private int end;
    public DivisionByZeroException(String message, int l, int r) {
        super(message);
        begin = l;
        end = r;
    }

    public int getBeginOfError() {
        return begin;
    }

    public int getEndOfError() {
        return end;
    }
}
