package ru.ifmo.md.lesson4.Calculation;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by Svet on 04.10.2014.
 */
public class MyCalculationEngine implements CalculationEngine{

    @Override
    public double calculate(String expression) throws CalculationException {
        Expression exp = parse(expression);
        return round(exp.evaluate());
    }

    private double round(double a) {
        return Math.floor(a * 100000.0) / 100000.0;
    }

    private static int cursor = 0;
    private static String s;

    public Expression parse(String string) throws CalculationException {
        cursor = 0;
        s = string;
        Expression res = plusMinus();
        if(cursor != s.length()) throw new CalculationException();
        return res;
    }

    private static void skip() {
        while (cursor < s.length() && Character.isWhitespace(s.charAt(cursor))) {
            ++cursor;
        }
    }

    private Expression plusMinus() throws CalculationException {
        skip();
        Expression cur = mulDiv();
        Expression temp = cur;

        while (cursor != s.length()) {
            char sign = s.charAt(cursor);
            if (!(sign == '+' || sign == '-')) {
                break;
            }

            ++cursor;
            cur = mulDiv();

            if (sign == '+') {
                temp = new Add(temp, cur);
            } else {
                temp = new Substract(temp, cur);
            }
        }
        return temp;
    }

    private Expression mulDiv() throws CalculationException {
        skip();
        Expression cur = brackets();
        Expression temp = cur;
        while (cursor != s.length()) {

            char sign = s.charAt(cursor);
            if (!(sign == '*' || sign == '/' || sign == 'm')) {
                return cur;
            }
            if (sign == 'm' && cursor + 3 < s.length() && s.substring(cursor, cursor + 3).equals("mod")) {
                cursor += 2;
            } else if(sign == 'm' && cursor + 3 >= s.length()){
                throw new CalculationException();
            }
            ++cursor;

            cur = brackets();
            if (sign == '*') {
                temp = new Multiply(temp, cur);
            } else if (sign == '/') {
                temp = new Divide(temp, cur);
            }
            cur = temp;
        }
        return temp;
    }

    private Expression brackets() throws CalculationException {
        skip();
        if (cursor >= s.length()) {
            throw new CalculationException();
        }
        char c = s.charAt(cursor);
        if (c == '(') {
            ++cursor;
            Expression res = plusMinus();
            if(cursor >= s.length()) throw new CalculationException();
            if (s.charAt(cursor) == ')') {
                cursor++;
            }
            skip();
            return res;
        }
        return function();
    }

    private Expression function() throws CalculationException {
        skip();
        if (cursor >= s.length()) {
            throw new CalculationException();
        }
        char determiner = s.charAt(cursor);
        if ((determiner == '-' || determiner == '~') && cursor + 1 == s.length()) {
            throw new CalculationException();
        }

        if (determiner == '-' && !Character.isDigit(s.charAt(cursor + 1))) {
            ++cursor;
            Expression r = brackets();
            return new Negative(r);
        }
        return number();
    }

    private Expression number() throws CalculationException {
        skip();
        int i = 0;
        char sign = s.charAt(cursor);

        if (sign == '-') {
            ++i;
        }
        String value;
        while (cursor + i < s.length() && (Character.isDigit(s.charAt(cursor + i)) || s.charAt(cursor + i) == '.' ||
                s.charAt(cursor + i) == 'E' || s.charAt(cursor + i) == '-')) {
            if(s.charAt(cursor + i) == '-' && s.charAt(cursor + i - 1) != 'E') break;
            ++i;
        }
        value = s.substring(cursor, cursor + i);
        if (value.isEmpty()) {
            throw new CalculationException();
        }
        cursor += i;
        skip();
        Const result = new Const(Double.parseDouble(value));
        return result;
    }
}
