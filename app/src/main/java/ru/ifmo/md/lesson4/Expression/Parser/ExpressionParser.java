package ru.ifmo.md.lesson4.Expression.Parser;

import ru.ifmo.md.lesson4.Expression.Add;
import ru.ifmo.md.lesson4.Expression.Const;
import ru.ifmo.md.lesson4.Expression.Divide;
import ru.ifmo.md.lesson4.Expression.Expression3;
import ru.ifmo.md.lesson4.Expression.Multiply;
import ru.ifmo.md.lesson4.Expression.Subtract;
import ru.ifmo.md.lesson4.Expression.UnaryMinus;
import ru.ifmo.md.lesson4.Expression.Variable;

public class ExpressionParser {
    // Grammar:
    // <Exp> : <Pr2> EOF
    // <Pr2> : <Pr1> [ ('+' | '-') <Pr1> ]*
    // <Pr1> : <Pr0> [ ('*' | '/') <Pr0> ]*
    // <Pr0> : <Unit> | '-' <Pr0> | '~' <Pr0>
    // <Unit>: <Const> | <Var> | '(' <Pr3> ')'
    // <Const>: (0-9)+
    // <Var> : 'x' | 'y' | 'z'

    private int curPos;
    private String input;

    private Character next() {
        if (curPos >= input.length()) {
            return null;
        }

        char c = input.charAt(curPos);
        while (curPos < input.length() && Character.isWhitespace(c)) {
            if (curPos + 1 < input.length()) {
                c = input.charAt(curPos + 1);
            }
            curPos++;
        }
        return curPos < input.length() ? c : null;
    }

    private Character move() {
        Character res = next();
        if (res != null) {
            curPos++;
        }
        return res;
    }

    private void expect(Character ch) throws ParsingException {
        if (next() != ch) {
            throw new ParsingException(input, curPos, String.valueOf(ch));
        }
        move();
    }

    private void expectNotEndBut(String msg) throws ParsingException {
        if (next() == null) {
            throw new ParsingException(input, curPos, msg);
        }
    }

    public Expression3 parse(String s) throws ParsingException {
        try {
            input = s;
            curPos = 0;
            Expression3 exp = parsePr2();
            expect(null);
            return exp;
        } catch (NullPointerException e) {
            throw new ParsingException(input, 0, "Input must be incorrect");
        }
    }

    private Expression3 parsePr2() throws ParsingException {
        Expression3 exp = parsePr1();
        Character nc = next();
        while (nc != null && (nc.equals('+') || nc.equals('-'))) {
            move();
            Expression3 exp2 = parsePr1();
            if (nc.equals('+')) {
                exp = new Add(exp, exp2);
            } else {
                exp = new Subtract(exp, exp2);
            }
            nc = next();
        }
        return exp;
    }

    private Expression3 parsePr1() throws ParsingException {
        Expression3 exp = parsePr0();
        Character nc = next();
        while (nc != null && (nc.equals('*') || nc.equals('/'))) {
            move();
            Expression3 exp2 = parsePr0();
            if (nc.equals('*')) {
                exp = new Multiply(exp, exp2);
            } else {
                exp = new Divide(exp, exp2);
            }
            nc = next();
        }
        return exp;
    }

    private Expression3 parsePr0() throws ParsingException {
        if(next().equals('+')) {
            move();
            return parsePr0();
        }
        if (next().equals('-')) {
            move();
            return new UnaryMinus(parsePr0());
        }
        return parseUnit();
    }

    private Expression3 parseUnit() throws ParsingException {
        expectNotEndBut("Unit");
        Character c = next();
        if (Character.isDigit(c)) {
            return parseConst();
        }
        if (c.equals('(')) {
            move();
            Expression3 exp = parsePr2();
            expect(')');
            return exp;
        }
        return parseVar();
    }

    private Expression3 parseConst() throws ParsingException {
        StringBuilder sb = new StringBuilder();
        while (next() != null && Character.isDigit(next())) {
            char c = move();
            sb.append(c);
        }
        if(next() != null && next().equals('.')) {
            char c = move();
            sb.append(c);
            while (next() != null && Character.isDigit(next())) {
                c = move();
                sb.append(c);
            }
        }
        if(next() != null && next().equals('E')) {
            char c = move();
            sb.append(c);
            if(next() != null && next().equals('-')) {
                c = move();
                sb.append(c);
            }
            while (next() != null && Character.isDigit(next())) {
                c = move();
                sb.append(c);
            }
        }
        return new Const(Double.valueOf(sb.toString()));
    }

    private Expression3 parseVar() throws ParsingException {
        expectNotEndBut("variable");
        Character c = next();
        switch (c) {
            case 'x':
            case 'y':
            case 'z':
                move();
                return new Variable(String.valueOf(c));
            default:
                throw new ParsingException(input, curPos, "variable");
        }
    }
}
