package ru.ifmo.md.lesson4.expressionParser;

public class UnaryPlus implements Expression3 {
    private Expression3 my;
    public UnaryPlus(Expression3 my) {
        this.my = my;
    }

    public double evaluate() {
        return my.evaluate();
    }
}