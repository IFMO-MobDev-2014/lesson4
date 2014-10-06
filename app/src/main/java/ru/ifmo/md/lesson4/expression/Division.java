package ru.ifmo.md.lesson4.expression;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by Nikita Yaschenko on 06.10.14.
 */
public class Division extends BinaryOperation {
    public Division(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    public double evaluate() {
        return first.evaluate() / second.evaluate();
    }
}
