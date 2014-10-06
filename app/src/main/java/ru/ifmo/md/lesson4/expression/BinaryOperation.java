package ru.ifmo.md.lesson4.expression;

/**
 * Created by andreikapolin on 06.10.14.
 */
public abstract class BinaryOperation implements Expression3 {
    protected Expression3 first, second;

    protected BinaryOperation(Expression3 first, Expression3 second) {
        this.first = first;
        this.second = second;
    }

}