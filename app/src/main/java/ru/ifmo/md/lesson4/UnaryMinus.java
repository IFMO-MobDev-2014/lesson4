package ru.ifmo.md.lesson4;

class UnaryMinus implements Expression {
    private final Expression op1;
    public double evaluate() {
        return -op1.evaluate();
    }
    public UnaryMinus(Expression op) {
        this.op1 = op;
    }
}
