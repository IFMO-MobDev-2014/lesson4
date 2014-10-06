package ru.ifmo.md.lesson4.ExpressionEvaluator.ExpressionParser;

import ru.ifmo.md.lesson4.CalculationException;
import ru.ifmo.md.lesson4.ExpressionEvaluator.BinaryExpressions.*;

/**
 * Created by Aydar on 31.03.14.
 */

public class ExpressionParser {
    private static Lexeme curLex;
    private static String s;
    private static int pos;
    private static double num;

    private ExpressionParser() {
    }

    private static Expression expr() throws CalculationException {
        Expression c = add();
        while (curLex == Lexeme.minus || curLex == Lexeme.plus) {
            Lexeme l = curLex;
            nextLexeme();
            if (l == Lexeme.plus) {
                c = new Add(c, add());
            } else {
                c = new Subtract(c, add());
            }
        }
        return c;
    }

    private static Expression add() throws CalculationException {
        Expression c = mul();
        while (curLex == Lexeme.mul || curLex == Lexeme.div) {
            Lexeme l = curLex;
            nextLexeme();
            if (l == Lexeme.mul) {
                c = new Multiply(c, mul());
            } else {
                c = new Divide(c, mul());
            }
        }
        return c;
    }

    private static Expression mul() throws CalculationException {
        if (curLex == Lexeme.minus) {
            nextLexeme();
            if (curLex == Lexeme.num) {
                nextLexeme();
                return new Const(-num);
            }
            return new UnaryMinus(mul());
        } else if (curLex == Lexeme.num) {
            double cur = num;
            nextLexeme();
            return new Const(cur);
        } else if (curLex == Lexeme.open) {
            nextLexeme();
            Expression cur = expr();
            if (curLex != Lexeme.close) {
                throw new CalculationException("Not enough brackets");
            }
            nextLexeme();
            return cur;
        } else {
            throw new CalculationException("Unexpected lexeme: " + curLex.name());
        }
    }

    private static void nextLexeme() throws CalculationException {
        for (; pos < s.length() && Character.isWhitespace(s.charAt(pos)); ++pos) {
        }
        if (pos >= s.length()) {
            return;
        }
        char c = s.charAt(pos);
        if (Character.isDigit(c) || c == '.') {
            num = findNum();
            curLex = Lexeme.num;
            --pos;
        } else if (c == '(') {
            curLex = Lexeme.open;
        } else if (c == '-') {
            curLex = Lexeme.minus;
        } else if (c == '+') {
            curLex = Lexeme.plus;
        } else if (c == '*') {
            curLex = Lexeme.mul;
        } else if (c == ')') {
            curLex = Lexeme.close;
        } else if (c == '/') {
            curLex = Lexeme.div;
        } else if (c == ':') {
            curLex = Lexeme.end;
        } else {
            throw new CalculationException("Unexpected symbol: " + c);
        }

        ++pos;
    }

    private static double findNum() throws CalculationException {
        int l = pos;
        for (; pos < s.length() && (Character.isDigit(s.charAt(pos))
                || s.charAt(pos) == '.' || s.charAt(pos) == 'E'); ++pos) {
        }
        String toNum = s.substring(l, pos);
        try {
            return Double.parseDouble(toNum);
        } catch (Exception e) {
            throw new CalculationException("Incorrect number: " + toNum);
        }
    }

    public static Expression parse(String t) throws CalculationException {
        s = t + ":";
        pos = 0;
        nextLexeme();
        Expression res = expr();
        if (curLex != Lexeme.end) {
            throw new CalculationException("Unexpected lexeme: " + curLex.name());
        }
        return res;
    }

    private static enum Lexeme {
        plus, minus, mul, num, open, close, div, end
    }
}

