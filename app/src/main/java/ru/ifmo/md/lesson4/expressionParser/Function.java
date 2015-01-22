package ru.ifmo.md.lesson4.expressionParser;

public abstract class Function {
    public  Function(char symbol) {
        this.symbol = symbol;
    }
    public final char symbol;
    public abstract Expression3 calc(Expression3 expr);
}