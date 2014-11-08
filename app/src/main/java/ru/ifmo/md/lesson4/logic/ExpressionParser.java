package ru.ifmo.md.lesson4.logic;

import ru.ifmo.md.lesson4.logic.ExceptionsHandle.IllegalCharacterException;
import ru.ifmo.md.lesson4.logic.ExceptionsHandle.ParserException;

public final class ExpressionParser {
    private String expression;
    private int position;
    private String nextNumber;
    private Token currentToken;

    enum Token {
        OPEN,
        CLOSE,
        PLUS,
        MINUS,
        MUL,
        DIV,
        NUM,
        POW,
        END
    }

    public Expression parse(String string) throws ParserException {
        expression = string;
        position = 0;
        currentToken = nextToken();
        return readExp();
    }

    private Token nextToken() throws IllegalCharacterException {
        while (Character.isWhitespace(currentChar())) position++;
        if (position >= expression.length()) return Token.END;
        char oldChar = currentChar();
        position++;
        switch (oldChar) {
            case '(':
                return Token.OPEN;
            case ')':
                return Token.CLOSE;
            case '+':
                return Token.PLUS;
            case '-':
                return Token.MINUS;
            case '*':
                return Token.MUL;
            case '/':
                return Token.DIV;
            case '^':
                return Token.POW;
            default: {
                if (Character.isDigit(oldChar)) {
                    nextNumber = String.valueOf(oldChar);
                    while (Character.isDigit(currentChar())) {
                        nextNumber += currentChar();
                        position++;
                    }
                    if (currentChar() == '.') {
                        nextNumber += '.';
                        position++;
                        while (Character.isDigit(currentChar())) {
                            nextNumber += currentChar();
                            position++;
                        }
                        if (currentChar() == 'E') {
                            nextNumber += currentChar();
                            position++;
                            if (currentChar() == '+' || currentChar() == '-') {
                                nextNumber += currentChar();
                                position++;
                            }
                            while (Character.isDigit(currentChar())) {
                                nextNumber += currentChar();
                                position++;
                            }
                        }
                    }
                    return Token.NUM;
                }
            }
        }
        throw new IllegalCharacterException(position);
    }

    private char currentChar() {
        if (position >= expression.length()) return '@'; //impossible symbol in parsing
        return expression.charAt(position);
    }

    private Expression readNum(boolean negative) throws ParserException {
        // flag == true : means that this decimal number should be negative
        if (currentToken != Token.NUM)
            throw new IllegalCharacterException(position);
        currentToken = nextToken();
        if (negative)
            nextNumber = '-' + nextNumber;
        double result = Double.parseDouble(nextNumber);
        return new Const(result);
    }

    private Expression readMul() throws ParserException {
        // <Множитель> = {+|-|~} <ДробноеЧисло>|(<Выражение>)
        int cntMinuses = 0;
        while (currentToken == Token.PLUS || currentToken == Token.MINUS) {
            if (currentToken == Token.MINUS) {
                cntMinuses++;
            }
            currentToken = nextToken();
        }
        Expression result;
        switch (currentToken) {
            case NUM: {
                boolean negative = cntMinuses > 0;
                if (negative) cntMinuses--;  //the last minus belongs to negative decimal number
                result = readNum(negative);
                break;
            }
            case OPEN: {
                currentToken = nextToken();
                result = readExp();
                if (currentToken != Token.CLOSE)
                    throw new IllegalCharacterException(position);
                currentToken = nextToken();
                break;
            }
            default:
                throw new IllegalCharacterException(position);
        }
        if (cntMinuses % 2 == 1)
            result = new UnaryMinus(result);
        return result;
    }

    private Expression readPower() throws ParserException {
        //<Множитель_степень> = <Множитель> {^ <Множитель_степень>}
        Expression result = readMul();
        while (currentToken == Token.POW) {
            currentToken = nextToken();
            Expression cur = readPower();
            result = new Power(result, cur);
        }
        return result;
    }

    private Expression readTerm() throws ParserException {
        // <Слагаемое> = <Множитель_степень>{*|/<Множитель_степень>}
        Expression result = readPower();
        while (currentToken == Token.MUL || currentToken == Token.DIV) {
            Token operator = currentToken;
            currentToken = nextToken();
            Expression cur = readPower();
            if (operator == Token.MUL)
                result = new Multiply(result, cur);
            else
                result = new Divide(result, cur);
        }
        return result;
    }

    private Expression readExp() throws ParserException {
        // <Выражение> = <Слагаемое>{+|-<Слагаемое>}
        Expression result = readTerm();
        while (currentToken == Token.PLUS || currentToken == Token.MINUS) {
            Token operator = currentToken;
            currentToken = nextToken();
            Expression cur = readTerm();
            if (operator == Token.PLUS)
                result = new Add(result, cur);
            else
                result = new Subtract(result, cur);
        }
        if (currentToken != Token.CLOSE && currentToken != Token.END)
            throw new IllegalCharacterException(position);
        return result;
    }

}