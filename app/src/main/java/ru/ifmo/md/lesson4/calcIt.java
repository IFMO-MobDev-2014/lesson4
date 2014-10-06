package ru.ifmo.md.lesson4;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad107 on 06.10.14
 */
public class calcIt {
    enum Lexeme {
        ADD,
        SUB,
        MUL,
        UNARY_SUB,
        DIV,
        OPEN_BRACKET,
        CLOSE_BRACKET,
        EXPR
    }

    public String Evaluate(String s) throws CalculationException {
        try {
            ExpressionParser epr = new ExpressionParser();
            Expression3 expr = epr.parse(s);
            return Double.toString(expr.evaluate());
        } catch (PossibleException err) {
            throw new CalculationException();
        }
    }

    public class ExpressionParser {
        private List<Item> a;

        public Expression3 parse(String s) throws PossibleException {
            return parseIt(s);
        }

        private ArrayList<Item> parseLexeme(String s) throws SyntaxException {
            char[] inp = s.toCharArray();
            ArrayList<Item> res = new ArrayList<Item>(0);

            for (int i = 0; i < inp.length; i++) {
                if (Character.isWhitespace(inp[i])) {
                    continue;
                }
                Item cur;
                String nextLexeme = "";
                if (Character.isDigit(inp[i])) {
                    while ((i < inp.length) && (Character.isDigit(inp[i]))) {
                        nextLexeme += inp[i];
                        i++;
                    }
                    if ((i < inp.length) && (inp[i] == '.')) {
                        nextLexeme += '.';
                        i++;
                    }
                    while ((i < inp.length) && (Character.isDigit(inp[i]))) {
                        nextLexeme += inp[i];
                        i++;
                    }
                    i--;
                } else {
                    nextLexeme = Character.toString(inp[i]);
                }
                if ((isOperator(nextLexeme)) || (nextLexeme.equals("(")) || (nextLexeme.equals(")"))) {
                    Item prev;
                    if (res.isEmpty()) {
                        prev = null;
                    } else {
                        prev = res.get(res.size() - 1);
                    }
                    cur = new Item(prev, nextLexeme);
                } else {
                    if (isDouble(nextLexeme)) {
                        Lexeme prev;
                        if (res.isEmpty()) {
                            prev = null;
                        } else {
                            prev = res.get(res.size() - 1).type;
                        }
                        double curVal = Double.parseDouble(nextLexeme);
                        if (prev == Lexeme.UNARY_SUB) {
                            res.remove(res.size() - 1);
                            curVal = -curVal;
                        }
                        cur = new ExpressionItem(new Const(curVal));
                    } else {
                        throw new SyntaxException("Unexpected lexeme");
                    }
                }
                res.add(cur);
            }
            return res;
        }

        private Expression3 parseIt (String s) throws PossibleException {
            a = parseLexeme(s);
            ArrayList<Lexeme> s0 = new ArrayList<Lexeme>();
            ArrayList<Item> s1 = new ArrayList<Item>();
            for (Item anA : a) {
                if (anA.type == Lexeme.EXPR) {
                    s1.add(anA);
                } else {
                    if (anA.type == Lexeme.OPEN_BRACKET) {
                        s0.add(Lexeme.OPEN_BRACKET);
                        continue;
                    }
                    if (anA.type == Lexeme.CLOSE_BRACKET) {
                        while ((!s0.isEmpty()) && (s0.get(s0.size() - 1) != Lexeme.OPEN_BRACKET)) {
                            Item cur = new Item();
                            cur.type = s0.get(s0.size() - 1);
                            s0.remove(s0.size() - 1);
                            s1.add(cur);
                        }
                        if (s0.isEmpty()) {
                            throw new SyntaxException("too many close_brackets");
                        }
                        s0.remove(s0.size() - 1);
                        continue;
                    }
                    while (!s0.isEmpty()) {
                        if (((leftA(s0.get(s0.size() - 1))) && (priority(s0.get(s0.size() - 1)) >= priority(anA.type))) ||
                                ((!leftA(s0.get(s0.size() - 1))) && (priority(s0.get(s0.size() - 1)) > priority(anA.type)))) {
                            Item cur = new Item();
                            cur.type = s0.get(s0.size() - 1);
                            s0.remove(s0.size() - 1);
                            s1.add(cur);
                        } else {
                            break;
                        }
                    }
                    s0.add(anA.type);
                }
            }
            while (!s0.isEmpty()) {
                Item cur = new Item();
                cur.type = s0.get(s0.size() - 1);
                s0.remove(s0.size() - 1);
                s1.add(cur);
            }
            ArrayList<Expression3> res = new ArrayList<Expression3>(0);
            for (Item aS1 : s1) {
                if (aS1.type == Lexeme.EXPR) {
                    res.add(((ExpressionItem) aS1).val);
                    continue;
                }
                if (isUnaryOperation(aS1.type)) {
                    if (res.isEmpty()) {
                        throw new SyntaxException("Invalid expression");
                    }
                    Expression3 cur = unaryExec(res.get(res.size() - 1), aS1.type);
                    res.remove(res.size() - 1);
                    res.add(cur);
                    continue;
                }
                if (res.size() < 2) {
                    throw new SyntaxException("Invalid expression");
                }
                Expression3 cur = binaryExec(res.get(res.size() - 2), res.get(res.size() - 1), aS1.type);
                res.remove(res.size() - 1);
                res.remove(res.size() - 1);
                res.add(cur);
            }
            if (res.size() != 1) {
                throw new SyntaxException("Not enough operators");
            }
            return res.get(0);
        }

