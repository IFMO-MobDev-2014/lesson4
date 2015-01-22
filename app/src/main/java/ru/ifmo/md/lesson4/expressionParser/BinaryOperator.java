package ru.ifmo.md.lesson4.expressionParser;

public abstract class BinaryOperator {
    public  BinaryOperator(char symb, int prior) {
        priority = prior;
        symbol = symb;
    }
    public final int  priority;
    public final char symbol;
    public abstract Expression3 calc(Expression3 expr1, Expression3 expr2);
}