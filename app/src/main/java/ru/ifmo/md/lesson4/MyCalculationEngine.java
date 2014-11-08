package ru.ifmo.md.lesson4;

/**
 * Created by Амир on 05.10.2014.
 */

interface Expression {
    double evaluate() throws CalculationException;
}

abstract class BinaryOperation implements Expression {
    protected Expression leftOperand;
    protected Expression rightOperand;
    public BinaryOperation(Expression left, Expression right) {
        leftOperand = left;
        rightOperand = right;
    }
    public abstract double evaluate() throws CalculationException;
}

class Add extends BinaryOperation {
    public Add(Expression left, Expression right) {
        super(left, right);
    }
    public double evaluate() throws CalculationException  {
        double left = leftOperand.evaluate();
        double right = rightOperand.evaluate();
        return left + right;
    }
}

class Subtract extends BinaryOperation {
   public Subtract(Expression left, Expression right) {
        super(left, right);
    }
    public double evaluate() throws CalculationException {
        double left = leftOperand.evaluate();
        double right = rightOperand.evaluate();
        return left - right;
    }
}

class Multiply extends BinaryOperation {
    public Multiply(Expression left, Expression right) {
        super(left, right);
    }
    public double evaluate() throws CalculationException {
        double left = leftOperand.evaluate();
        double right = rightOperand.evaluate();
        return left * right;
    }
}

class Divide extends BinaryOperation {
    public Divide(Expression left, Expression right) {
        super(left, right);
    }
    public double evaluate() throws CalculationException {
        double left = leftOperand.evaluate();
        double right = rightOperand.evaluate();
        //if (right == 0) {
        //    throw new CalculationException();
        //}
        return left / right;
    }
}

abstract class UnaryOperation implements Expression {
    Expression value;
    public UnaryOperation(Expression v) {
        value = v;
    }
    public abstract double evaluate() throws CalculationException;
}

class UnaryMinus extends UnaryOperation {
    public UnaryMinus(Expression value) {
        super(value);
    }
    public double evaluate() throws CalculationException {
        double result = value.evaluate();
        return -result;
    }
}

class Const implements Expression {
    private final double result;
    public Const(double value) {
        result = value;
    }
    public double evaluate() {
        return result;
    }
}

class ExpressionParser {

    enum Lexem {
        EMPTY, CONSTANT, MINUS, PLUS, DIVIDE, MULTIPLY, OPEN, CLOSE, UNARYMINUS;
    }

    static private int curpos;
    static private int lastLexemPos;
    static private String expression;
    static private Lexem curLexem;
    static private String value;

    private static void nextLexem() throws CalculationException {
        lastLexemPos = curpos;
        while (curpos < expression.length()
                && Character.isWhitespace(expression.charAt(curpos))) {
            curpos++;
        }
        if (curpos == expression.length()) {
            curLexem = Lexem.EMPTY;
            return;
        }
        if (Character.isDigit(expression.charAt(curpos)) || expression.charAt(curpos) == '.') {
            int j = curpos;
            while (curpos < expression.length()
                    && (Character.isDigit(expression.charAt(curpos)) || expression.charAt(curpos) == '.')) {
                curpos++;
            }
            curLexem = Lexem.CONSTANT;
            value = expression.substring(j, curpos);
            return;
        } else {
            switch (expression.charAt(curpos)) {
                case '+':
                    curLexem = Lexem.PLUS;
                    break;
                case '-':
                    curLexem = Lexem.MINUS;
                    break;
                case '*':
                    curLexem = Lexem.MULTIPLY;
                    break;
                case '/':
                    curLexem = Lexem.DIVIDE;
                    break;
                case '(':
                    curLexem = Lexem.OPEN;
                    break;
                case ')':
                    curLexem = Lexem.CLOSE;
                    break;
            }
            curpos++;
            return;
        }
    }

    public static Expression parse(String exp) throws CalculationException {
        expression = exp;
        curpos = 0;
        lastLexemPos = 0;
        nextLexem();
        Expression result = count();
        if (curLexem != Lexem.EMPTY) {
            throw new CalculationException();
        }
        return result;
    }

    private static Expression count() throws CalculationException {
        Expression x = getSummand();
        while (curLexem == Lexem.PLUS || curLexem == Lexem.MINUS) {
            if (curLexem == Lexem.PLUS) {
                nextLexem();
                x = new Add(x, getSummand());
            }
            if (curLexem == Lexem.MINUS) {
                nextLexem();
                x = new Subtract(x, getSummand());
            }
        }
        return x;
    }

    private static Expression getSummand() throws CalculationException {
        Expression x = getMultiplier();
        while (curLexem == Lexem.MULTIPLY || curLexem == Lexem.DIVIDE) {
            if (curLexem == Lexem.MULTIPLY) {
                nextLexem();
                x = new Multiply(x, getMultiplier());
            }
            if (curLexem == Lexem.DIVIDE) {
                nextLexem();
                x = new Divide(x, getMultiplier());
            }
        }
        return x;
    }



    private static Expression getMultiplier() throws CalculationException {
        Lexem s = curLexem;
        int pos = lastLexemPos;
        nextLexem();
        if (s == Lexem.OPEN) {
            Expression x = count();
            if (curLexem != Lexem.CLOSE) {
                throw new CalculationException();
            }
            nextLexem();
            return x;
        }
        if (s == Lexem.MINUS) {
            return new UnaryMinus(getMultiplier());
        }

        if (s == Lexem.CONSTANT) {
            Const c = new Const(0);
            try {
                c = new Const(Double.parseDouble(value));
            } catch (Exception e) {
                throw new CalculationException();
            }
            return c;
        }
        throw new CalculationException();
    }

}

public class MyCalculationEngine implements CalculationEngine {

    @Override
    public double calculate(String expression) throws CalculationException {
        return (new ExpressionParser()).parse(expression).evaluate();
    }

}
