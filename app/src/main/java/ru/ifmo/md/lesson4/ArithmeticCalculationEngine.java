package ru.ifmo.md.lesson4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dimatomp on 05.10.14.
 */
public class ArithmeticCalculationEngine implements CalculationEngine {
    private static final Pattern doublePattern = Pattern.compile("\\-?(\\.[0-9]+|[0-9]+(\\.[0-9]*)?)([e│E][\\+\\-]?[0-9]+)?");

    @Override
    public double calculate(String expression) throws CalculationException {
        return new ArithmeticCalculator(expression).calculate(Priority.values()[0]);
    }

    private enum Priority {ADD, MULTIPLY, NEGATE}

    private static class ArithmeticCalculator {
        private final String expression;
        private final Matcher matcher;
        private int position;

        private ArithmeticCalculator(String expression) {
            this.expression = expression;
            this.matcher = doublePattern.matcher(expression);
        }

        private double calculate(Priority priority) throws CalculationException {
            Double result = null;
            while (position < expression.length()) {
                char cChar = expression.charAt(position++);
                if (result == null ? (cChar == '+' || cChar == '*' || cChar == '/' || cChar == ')') : cChar == '(')
                    throw new CalculationException(expression, position - 1);
                switch (cChar) {
                    case '(':
                        result = calculate(Priority.ADD);
                        position++;
                        break;
                    case '-':
                        if (result == null) {
                            result = -calculate(Priority.NEGATE);
                            break;
                        } else if (priority.ordinal() <= Priority.ADD.ordinal()) {
                            result -= calculate(Priority.ADD);
                            break;
                        }
                    case '+':
                        if (priority.ordinal() <= Priority.ADD.ordinal()) {
                            result += calculate(Priority.ADD);
                            break;
                        }
                    case '*':
                        if (priority.ordinal() <= Priority.MULTIPLY.ordinal()) {
                            result *= calculate(Priority.MULTIPLY);
                            break;
                        }
                    case '/':
                        if (priority.ordinal() <= Priority.MULTIPLY.ordinal()) {
                            result *= calculate(Priority.MULTIPLY);
                            break;
                        }
                    case ')':
                        position--;
                        return result;
                    default:
                        if (!matcher.find(position - 1) || result != null)
                            throw new CalculationException(expression, position - 1);
                        result = Double.parseDouble(expression.substring(matcher.start(), matcher.end()));
                        position += matcher.end() - matcher.start() - 1;
                        break;
                }
            }
            if (result == null)
                throw new CalculationException(expression);
            return result;
        }
    }
}
