package ru.ifmo.md.lesson4.expression;

/**
 * Created by Nikita Yaschenko on 06.10.14.
 */
public class Multiplication extends BinaryOperation {
    public Multiplication(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    public double evaluate() {
        return first.evaluate() * second.evaluate();
    }
}
