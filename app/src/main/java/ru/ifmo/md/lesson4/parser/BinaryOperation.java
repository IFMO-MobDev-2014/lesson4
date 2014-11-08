package ru.ifmo.md.lesson4.parser;

/**
 * Created by german on 04.10.14.
 */
public abstract class BinaryOperation implements Expression {
    Expression first, second;

    protected BinaryOperation(Expression first, Expression second) {
        assert first != null;
        assert second != null;
        this.first = first;
        this.second = second;
    }
}
