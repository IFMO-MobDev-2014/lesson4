package ru.ifmo.md.lesson4.expression;

/**
 * Created by Nikita Yaschenko on 06.10.14.
 */
public abstract class BinaryOperation implements Expression {
    protected final Expression first;
    protected final Expression second;

    public BinaryOperation(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }
}
