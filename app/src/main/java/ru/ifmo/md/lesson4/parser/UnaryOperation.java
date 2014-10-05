package ru.ifmo.md.lesson4.parser;

/**
 * Created by german on 04.10.14.
 */
public abstract class UnaryOperation implements Expression {
    Expression expression;

    UnaryOperation(Expression expression) {
        assert expression != null;
        this.expression = expression;
    }
}
