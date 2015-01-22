package ru.ifmo.md.lesson4;

import ru.ifmo.md.lesson4.expressionParser.ExpressionParser;



public class MyCalculatorEngine implements CalculationEngine {
    @Override
    public double calculate(String expr) throws CalculationException {
        double result = 0;
        try {
            result = ExpressionParser.parse(expr).evaluate();
        }
        catch (Exception e) {
            throw new CalculationException(e.getMessage());
        }
        return result;
    }
}


/*
interface Expression {
    double evaluate();
}
public class UnaryMinus implements Expression3 {
    private Expression3 my;
    public UnaryMinus(Expression3 my) {
        this.my = my;
    }

    public double evaluate() {
        return -my.evaluate();
    }
}

abstract class UnaryOperator implements Expression {
    private Expression expr;
    public UnaryOperator(Expression expr) {
        this.expr = expr;
    }
    protected abstract double mEvaluate(double value);
    public double evaluate() {
        return mEvaluate(expr.evaluate());
    }
}

abstract class BinaryOperator implements Expression {
    private Expression left, right;
    public BinaryOperator(Expression left, Expression right) {
        this.left  = left;
        this.right = right;
    }
    public double evaluate() {
        return mEvaluate(left.evaluate(), right.evaluate());
    }
    protected abstract double mEvaluate(double left, double right);
}

class Const implements Expression {
    private double value;
    public Const(double value) {
        this.value = value;
    }
    public double evaluate() {
        return value;
    }
}

class UnaryPlus extends UnaryOperator {
    public UnaryPlus(Expression expr) {
        super(expr);
    }
    protected double mEvaluate(double value) {
        return value;
    }
}
class UnaryMinus extends UnaryOperator {
    public UnaryMinus(Expression expr) {
        super(expr);
    }
    protected double mEvaluate(double value) {
        return -value;
    }
}

class Add extends BinaryOperator {
    public Add(Expression expr1, Expression expr2) {
        super(expr1, expr2);
    }
    protected double mEvaluate(double left, double right) {
        return left + right;
    }
}

class Subtract extends BinaryOperator {
    public Subtract(Expression expr1, Expression expr2) {
        return super(expr1, expr2);
    }
    protected double mEvaluate(double left, double right) {
        return left - right;
    }
}

*/