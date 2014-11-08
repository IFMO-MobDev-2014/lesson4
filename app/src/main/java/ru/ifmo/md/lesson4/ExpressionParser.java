package ru.ifmo.md.lesson4;

public class ExpressionParser {
    private static enum Lexem {
        OPEN_BRACKET, CLOSE_BRACKET, UNARY_MINUS, MULTIPLICATION_SIGN, DIVISION_SIGN,
        PLUS, MINUS, POWER, VARIABLE, ABS, LB, NEGETION, NUMBER, END
    }

    private static String s;
    private static Lexem previousLexem, currentLexem;
    private static int position;
    private static double numberValue;
    private static char variableValue;

    private static void skipWhitespaces() {
        while (position < s.length() && Character.isWhitespace(s.charAt(position))) {
            position++;
        }
    }

    private static void setNextLexem() throws CalculationException {
        previousLexem = currentLexem;
        skipWhitespaces();
        if (position >= s.length()) {
            currentLexem = Lexem.END;
            return;
        }
        position++;
        Character ch = s.charAt(position - 1);
        switch (ch) {
            case '(':
                currentLexem = Lexem.OPEN_BRACKET;
                return;
            case ')':
                currentLexem = Lexem.CLOSE_BRACKET;
                return;
            case '*':
                currentLexem = Lexem.MULTIPLICATION_SIGN;
                return;
            case '/':
                currentLexem = Lexem.DIVISION_SIGN;
                return;
            case '+':
                currentLexem = Lexem.PLUS;
                return;
            case '~':
                currentLexem = Lexem.NEGETION;
                return;
            case '^':
                currentLexem = Lexem.POWER;
                return;
        }
        if (ch == '.' || Character.isDigit(ch) || ch == '-' && position < s.length() && Character.isDigit(s.charAt(position))
                && previousLexem != Lexem.CLOSE_BRACKET
                && previousLexem != Lexem.VARIABLE
                && previousLexem != Lexem.ABS
                && previousLexem != Lexem.NUMBER) {
            String tmp = ch.toString();
            while (position < s.length() && Character.isDigit(s.charAt(position))) {
                ch = s.charAt(position++);
                if (Character.isDigit(ch))
                    tmp += ch;
            }
            if (position < s.length() && s.charAt(position) == '.') {
                tmp += '.';
                position++;
                while (position < s.length() && Character.isDigit(s.charAt(position))) {
                    ch = s.charAt(position++);
                    if (Character.isDigit(ch))
                        tmp += ch;
                }
            }
            currentLexem = Lexem.NUMBER;
            try {
                numberValue = Double.parseDouble(tmp);
            } catch (NumberFormatException e) {
                throw new InvalidNumberException(tmp);
            }
            return;
        }
        if (ch == '-') {
            if (previousLexem == null) {
                currentLexem = Lexem.UNARY_MINUS;
                return;
            }
            switch (previousLexem) {
                case CLOSE_BRACKET:
                case VARIABLE:
                case ABS:
                case NUMBER:
                    currentLexem = Lexem.MINUS;
                    return;
                case END:
                    throw new CalculationException(s, position);
                default:
                    currentLexem = Lexem.UNARY_MINUS;
                    return;
            }
        }
        if (ch.toString().matches("[xyz]")) {
            throw new CalculationException("Variables are not supported", 0);
//            skipWhitespaces();
//            if (position == s.length()) {
//                currentLexem = Lexem.VARIABLE;
//                variableValue = ch;
//                return;
//            }
//            switch (s.charAt(position)) {
//                case ')':
//                case '-':
//                case '+':
//                case '*':
//                case '/':
//                case '^':
//                    currentLexem = Lexem.VARIABLE;
//                    variableValue = ch;
//                    return;
//            }
        }
        if (ch == 'a') {
            String tmp;
            try {
                tmp = s.substring(position - 1, position + 3);
            } catch (StringIndexOutOfBoundsException e) {
                throw new CalculationException(s, position);
            }
            position += 2;
            if (tmp.equals("abs(")) {
                currentLexem = Lexem.ABS;
                return;
            } else {
                throw new CalculationException(s, position - 2);
            }
        }
        if (ch == 'l') {
            String tmp;
            try {
                tmp = s.substring(position - 1, position + 2);
            } catch (StringIndexOutOfBoundsException e) {
                throw new CalculationException(s, position);
            }
            position += 1;
            if (tmp.equals("lb(")) {
                currentLexem = Lexem.LB;
                return;
            } else {
                throw new CalculationException(s, position - 1);
            }
        }
        throw new CalculationException(s, position);
    }

    public static Expression3 parse(String parsingString) throws CalculationException {
        s = parsingString.trim();
        position = 0;
        previousLexem = null;
        currentLexem = null;
        numberValue = 0;
        variableValue = 'n';
        Expression3 result;

        setNextLexem();
        result = expression();
        if (currentLexem != Lexem.END) {
            throw new CalculationException(s, position);
        }

        return result;
    }

    private static Expression3 expression() throws CalculationException {
        Expression3 acc = summand();
        while (currentLexem == Lexem.PLUS || currentLexem == Lexem.MINUS) {
            setNextLexem();
            if (previousLexem == Lexem.PLUS) {
                acc = new Add(acc, summand());
            }
            if (previousLexem == Lexem.MINUS) {
                acc = new Subtract(acc, summand());
            }
        }
        return acc;
    }

    private static Expression3 summand() throws CalculationException {
        Expression3 acc = power();
        while (currentLexem == Lexem.MULTIPLICATION_SIGN || currentLexem == Lexem.DIVISION_SIGN) {
            setNextLexem();
            if (previousLexem == Lexem.MULTIPLICATION_SIGN) {
                acc = new Multiply(acc, power());
            }
            if (previousLexem == Lexem.DIVISION_SIGN) {
                acc = new Divide(acc, power());
            }
        }
        return acc;
    }

    private static Expression3 power() throws CalculationException {
        Expression3 acc = multiplier();
        while (currentLexem == Lexem.POWER) {
            setNextLexem();
            acc = new Power(acc, power());
        }
        return acc;
    }

    private static Expression3 multiplier() throws CalculationException {
        setNextLexem();
        switch (previousLexem) {
            case NUMBER:
                return new Const(numberValue);
            case OPEN_BRACKET:
                Expression3 acc = expression();
                if (currentLexem == Lexem.CLOSE_BRACKET)
                    setNextLexem();
                else
                    throw new CalculationException(s, position);
                return acc;
            case UNARY_MINUS:
                return new UnaryMinus(multiplier());
            case VARIABLE:
                return new Variable(variableValue);
            case ABS:
                return new Abs(multiplier());
            case LB:
                return new Lb(multiplier());
        }
        throw new CalculationException(s, position - 1);
    }
}