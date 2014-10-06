package ru.ifmo.md.lesson4.expression;

/**
 * Created by andreikapolin on 06.10.14.
 */
public abstract class UnaryOperation implements Expression3 {
    protected Expression3 expression;

    protected UnaryOperation(Expression3 expression) {
        this.expression = expression;
    }

}
