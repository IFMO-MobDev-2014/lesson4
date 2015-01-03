package ru.ifmo.md.lesson4;

/**
 * Created by timur on 02.01.15.
 */

public class ExpressionParser {

    private static enum Lexeme {
        plus, minus, mul, num, open, close, div, end
    }

    private static Lexeme currentLexeme;
    private static String expressionToParse;
    private static int position;
    private static double number;

    private ExpressionParser() {
    }

    private static Expression expression() throws CalculationException {
        Expression currentExpression = add();
        while (currentLexeme == Lexeme.minus || currentLexeme == Lexeme.plus) {
            Lexeme lastLexeme = currentLexeme;
            nextLexeme();
            if (lastLexeme == Lexeme.plus) {
                currentExpression = new Add(currentExpression, add());
            } else {
                currentExpression = new Subtract(currentExpression, add());
            }
        }
        return currentExpression;
    }

    private static Expression add() throws CalculationException {
        Expression currentExpression = mul();
        while (currentLexeme == Lexeme.mul || currentLexeme == Lexeme.div) {
            Lexeme lastLexeme = currentLexeme;
            nextLexeme();
            if (lastLexeme == Lexeme.mul) {
                currentExpression = new Multiply(currentExpression, mul());
            } else {
                currentExpression = new Divide(currentExpression, mul());
            }
        }
        return currentExpression;
    }

    private static Expression mul() throws CalculationException {
        if (currentLexeme == Lexeme.minus) {
            nextLexeme();
            if (currentLexeme == Lexeme.num) {
                nextLexeme();
                return new Const(-number);
            }
            return new UnaryMinus(mul());
        } else if (currentLexeme == Lexeme.num) {
            double current = number;
            nextLexeme();
            return new Const(current);
        } else if (currentLexeme == Lexeme.open) {
            nextLexeme();
            Expression current = expression();
            if (currentLexeme != Lexeme.close) {
                throw new CalculationException();
            }
            nextLexeme();
            return current;
        } else {
            throw new CalculationException();
        }
    }

    private static void nextLexeme() throws CalculationException {
        while (position < expressionToParse.length() && Character.isWhitespace(expressionToParse.charAt(position))) {
            position++;
        }
        if (position >= expressionToParse.length()) {
            return;
        }
        char currentChar = expressionToParse.charAt(position);
        if (Character.isDigit(currentChar) || currentChar == '.') {
            number = 0d;
            int lastPosition = position;
            while (position < expressionToParse.length() && (Character.isDigit(expressionToParse.charAt(position))
                    || expressionToParse.charAt(position) == '.')) {
                position++;
            }
            String currentNumber = expressionToParse.substring(lastPosition, position);
            try {
                number = Double.parseDouble(currentNumber);
            } catch (Exception e) {
                throw new CalculationException();
            }
            currentLexeme = Lexeme.num;
            position--;
        } else if (currentChar == '(') {
            currentLexeme = Lexeme.open;
        } else if (currentChar == '+') {
            currentLexeme = Lexeme.plus;
        } else if (currentChar == '/') {
            currentLexeme = Lexeme.div;
        } else if (currentChar == '$') {
            currentLexeme = Lexeme.end;
        } else if (currentChar == '-') {
            currentLexeme = Lexeme.minus;
        } else if (currentChar == '*') {
            currentLexeme = Lexeme.mul;
        } else if (currentChar == ')') {
            currentLexeme = Lexeme.close;
        } else {
            throw new CalculationException();
        }

        position++;
    }


    public static Expression parse(String argument) throws CalculationException {
        expressionToParse = argument + "$";
        position = 0;
        nextLexeme();
        Expression result = expression();
        if (currentLexeme != Lexeme.end) {
            throw new CalculationException();
        }
        return result;
    }

}

