package ru.ifmo.md.lesson4.expression;

/**
 * Created by Nikita Yaschenko on 06.10.14.
 */
public abstract class UnaryOperation implements Expression {
    protected final Expression first;

    public UnaryOperation(Expression first) {
        this.first = first;
    }
}
