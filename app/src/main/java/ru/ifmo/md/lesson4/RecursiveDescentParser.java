package ru.ifmo.md.lesson4;

import java.math.BigDecimal;

public class RecursiveDescentParser {
    private static int pointer;

    private static void passWhitespaces(String s) {
        while (pointer < s.length() &&
                (s.charAt(pointer) == ' ' || s.charAt(pointer) == '\t')) {
            pointer++;
        }
    }

    public static Expression parse(String s) throws Exception {
        pointer = 0;
        passWhitespaces(s);
        Expression result = addSub(s);
        if (pointer < s.length())
            throw new ParseException();
        return result;
    }

    private static Expression addSub(String s) throws Exception {
        Expression expr = mulDivMod(s), curr;

        while (pointer < s.length()) {
            if (Character.toString(s.charAt(pointer)).matches("[^\\-\\+]")) break;

            Character sign = s.charAt(pointer);
            pointer++;
            passWhitespaces(s);
            if (pointer >= s.length())
                throw new ParseException();

            curr = mulDivMod(s);
            if (sign.equals('+')) {
                if (expr == null)
                    expr = curr;
                else
                    expr = new Add(expr, curr);
            } else {
                if (expr == null)
                    expr = new Neg(curr);
                else
                    expr = new Subtract(expr, curr);
            }
        }

        return expr;
    }

    private static Expression mulDivMod(String s) throws Exception {
        Expression expr = bracket(s), curr;

        while (pointer < s.length()) {
            if (Character.toString(s.charAt(pointer)).matches("[^\\*/m]")) break;

            Character sign = s.charAt(pointer);
            if (sign.equals('m')) {
                if (pointer + 3 >= s.length() || !s.substring(pointer, pointer + 3).equals("mod"))
                    throw new ParseException();
                pointer += 3;
                passWhitespaces(s);
            } else {
                pointer++;
                passWhitespaces(s);
            }

            if (pointer >= s.length())
                throw new ParseException();

            curr = bracket(s);
            if (sign.equals('*')) {
                expr = new Multiply(expr, curr);
            } else if (sign.equals('/')) {
                expr = new Divide(expr, curr);
            }
        }

        return expr;
    }

    private static Expression bracket(String s) throws Exception {
        if (s.charAt(pointer) == '(') {
            pointer++;
            if (pointer >= s.length())
                throw new ParseException();
            passWhitespaces(s);
            Expression tmp = addSub(s);
            if (s.length() <= pointer || s.charAt(pointer) != ')')
                throw new ParseException();
            pointer++;
            passWhitespaces(s);
            return tmp;
        } else
            return unary(s);
    }

    private static Expression unary(String s) throws Exception {
        if (s.charAt(pointer) == '-') {
            pointer++;
            passWhitespaces(s);
/*            boolean nextConst = false;
            if (pointer < s.length() && Character.toString(s.charAt(pointer)).matches("[x-z\\d]"))
                nextConst = true;

            if (nextConst)
                return tmp;*/
            Expression tmp = unary(s);
            return new Neg(tmp);
        }

        if (pointer >= s.length())
            throw new ParseException();

        return num(s);
    }

    private static Expression num(String s) throws Exception {
        Expression tmp;
        if (s.charAt(pointer) == '(')
            return bracket(s);
        if (Character.toString(s.charAt(pointer)).matches("[\\d\\.]")) {
            boolean point = false;
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i + pointer < s.length() &&
                    Character.toString(s.charAt(pointer + i)).matches("[\\d\\.]"); i++) {
                if (s.charAt(pointer + i) == '.') {
                    if (point) {
                        throw new ParseException();
                    }
                    point = true;
                }
                temp.append(s.charAt(pointer + i));
            }
            try {
                tmp = new Const(new BigDecimal(temp.toString()));
            } catch (NumberFormatException e) {
                throw new ParseException();
            }
            pointer += temp.length();
            passWhitespaces(s);
            if (s.length() > pointer && Character.toString(s.charAt(pointer)).matches("[x-z]")) {
                throw new ParseException();
            }
        }
        else {
            throw new ParseException();
        }
        return tmp;
    }

}