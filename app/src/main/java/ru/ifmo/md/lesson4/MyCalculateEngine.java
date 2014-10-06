package ru.ifmo.md.lesson4;

import android.util.Log;

public class MyCalculateEngine implements CalculationEngine {
    // A = B {'+'/'-' B}
    // B = C {'*'/'/' C}
    // C = double | '(' A ')'

    private int current;
    private boolean isAppropriate(char c) {
        return Character.isDigit(c) || c == '∞' || c == '.';
    }
    private Expression getValue(String s) {
        Log.i("getval", "\"" + s + "\"");
        int i = current;
        for(;current < s.length() && isAppropriate(s.charAt(current)); current++);
        if(s.substring(i, current).equals("∞")) {
            return new Const(Double.POSITIVE_INFINITY);
        }
        double a = Double.parseDouble(s.substring(i, current));
        return new Const(a);
    }

    private Expression formula(String s) throws CalculationException {
        Log.i("formula", "\"" + s + "\"");
        Expression left = null;
        int initialPosition = current;
        if(current >= s.length())
            throw new CalculationException();
        while(current < s.length() && s.charAt(current) != ')') {
            if(s.charAt(current) == '+' || s.charAt(current) == '-') {
                if(current == initialPosition) {
                    left = new Const(0);
                }
                left = addSub(left, s);
            } else if(s.charAt(current) == '*' || s.charAt(current) == '/') {
                if (current == initialPosition) {
                    throw new CalculationException();
                }
                left = mulDiv(left, s);
            } else
                left = factor(s);
        }
        if(left == null) {
            throw new CalculationException();
        }
        return left;
    }
    private Expression factor(String s) throws CalculationException {
        Log.i("Factor", "\"" + s + "\"");
        if(current >= s.length()) {
            throw new CalculationException();
        }
        char ch = s.charAt(current);
        if(ch == '-') {
            current++;
            return new UnaryMinus(factor(s));
        } else if(isAppropriate(ch)) {
            return getValue(s);
        }
        if(ch == '(') {
            current++;
            Expression ans = formula(s);
            if(current < s.length() && s.charAt(current++) == ')') {
                return ans;
            }
            throw new CalculationException();
        }
        throw new CalculationException();
    }

    private Expression mulDiv(Expression left, String s) throws CalculationException {
        Log.i("muldiv", "\"" + s + "\"");
        if(current >= s.length())
            return left;
        char ch = s.charAt(current);
        Expression right;
        if(ch != '*' && ch != '/')
            return left;
        current++;
        right = factor(s);
        if(ch == '*')
            return mulDiv(new Multiply(left, right), s);
        else
            return mulDiv(new Divide(left, right), s);
    }
    private Expression addSub(Expression left, String s) throws CalculationException {
        Log.i("addsub", "\"" + s + "\"");
        if(current >= s.length()) {
            return left;
        }
        char ch = s.charAt(current);
        Expression right;
        if(ch != '+' && ch != '-') {
            return left;
        }
        current++;
        right = mulDiv(factor(s), s);
        if(ch == '+') {
            return addSub(new Add(left, right), s);
        } else {
            return addSub(new Subtract(left, right), s);
        }
    }
    @Override
    public double calculate(String expression) throws CalculationException{
        current = 0;
        double ans = formula(expression).evaluate();
        if(current != expression.length()) {
            throw new CalculationException();
        }
        return ans;
    }
}
