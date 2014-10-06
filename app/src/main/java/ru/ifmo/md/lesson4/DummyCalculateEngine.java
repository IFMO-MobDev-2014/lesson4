package ru.ifmo.md.lesson4;

import android.util.Log;

public class DummyCalculateEngine implements CalculationEngine {
    private final char PLUS = '+';
    private final char SUB = '-';
    private final char OPEN = '(';
    private final char MUL = '*';
    private final char DIV = '/';
    private String lexeme;
    private int nextperm;
    private final double EPS = 0.00000000001;

    double number() throws CalculationException {
        String num = "";
        while ((lexeme.charAt(nextperm) >= '0' && lexeme.charAt(nextperm) <= '9') || (lexeme.charAt(nextperm) == '.')) {
            num += lexeme.charAt(nextperm);
            nextperm++;
        }
//        Log.d("Dummy", "num="+num);
        if (num.isEmpty()) {
            throw new CalculationException("Incorrect expression");
        }
        double res;
        try {
            res = Double.parseDouble(num);
        } catch (Exception e) {
            throw new CalculationException("Incorrect expression");
        }
        return res;
    }

    double multiplier() throws CalculationException {
        double ans;
        if (lexeme.charAt(nextperm) == OPEN) {
            nextperm++;
            ans = expr();
            if (lexeme.charAt(nextperm) != ')') {
                throw new CalculationException("Incorrect expression");
            }
            nextperm++;
        } else {
            if (lexeme.charAt(nextperm) == SUB) {
                nextperm++;
                ans = (-1) * multiplier();
            } else {
                ans = number();
            }
        }
        return ans;
    }

    double summand() throws CalculationException {
        double ans = multiplier();
        while ((lexeme.charAt(nextperm) == MUL) || (lexeme.charAt(nextperm) == DIV)) {
            nextperm++;
            if (lexeme.charAt(nextperm - 1) == MUL) {
                ans *= multiplier();
            } else {
                double tmp = multiplier();
                if (tmp < EPS)
                    throw new CalculationException("Division by zero");
                ans /= tmp;
            }
        }
        return ans;
    }

    double expr() throws CalculationException {
        double ans = summand();
//        Log.d("Dummy", "ans1="+ans);
        while ((lexeme.charAt(nextperm) == PLUS) || (lexeme.charAt(nextperm) == SUB)) {
            nextperm++;
            if (lexeme.charAt(nextperm - 1) == PLUS) {
                ans += summand();
//                Log.d("Dummy", "ans2="+ans);
            } else {
                ans -= summand();
            }
        }
        if (lexeme.charAt(nextperm) != '$') {
            throw new CalculationException("Incorrect expression");
        }
        return ans;
    }
    @Override
    public double calculate(String expression) throws CalculationException {
        nextperm = 0;
        lexeme = expression.replaceAll("\\s+", " ").concat("$");
        return expr();
    }
}
