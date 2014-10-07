package ru.ifmo.md.lesson4.expr;

/**
 * Created by mariashka on 10/5/14.
 */
public abstract class BinaryExpr implements Expression {
    protected final Expression arg1, arg2;

    protected BinaryExpr(Expression arg1, Expression arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
}
