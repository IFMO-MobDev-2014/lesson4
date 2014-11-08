package ru.ifmo.md.lesson4.expr;

/**
 * Created by mariashka on 10/5/14.
 */
public abstract class UnaryExpr implements  Expression{
    protected final Expression arg;

    protected UnaryExpr(Expression arg) {
        this.arg = arg;
    }
}