        private Expression3 binaryExec(Expression3 x, Expression3 y, Lexeme c) throws SyntaxException {
            switch (c) {
                case ADD:
                    return new Add(x, y);
                case SUB:
                    return new Subtract(x, y);
                case MUL:
                    return new Multiply(x, y);
                case DIV:
                    return new Divide(x, y);
            }
            throw new SyntaxException("binaryExec");
        }

        private boolean isUnaryOperation(Lexeme c) throws SyntaxException {
            return c == Lexeme.UNARY_SUB;
        }

        private Expression3 unaryExec(Expression3 x, Lexeme c) throws SyntaxException {
            if (c == Lexeme.UNARY_SUB) {
                return new UnaryMinus(x);
            }
            throw new SyntaxException("unaryExec");
        }

        private int priority(Lexeme c) throws SyntaxException {
            if (c == Lexeme.OPEN_BRACKET) {
                return -1;
            }
            if (c == Lexeme.SUB || c == Lexeme.ADD) {
                return 0;
            }
            if (c == Lexeme.MUL || c == Lexeme.DIV) {
                return 1;
            }
            if (c == Lexeme.UNARY_SUB) {
                return 2;
            }
            throw new SyntaxException("priority");
        }

        private boolean leftA (Lexeme c) {
            return c != Lexeme.UNARY_SUB;
        }

        private boolean isDouble(String s) {
            try {
                Double temp = Double.parseDouble(s);
                return true;
            } catch (NumberFormatException ignored) {
            }
            return false;
        }

        private boolean isOperator(String o) {
            return  ((o.equals("+")) || (o.equals("-")) || (o.equals("*")) || (o.equals("/")));
        }

        private boolean isOperator(Item o) {
            return ((o.type == Lexeme.ADD) || (o.type == Lexeme.SUB) ||
                    (o.type == Lexeme.MUL) || (o.type == Lexeme.DIV) || (o.type == Lexeme.UNARY_SUB));
        }

        private class Item {
            public Lexeme type;
            public Item() {
                type = null;
            }
            public Item(Item prev, String c) throws SyntaxException {
                if (c.isEmpty()) {
                    throw new SyntaxException("Item constructor");
                }
                switch (c.charAt(0)) {
                    case '-':
                        if ((prev == null) || (prev.type == Lexeme.OPEN_BRACKET) || (isOperator(prev))) {
                            type = Lexeme.UNARY_SUB;
                        } else {
                            type = Lexeme.SUB;
                        }
                        break;
                    case '+':
                        type = Lexeme.ADD;
                        break;
                    case '*':
                        type = Lexeme.MUL;
                        break;
                    case '/':
                        type = Lexeme.DIV;
                        break;
                    case '(':
                        type = Lexeme.OPEN_BRACKET;
                        break;
                    case ')':
                        type = Lexeme.CLOSE_BRACKET;
                        break;
                    default:
                        throw new SyntaxException("Item constructor");
                }
            }
        }
        private class ExpressionItem extends Item {
            public Expression3 val;

            public ExpressionItem(Expression3 val) {
                this.val = val;
                this.type = Lexeme.EXPR;
            }
        }
    }
}

class EvaluateException extends PossibleException {
    public EvaluateException(String message) {
        super(message);
    }
}

class SyntaxException extends PossibleException {
    public SyntaxException (String message) {
        super(message);
    }
}


class PossibleException extends CalculationException {
    private String message;
    public PossibleException(String message) {
        super();
        this.message = message;
    }
    public void printMessage() {
        Log.e("PossibleException: ", message);
    }
}

class Add extends BinaryOperation {
    public Add(Expression3 a, Expression3 b) {
        super(a, b);
    }
    public double evaluate() throws EvaluateException {
        double first = u.evaluate();
        double second = v.evaluate();
        return first + second;
    }
}

class Divide extends BinaryOperation {
    public Divide(Expression3 a, Expression3 b) {
        super(a, b);
    }
    public double evaluate() throws EvaluateException{
        double first = u.evaluate();
        double second = v.evaluate();
        if (Math.abs(second) < 1e-20) {
            throw new EvaluateException("Divide by zero");
        }
        return first / second;
    }
}

class Subtract extends BinaryOperation {
    public Subtract(Expression3 a, Expression3 b) {
        super(a, b);
    }
    public double evaluate() throws EvaluateException {
        double first = u.evaluate();
        double second = v.evaluate();
        return first - second;
    }
}

class Multiply extends BinaryOperation {
    public Multiply(Expression3 a, Expression3 b) {
        super(a, b);
    }
    public double evaluate() throws EvaluateException {
        double first = u.evaluate();
        double second = v.evaluate();
        return first * second;
    }
}


class UnaryMinus extends UnaryOperation {
    public UnaryMinus(Expression3 a) {
        super(a);
    }
    public double evaluate() throws EvaluateException {
        double current = u.evaluate();
        return -current;
    }
}

abstract class BinaryOperation implements Expression3 {
    protected Expression3 u, v;
    public BinaryOperation(Expression3 a, Expression3 b) {
        u = a;
        v = b;
    }
    public abstract double evaluate() throws EvaluateException;
}


abstract class UnaryOperation implements Expression3 {
    protected Expression3 u;
    public UnaryOperation(Expression3 x) {
        u = x;
    }
    public abstract double evaluate() throws EvaluateException;
}

class Const implements Expression3 {
    private double value;
    public Const(double x) {
        value = x;
    }
    public double evaluate() {
        return value;
    }
}

interface Expression3 {
    public double evaluate() throws EvaluateException;
}

