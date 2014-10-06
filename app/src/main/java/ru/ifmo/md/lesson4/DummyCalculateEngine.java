package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {
    enum expr {
        Num, Plus, Minus, Mul,
        Div, Open, Close, End;
    }

    private static String expression;
    private static expr curLexem;
    private static double value;
    private static int index;

    private static boolean isDigit(char symbol) {
        return '0' <= symbol && symbol <= '9';
    }

    private static boolean isOperation(char symbol) {
        return (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/');
    }

    private static void parseNumber() throws ParseErrorException {
        value = .0;
        String number = "";
        while (index < expression.length() && isDigit(expression.charAt(index)))
            number += expression.charAt(index++);
        if (index == expression.length()) {
            value = Double.parseDouble(number);
            return;
        }
        char symbol = expression.charAt(index);
        if (symbol == '.') {
            number += symbol;
            index++;
        } else if (symbol == 'e' || symbol == 'E') {
            number += symbol;
            index++;
            if (index == expression.length()) {
                throw new ParseErrorException("After letter E digits must go");
            }
            if (expression.charAt(index) == '+' || expression.charAt(index) == '-') {
                number += expression.charAt(index++);
            }
        }
        while (index < expression.length() && isDigit(expression.charAt(index))) {
            number += expression.charAt(index++);
        }
        try {
            value = Double.parseDouble(number);
        } catch (java.lang.NumberFormatException e) {
            throw new ParseErrorException("Wrong number, can't parse");
        }
    }

    private static void nextLexem() throws SyntaxErrorException {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
        if (index >= expression.length()) {
            curLexem = expr.End;
            return;
        }
        char symbol = expression.charAt(index);
        if (isDigit(symbol)) {
            curLexem = expr.Num;
            parseNumber();
            index--;
        } else if (symbol == '+') {
            curLexem = expr.Plus;
        } else if (symbol == '-') {
            curLexem = expr.Minus;
        } else if (symbol == '*') {
            curLexem = expr.Mul;
        } else if (symbol == '/') {
            curLexem = expr.Div;
        } else if (symbol == '(') {
            curLexem = expr.Open;
        } else if (symbol == ')') {
            curLexem = expr.Close;
        } else {
            throw new WrongSymbolException("Wrong symbol at position " + index + ": " + symbol);
        }
        index++;
    }

    private static double parseMultiplier() throws SyntaxErrorException {
        if (curLexem == expr.Minus) {
            nextLexem();
            return -parseMultiplier();
        } else if (curLexem == expr.Num) {
            double number = value;
            nextLexem();
            return number;
        } else if (curLexem == expr.Open) {
            nextLexem();
            double answer = parseExpression();
            if (curLexem != expr.Close) {
                throw new WrongBracketsSequenceException("Cannot find pair to open bracket");
            }
            nextLexem();
            return answer;
        }
        throw new ParseErrorException("Parse error at index " + index);
    }

    private static double parseSummand() throws SyntaxErrorException {
        double answer = parseMultiplier();
        while (curLexem == expr.Mul || curLexem == expr.Div) {
            expr c = curLexem;
            nextLexem();
            if (c == expr.Mul) {
                answer *= parseMultiplier();
            } else {
                answer /= parseMultiplier();
            }
        }
        return answer;
    }

    private static double parseExpression() throws SyntaxErrorException {
        double answer = parseSummand();
        while (curLexem == expr.Plus || curLexem == expr.Minus) {
            expr c = curLexem;
            nextLexem();
            if (c == expr.Plus) {
                answer += parseSummand();
            } else {
                answer -= parseSummand();
            }
        }
        return answer;
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        if (expression.equals("")) {
            throw new EmptyExpressionException("Cannot parse empty expression");
        }
        this.expression = expression;
        index = 0;
        nextLexem();
        double answer = parseExpression();
        if (index != this.expression.length()) {
            throw new ParseErrorException("Parse error at symbol " + index + ": " + expression.charAt(index));
        }
        return answer;
    }

    /*public static void main(String[] args) {
        DummyCalculateEngine t = new DummyCalculateEngine();
        try {
            double x = t.calculate("5.0 + 0.+");
            System.out.println(x);
        } catch (CalculationException e) {
            System.out.println(e.getMessage());
        }
    }*/
}
