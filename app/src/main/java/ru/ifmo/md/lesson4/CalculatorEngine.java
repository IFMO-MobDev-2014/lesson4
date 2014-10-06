package ru.ifmo.md.lesson4;

import java.util.StringTokenizer;

public class CalculatorEngine implements CalculationEngine {

    static class Lexer {
        private StringTokenizer tokenizer;
        private String lexeme;
        private int curIndex = 0;
        private int length;

        public Lexer(String s, String split) {
            length = s.length();
            lexeme = "";

            tokenizer = new StringTokenizer(s, split, true);
        }

        public void next() {
            curIndex += lexeme.length();
            lexeme = "";
            while (tokenizer.hasMoreTokens()) {
                lexeme = tokenizer.nextToken();
                lexeme = lexeme.replaceAll("\\s+", "");
                if (!lexeme.isEmpty())
                    break;
                lexeme = "";
            }
        }

        public int getCurPosition() {
            return curIndex;
        }

        public int getLength() {
            return length;
        }

        public String current() {
            return lexeme;
        }
    }

    private static final double EPS = 1e-8;

    @Override
    public double calculate(String expression) throws CalculationException {
        Lexer lexer = new Lexer(expression, "+-*/ ()");
        lexer.next();
        double ret = expr(lexer);
        if (!lexer.current().isEmpty())
            throw new UnexpectedExpressionException("Unexpected expression", lexer.getCurPosition(), lexer.getLength());
        return ret;
    }

    private double expr(Lexer lexer) throws CalculationException {
        double res = item(lexer);
        while (lexer.current().equals("-") || lexer.current().equals("+")) {
            boolean isPlus = lexer.current().equals("+");
            lexer.next();
            if (isPlus)
                res += item(lexer);
            else
                res -= item(lexer);
        }
        return res;
    }

    private double item(Lexer lexer) throws CalculationException {
        double res = mul(lexer);
        while (lexer.current().equals("*") || lexer.current().equals("/")) {
            boolean isMul = lexer.current().equals("*");
            lexer.next();
            if (isMul)
                res *= mul(lexer);
            else {
                int st = lexer.getCurPosition();
                double b = mul(lexer);
                if (Math.abs(b) < EPS)
                    throw new DivisionByZeroException("Division by zero", st, lexer.getCurPosition());
                else
                    res /= b;
            }
        }
        return res;
    }

    private double mul(Lexer lexer) throws CalculationException {
        double res = 0;
        if (lexer.current().equals("-")) {
            lexer.next();
            if (isConst(lexer.current())) {
                res = -Double.parseDouble(lexer.current());
                lexer.next();
            } else
                res =  -expr(lexer);
        } else if (lexer.current().equals("+")) {
            lexer.next();
            if (isConst(lexer.current())) {
                res = Double.parseDouble(lexer.current());
                lexer.next();
            } else
                res =  expr(lexer);
        } else if (lexer.current().equals("(")) {
            int pos = lexer.getCurPosition();
            lexer.next();
            res = expr(lexer);
            if (lexer.current().equals(")"))
                lexer.next();
            else
                throw new UnpairedBracketException("Unpaired bracket", pos);
        } else if (isConst(lexer.current())) {
            res = Double.parseDouble(lexer.current());
            lexer.next();
        }
        else
            throw new UnexpectedExpressionException("Unexpected number ",
                    lexer.getCurPosition(), lexer.getCurPosition() + lexer.current().length());
        return res;
    }

    private boolean isConst(String num) {
        if (num.isEmpty())
            return false;
        try {
            double res = Double.parseDouble(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
