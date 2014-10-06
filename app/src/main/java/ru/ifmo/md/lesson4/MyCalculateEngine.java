package ru.ifmo.md.lesson4;

import android.util.Log;

public class MyCalculateEngine implements CalculationEngine {
    // A = B {'+'/'-' B}
    // B = C {'*'/'/' C}
    // C = double | '(' A ')'
    private Expression parseFirst(String expression) throws CalculationException {
        int bracketIndex = 0;
        Log.i("exp", expression);
        for(int i = expression.length() - 1; i >= 0; i--) {
            char cur = expression.charAt(i);
            if(cur == '(') {
                bracketIndex++;
            } else if(cur == ')') {
                bracketIndex--;
            }
            if(bracketIndex == 0) {
                if(cur == '+') {
                    return new Add(parseFirst(expression.substring(0, i)), parseSecond(expression.substring(i + 1, expression.length())));
                } else if(cur == '-' && i != 0) {
                    return new Subtract(parseFirst(expression.substring(0, i)), parseSecond(expression.substring(i + 1, expression.length())));
                }
            }
        }
        return parseSecond(expression);
    }

    private Expression parseSecond(String expression) throws CalculationException {
        int bracketIndex = 0;
        Log.i("exp", expression);
        for(int i = expression.length() - 1; i >= 0; i--) {
            char cur = expression.charAt(i);
            if(cur == '(') {
                bracketIndex++;
            } else if(cur == ')') {
                bracketIndex--;
            }
            if(bracketIndex == 0) {
                if(cur == '*') {
                    return new Multiply(parseSecond(expression.substring(0, i)), parseThird(expression.substring(i + 1, expression.length())));
                } else if(cur == '/') {
                    return new Divide(parseSecond(expression.substring(0, i)), parseThird(expression.substring(i + 1, expression.length())));
                }
            }
        }
        return parseThird(expression);
    }

    private Expression parseThird(String expression) throws CalculationException {
        Log.i("expr", expression);
        if(expression.startsWith("(")) {
            if(!expression.endsWith(")")) {
                throw new CalculationException();
            }
            return parseFirst(expression.substring(1, expression.length() - 1));
        }
        if(expression.equals("∞")) {
            return new Const(Double.POSITIVE_INFINITY);
        } else if(expression.equals("-∞")) {
            return new Const(Double.NEGATIVE_INFINITY);
        }

        try {
            return new Const(Double.parseDouble(expression));
        } catch (Exception e) {
            throw new CalculationException();
        }
    }
    
    @Override
    public double calculate(String expression) throws CalculationException {
        return parseFirst(expression).evaluate();
    }
}
