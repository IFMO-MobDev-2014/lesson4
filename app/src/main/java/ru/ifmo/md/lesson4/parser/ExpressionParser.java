package ru.ifmo.md.lesson4.parser;

import ru.ifmo.md.lesson4.parser.exceptions.MyException;
import ru.ifmo.md.lesson4.parser.exceptions.ParseException;
import ru.ifmo.md.lesson4.parser.expression.Const;
import ru.ifmo.md.lesson4.parser.expression.Expression3;
import ru.ifmo.md.lesson4.parser.expression.Variable;
import ru.ifmo.md.lesson4.parser.number.Helper;
import ru.ifmo.md.lesson4.parser.number.MyNumber;

public class ExpressionParser<T extends MyNumber<T>> {
    private String lastLexem;
    private Lexer lexer;
    private Helper<T> helper;

    //-1 )
    // 0 +- // left
    // 1 */ // left
    // 2 ^  // right
    // 3 unary functions; const; variables; (; reading

    boolean isConst(String s) {
        return s.charAt(0) >= '0' && s.charAt(0) <= '9';
    }

    boolean isVariable(String s) {
        return helper.isVariable(s);
    }

    private Expression3<T> parseExpr(int h) throws ParseException {
        try {
            if (h < 3) {
                Expression3<T> left = parseExpr(h + 1);
                String s = lastLexem;
                if (s.equals("") || helper.getPrior(s) < h) {
                    return left;
                }
                if (h == 0 || h == 1) {
                    while (true) {
                        s = lastLexem;
                        if (s.equals("") || helper.getPrior(s) < h) {
                            return left;
                        }
                        left = helper.applyFunction(s, left, parseExpr(h + 1));
                    }
                } else {
                    return helper.applyFunction(s, left, parseExpr(h));
                }
            } else {
                lastLexem = lexer.next();
                String s = lastLexem;
                if (s.equals("-")) {
                    s = "-un";
                }
                if (s.equals("")) {
                    throw new ParseException("parse error");
                }
                Expression3<T> result;

                if (isConst(s)) {
                    result = new Const<>(helper.parse(s));
                    lastLexem = lexer.next();
                } else
                if (s.equals("(")) {
                    result = parseExpr(0);
                    if (!lastLexem.equals(")")) {
                        throw new ParseException("parse error");
                    }
                    lastLexem = lexer.next();
                } else {
                if (isVariable(s)) {
                    result = new Variable<>(s);
                    lastLexem = lexer.next();
                } else
                    result = helper.applyFunction(s, parseExpr(h));
                }

                return result;
            }
        } catch (ParseException e) {
            throw new ParseException("parse error at " + lexer.getLast());
        }
    }

    private Expression3<T> run(String s, Helper<T> helper) throws MyException {
        this.helper = helper;
        lexer = new Lexer(s);
        Expression3<T> result = parseExpr(0);
        if (!lastLexem.equals("")) {
            throw new ParseException("parse fail at " + Integer.toString(lexer.getLast()));
        }
        return result;
    }

    static public <T extends MyNumber<T>> Expression3<T> parse(String s, Helper<T> helper) throws MyException {
        return new ExpressionParser<T>().run(s, helper);
    }
}