package ru.ifmo.md.lesson4;

public class DummyCalculateEngine implements CalculationEngine {

    public class Symbol {

        String str;
        double d;

        public Symbol(double d, String str) {

            this.str = str;
            this.d = d;

        }

    }

    private Symbol addition(String expression) throws CalculationException {

        Symbol current = multiplication(expression);
        double d = current.d;

        while (current.str.length() > 0) {
            if (!(current.str.charAt(0) == '+' || current.str.charAt(0) == '-')) {
                break;
            }

            char sign = current.str.charAt(0);
            String nextLine = current.str.substring(1);

            current = multiplication(nextLine);
            if (sign == '+') {
                d += current.d;
            } else {
                d -= current.d;
            }
        }
        return new Symbol(d, current.str);
    }

    private Symbol multiplication(String expression) throws CalculationException {
        Symbol current = brackets(expression);
        double d = current.d;
        while (true) {
            if (current.str.length() == 0) {
                return current;
            }
            char sign = current.str.charAt(0);
            if (sign != '*' && sign != '/') {
                return current;
            }

            String next = current.str.substring(1);
            Symbol right = brackets(next);

            if (sign == '*') {
                d *= right.d;
            } else {
                if (right.d == 0) {
                    throw new CalculationException();
                }
                d /= right.d;

            }

            current = new Symbol(d, right.str);
        }
    }

    private Symbol brackets(String expression) throws CalculationException {

        if (expression.charAt(0) == '(') {
            Symbol symbol = addition(expression.substring(1));
            if (!symbol.str.isEmpty() && symbol.str.charAt(0) == ')') {
                symbol.str = symbol.str.substring(1);
            }
            return symbol;
        }
        return digit(expression);
    }

    private Symbol digit(String expression) {

        boolean negative = false;

        if (expression.charAt(0) == '-') {
            negative = true;
            expression = expression.substring(1);
        }

        int i = 0;
        while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
            i++;
        }

        double number = (negative) ? (-Double.parseDouble(expression.substring(0, i))) : Double.parseDouble(expression.substring(0, i));

        return new Symbol(number, expression.substring(i));
    }

    @Override
    public double calculate(String expression) throws CalculationException {
        return addition(expression.replace(" ", "")).d;
    }
}
