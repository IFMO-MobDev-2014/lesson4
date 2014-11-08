package ru.ifmo.md.lesson4.Parser;

import android.util.Log;

import ru.ifmo.md.lesson4.CalculationEngine.CalculationException;

/**
 * Created by Женя on 03.10.2014.
 */
public class Parser {
    private static String str;
    private static int point;

    private static Expression parseE() throws CalculationException {
        Expression e = parseF();
        while (point < str.length() && (str.charAt(point) == '+' || str.charAt(point) == '-')) {
            switch (str.charAt(point)) {
                case '+':
                    point++;
                    e = new Add(e, parseF());
                    break;
                case '-':
                    point++;
                    e = new Sub(e, parseF());
                    break;
            }
        }
        return e;
    }
    private static Expression parseF() throws CalculationException {
        Expression e = parseN();
        while (point < str.length() && (str.charAt(point) == '*' || str.charAt(point) == '/')) {
            switch (str.charAt(point)) {
                case '*':
                    point++;
                    e = new Mul(e, parseN());
                    break;
                case '/':
                    point++;
                    e = new Div(e, parseN());
                    break;
            }
        }
        return e;
    }
    private static Expression parseN() throws CalculationException {
        if (point < str.length() && str.charAt(point) == '-') {
            point++;
            return new Negate(parseC());
        }
        return parseC();
    }
    private static Expression parseC() throws CalculationException{
        if (point == str.length())
            throw new CalculationException();
        if (str.charAt(point) == '(') {
            point++;
            Expression e = parseE();
            if (str.charAt(point) != ')')
                throw new CalculationException();
            point++;
            return e;
        }
        if (!Character.isDigit(str.charAt(point)))
            throw new CalculationException();
        String num = "";
        while (point < str.length() && ((str.charAt(point) >= '0' && str.charAt(point) <= '9') || str.charAt(point) == '.'))
            num += str.charAt(point++);
        double cnst = 0;
        try {
            cnst = (Double.parseDouble(num));
        } catch (Exception e) {
            throw new CalculationException();
        }
        return new Const(cnst);
    }

    public static Expression parse(String expression) throws CalculationException{
        str = expression;
        point = 0;
        return parseE();
    }
}
