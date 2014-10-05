package ru.ifmo.md.lesson4.MyCalcEngine;

import ru.ifmo.md.lesson4.CalculationException;

/**
 * @author sugak andrey
 */
public class BinaryOperation implements Expression {
    private Expression lhs;
    private Expression rhs;
    private Operator type;

    public BinaryOperation(Expression lhs, Expression rhs, Operator type) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.type = type;
    }

    @Override
    public String toString() {
        return "BinaryOperation{" +
                "lhs=" + lhs +
                ", rhs=" + rhs +
                ", type=" + type +
                '}';
    }

    @Override
    public double eval() throws CalculationException {
        return type.eval(lhs.eval(), rhs.eval()) ;
    }
}
