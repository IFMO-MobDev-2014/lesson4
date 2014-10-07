package ru.ifmo.md.lesson4.parser;

/**
 * Created by mariashka on 10/5/14.
 */
public class NumLex extends Lexem{
    public final Double value;

    public NumLex(Double num) {
        super(LexemType.CONST);

        value = num;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public LexemType getType() {
        return super.getType();
    }

    @Override
    public String toString() {
        return "Number";
    }
}
