package ru.ifmo.md.lesson4;

import android.content.CursorLoader;
import android.util.Log;

public class DummyCalculateEngine implements CalculationEngine {
    private int curPos = -1;
    private String s;

    boolean good() {
        return curPos < s.length();
    }

    void nextLexem() {
        curPos++;
        for (;good() && Character.isWhitespace(s.charAt(curPos)); curPos++);
    }

    String getDigits() {
        String temp = "";
        for (;good() && Character.isDigit(s.charAt(curPos)); curPos++) {
            temp = temp + s.charAt(curPos);
        }
        curPos--;
        return temp;
    }

    double number() throws CalculationException {
        double first = 0;
//        Log.i("LOG", String.valueOf(curPos));
//        Log.i("LOG", String.valueOf(s.charAt(curPos)));
        if (!good())
            return first;

        if (s.charAt(curPos) == '-') {
            nextLexem();
            first = -1 * number();
        } else if (s.charAt(curPos) == '+') {
            nextLexem();
            first = number();
        } else if (s.charAt(curPos) == '(') {
                nextLexem();
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

    double term() throws CalculationException {
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

    double expr() throws CalculationException {
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

    @Override
    public double calculate(String expression) throws CalculationException {
        s = expression;
        nextLexem();
        double result = expr();
        if (good())
            throw new CalculationException("unknown symbol on position: " + curPos);
        return result;
    }
}
