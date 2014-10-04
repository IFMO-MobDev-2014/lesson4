package ru.ifmo.md.lesson4;

/**
 * Created by fotyev on 04-Oct-14.
 */
public class Calculator implements CalculationEngine {
    private Lexer lex;

    public double calculate(String expression) throws CalculationException {
        lex = new Lexer(expression);
        lex.skipWhitespace();
        double result = expression();
        lex.skipWhitespace();
        if (lex.hasMoreTokens()) {
            // unexpected!
            throw new CalculationException();
        }
        lex = null; // free memory
        return result;
    }

    // expression = term {("+"|"-") term} .
    private double expression() throws CalculationException {
        double result = term();
        char tok = lex.peekToken();
        while (lex.accept('+') || lex.accept('-')) {
            if (tok == '+') {
                result += term();
            } else { // '-'
                result -= term();
            }
            tok = lex.peekToken();
        }
        return result;
    }

    // term = factor {("*"|"/") factor} .
    private double term() throws CalculationException {
        double result;

        result = factor();
        char tok = lex.peekToken();
        while (lex.accept('*') || lex.accept('/')) {
            if (tok == '*') {
                result *= factor();
            } else { // '/'
                // TODO: division by zero ?
                result /= factor();
            }

            tok = lex.peekToken();
        }
        return result;
    }

    // factor = ["[-+]"] factor | number | "(" expression ")" .
    private double factor() throws CalculationException {
        double result;
        char tok = lex.peekToken();

        if (lex.accept('-')) { // unary minus
            result = -factor();
        } else if (lex.accept('+')) { // unary plus
            result = factor();
        } else if (lex.accept('(')) { // expression
            result = expression();
            if (!lex.accept(')')) {
                // expected )
                throw new CalculationException();
            }
        } else { // number
            result = number();
        }
        return result;
    }


    // number = { "[0-9]" } [ "." { "[0-9]" } ]
    private double number() throws CalculationException {
        if (lex.accept((char) 0x221E)) { // infinity
            return Double.POSITIVE_INFINITY;
        }
        char tok = lex.peekToken();
        double result = 0.0;
        boolean hasDigits = false;
        while (tok >= '0' && tok <= '9') {
            result *= 10;
            result += (double) (tok - '0');
            tok = lex.nextChar();
            hasDigits = true;
        }
        if (lex.accept('.')) {
            double multiplier = 1.0;
            tok = lex.peekToken();
            while (tok >= '0' && tok <= '9') {
                multiplier *= 10;
                result += ((double) (tok - '0')) / multiplier;
                tok = lex.nextChar();
                hasDigits = true;
            }
        }
        if (!hasDigits) {
            // either "." or not a number
            throw new CalculationException();
        }
        lex.skipWhitespace();
        return result;
    }

}
