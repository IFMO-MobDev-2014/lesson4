package ru.ifmo.md.lesson4.expression;

/**
 * Created by Nikita Yaschenko on 06.10.14.
 */
public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(Expression first) {
        super(first);
    }

    @Override
    public double evaluate() {
        return -first.evaluate();
    }
}
