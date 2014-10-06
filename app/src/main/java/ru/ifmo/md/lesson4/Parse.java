package ru.ifmo.md.lesson4;

/**
 * Created by eugene on 10/5/14.
 */
public class Parse {
    private static int curPos;
    private static String s;

    private static boolean good() {
        return curPos < s.length();
    }

    private static void nextLexem() {
        curPos++;
        for (;good() && Character.isWhitespace(s.charAt(curPos)); curPos++);
    }

    private static String getDigits() {
        String temp = "";
        for (;good() && Character.isDigit(s.charAt(curPos)); curPos++) {
            temp = temp + s.charAt(curPos);
        }
        curPos--;
        return temp;
    }

    private static double number() throws CalculationException {
        double first = 0;
        if (!good())
            throw new CalculationException("unknown symbol on position: " + Integer.toString(curPos));

        if (s.charAt(curPos) == '-') {
            nextLexem();
            first = -1 * number();
        } else if (s.charAt(curPos) == '+') {
            nextLexem();
            first = number();
        } else if (s.charAt(curPos) == '(') {
            nextLexem();
            if (!good())
                throw new CalculationException("expected ')' on position: " + Integer.toString(curPos));
            first = expr();
            if (good() && s.charAt(curPos) == ')') {
                nextLexem();
            } else {
                throw new CalculationException("expected ')' on position: " + Integer.toString(curPos));
            }
        } else if (Character.isDigit(s.charAt(curPos))) {
            String parseNumber = getDigits();
            nextLexem();
            if (good() && s.charAt(curPos) == '.') {
                nextLexem();
                parseNumber += '.' + getDigits();
                nextLexem();
            }
            first = Double.parseDouble(parseNumber);
        } else {
            throw new CalculationException("unknown symbol on position: " + Integer.toString(curPos));
        }
        return first;
    }

    private static double term() throws CalculationException {
        double first = number();
        while (good()) {
            if (s.charAt(curPos) == '*') {
                nextLexem();
                first *= number();
            } else if (s.charAt(curPos) == '/') {
                nextLexem();
                first /= number();
            } else {
                break;
            }
        }
        return first;
    }

    private static double expr() throws CalculationException {
        double first = term();
        while (good()) {
            if (s.charAt(curPos) == '+') {
                nextLexem();
                first += term();
            } else if (s.charAt(curPos) == '-') {
                nextLexem();
                first -= term();
            } else {
                break;
            }
        }
        return first;
    }

    public static double calculate(String expression) throws CalculationException {
        curPos = -1;
        s = expression;
        nextLexem();
        double result = expr();
        if (good())
            throw new CalculationException("unknown symbol on position: " + curPos);
        return result;
    }
}
