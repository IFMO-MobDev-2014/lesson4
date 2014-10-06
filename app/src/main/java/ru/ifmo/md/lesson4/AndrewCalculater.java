package ru.ifmo.md.lesson4;

import java.util.StringTokenizer;

import ru.ifmo.md.lesson4.expression.Add;
import ru.ifmo.md.lesson4.expression.Const;
import ru.ifmo.md.lesson4.expression.Divide;
import ru.ifmo.md.lesson4.expression.Expression3;
import ru.ifmo.md.lesson4.expression.Lexems;
import ru.ifmo.md.lesson4.expression.Multiply;
import ru.ifmo.md.lesson4.expression.Subtract;
import ru.ifmo.md.lesson4.expression.UnaryMinus;

/**
 * Created by andreikapolin on 06.10.14.
 */
public class AndrewCalculater implements CalculationEngine {
    private static String expression;
    private static String curToken;
    private static Lexems current;
    private static StringTokenizer tokenizer;

    @Override
    public double calculate(String expression) throws CalculationException {
        Expression3 expr = parse(expression);
        return expr.evaluate();
    }

    public static Expression3 parse(String expression) throws CalculationException {
        tokenizer = new StringTokenizer(expression, "+-*/()", true);
        nextLexem();
        Expression3 result = parseExpression();
        if (current != Lexems.EOF) {
            throw new CalculationException();
        }
        return result;
    }


    private static void nextLexem() {
        if (tokenizer.hasMoreTokens()) {
            curToken = tokenizer.nextToken();
            if (curToken.equals("+")) {
                current = Lexems.PLUS;
            } else if (curToken.equals("-")) {
                current = Lexems.MINUS;
            } else if (curToken.equals("*")) {
                current = Lexems.MULTIPLY;
            } else if (curToken.equals("/")) {
                current = Lexems.DIVIDE;
            } else if (curToken.equals("(")) {
                current = Lexems.OPENED;
            } else if (curToken.equals(")")) {
                current = Lexems.CLOSED;
            } else {
                current = Lexems.NUMBER;
            }
        } else {
            curToken = "###";
            current = Lexems.EOF;
        }
    }

    private static Expression3 parseExpression() throws CalculationException {
        Expression3 result = parseSummand();
        while (current == Lexems.PLUS || current == Lexems.MINUS) {
            boolean plus = current == Lexems.PLUS;
            nextLexem();
            Expression3 summand = parseSummand();
            if (plus) {
                result = new Add(result, summand);
            } else {
                result = new Subtract(result, summand);
            }
        }
        return result;
    }

    private static Expression3 parseSummand() throws CalculationException {
        Expression3 result = parseFactor();
        while (current == Lexems.MULTIPLY || current == Lexems.DIVIDE) {
            boolean times = current == Lexems.MULTIPLY;
            nextLexem();
            Expression3 factor = parseFactor();
            if (times) {
                result = new Multiply(result, factor);
            } else {
                result = new Divide(result, factor);
            }
        }
        return result;
    }

    private static Expression3 parseFactor() throws CalculationException {
        Expression3 result;
        if (current == Lexems.OPENED) {
            nextLexem();
            result = parseExpression();
            if (current != Lexems.CLOSED) {
                throw new CalculationException();
            }
            nextLexem();
        } else if (current == Lexems.MINUS) {
            double tmp;
            nextLexem();
            try {
                tmp = Double.parseDouble(curToken);
                result = new Const(-tmp);
                nextLexem();
            } catch (NumberFormatException e) {
                result = new UnaryMinus(parseFactor());
            }
        } else {
            double tmp;
            try {
                tmp = Double.parseDouble(curToken);
                result = new Const(tmp);
                nextLexem();
            } catch (NumberFormatException e) {
                throw new CalculationException();
            }
        }
        return result;
    }
}