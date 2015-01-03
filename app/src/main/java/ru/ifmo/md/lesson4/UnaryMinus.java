package ru.ifmo.md.lesson4;

/**
 * Created by timur on 03.01.15.
 */

class UnaryMinus implements Expression {
    private final Expression argument;

    public UnaryMinus(Expression argument) {
        this.argument = argument;
    }

    public double evaluate() {
        return -argument.evaluate();
    }
}
