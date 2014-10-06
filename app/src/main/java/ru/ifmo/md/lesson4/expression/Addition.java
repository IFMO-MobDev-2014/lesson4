package ru.ifmo.md.lesson4.expression;

/**
 * Created by Nikita Yaschenko on 06.10.14.
 */
public class Addition extends BinaryOperation {
    public Addition(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    public double evaluate() {
        return first.evaluate() + second.evaluate();
    }
}
