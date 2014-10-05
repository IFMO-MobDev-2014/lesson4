package ru.ifmo.md.lesson4;

import android.util.Log;

/**
 * Created by Home on 02.10.2014.
 */

public class SuperCalculateEngine implements CalculationEngine {

    private class ParseResult {
        String s;
        double v;

        ParseResult(String s, double v)
        {
            this.s = s;
            this.v = v;
        }

    }

    private static String clearSpaces(String s) {
        if (s.equals(""))
            return s;
        int i = 0;
        while (i < s.length() && s.charAt(i) == ' ')
            i++;
        int j = s.length() - 1;
        while (j >= 0 && s.charAt(j) == ' ')
            j--;
        if (i > j)
            return "";
        return s.substring(i, j + 1);
    }

    private static String getToken(String s) {
        if (clearSpaces(s).isEmpty())
            return "";

        int i = 0;
        while (s.charAt(i) == ' ')
            i++;
        int i0 = i;

        if (s.charAt(i0) == '(' || s.charAt(i0) == ')' ||
                s.charAt(i0) == '+' || s.charAt(i0) == '-' ||
                s.charAt(i0) == '*' || s.charAt(i0) == '/' ) {

            return s.substring(0, i0 + 1);
        }

        for (; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i0)) || s.charAt(i0) == '.') {
                if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != '.' && s.charAt(i) != 'E')
                    break;
                else
                    if (s.charAt(i) == 'E' && s.charAt(i + 1) == '-')
                        i++;
            } else {
                if (!Character.isDigit(s.charAt(i)) && !Character.isLetter(s.charAt(i)))
                    break;
            }
        }

        return s.substring(0, i);
    }

    private ParseResult number(String expression) throws CalculationException {
        String s = getToken(expression);
        expression = expression.substring(s.length());
        try {
            return new ParseResult(expression, Double.parseDouble(s));
        } catch (NumberFormatException e) {
            throw new CalculationException("Incorrect number: ".concat(s));
        }
    }

    private ParseResult factor(String expression) throws CalculationException {
        String s = getToken(expression);
        if (clearSpaces(s).equals("(")) {
            expression = expression.substring(s.length());
            ParseResult p = expr(expression);
            s = getToken(p.s);
            if (!clearSpaces(s).equals(")"))
                throw new CalculationException("Missing closing bracket");
            return new ParseResult(p.s.substring(s.length()), p.v);
        } else if (clearSpaces(s).equals("-")) {
            expression = expression.substring(s.length());
            ParseResult p = factor(expression);
            return new ParseResult(p.s, -p.v);
        } else if (clearSpaces(s).equals("+")) {
            expression = expression.substring(s.length());
            return factor(expression);
        }
        return number(expression);
    }

    private ParseResult term(String expression) throws CalculationException {
        ParseResult p = factor(expression);
        String s = getToken(p.s);
        String rest = p.s;
        double acc = p.v;
        while (clearSpaces(s).equals("*") || clearSpaces(s).equals("/")) {
            rest = rest.substring(s.length());
            p = factor(rest);
            rest = p.s;
            if (clearSpaces(s).equals("*"))
                acc *= p.v;
            else
                acc /= p.v;
            s = getToken(rest);
        }
        return new ParseResult(rest, acc);
    }

    private ParseResult expr(String expression) throws CalculationException {
        ParseResult p = term(expression);
        String s = getToken(p.s);
        String rest = p.s;
        double acc = p.v;
        while (clearSpaces(s).equals("+") || clearSpaces(s).equals("-")) {
            rest = rest.substring(s.length());
            p = term(rest);
            rest = p.s;
            if (clearSpaces(s).equals("+"))
                acc += p.v;
            else
                acc -= p.v;
             s = getToken(rest);
        }
        return new ParseResult(rest, acc);
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        ParseResult p = expr(expression);
        if (clearSpaces(p.s).isEmpty())
            return p.v;
        else
            throw new CalculationException("Incorrect input");
    }

}
