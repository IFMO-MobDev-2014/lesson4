package ru.ifmo.md.lesson4.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by dimatomp on 05.10.14.
 */
public class EngineeringCalculationEngine implements CalculationEngine {
    private static final Pattern doublePattern = Pattern.compile("(\\.[0-9]+|[0-9]+(\\.[0-9]*)?)([e│E][\\+\\-]?[0-9]+)?%?");

    @Override
    public double calculate(String expression) throws CalculationException {
        return new ArithmeticCalculator(expression).calculate(Priority.values()[0]);
    }

    private enum Priority {ADD, MULTIPLY, POWER, NEGATE}

    private static class ArithmeticCalculator {
        private final String expression;
        private final Matcher numMatcher, funcMatcher, constMatcher;
        private int position;

        private ArithmeticCalculator(String expression) {
            this.expression = expression;
            this.numMatcher = doublePattern.matcher(expression);
            this.funcMatcher = Function.functionPattern.matcher(expression);
            this.constMatcher = Constant.constantPattern.matcher(expression);
        }

        private double calculate(Priority priority) throws CalculationException {
            Double result = null;
            while (position < expression.length()) {
                char cChar = expression.charAt(position++);
                if (result == null ? (cChar == '+' || cChar == '*' || cChar == '/' || cChar == '^' || cChar == ')') : cChar == '(')
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
                            result -= calculate(Priority.MULTIPLY);
                            break;
                        }
                    case '+':
                        if (cChar == '+' && priority.ordinal() <= Priority.ADD.ordinal()) {
                            result += calculate(Priority.MULTIPLY);
                            break;
                        }
                    case '*':
                        if (cChar == '*' && priority.ordinal() <= Priority.MULTIPLY.ordinal()) {
                            result *= calculate(Priority.POWER);
                            break;
                        }
                    case '/':
                        if (cChar == '/' && priority.ordinal() <= Priority.MULTIPLY.ordinal()) {
                            result /= calculate(Priority.POWER);
                            break;
                        }
                    case '^':
                        if (cChar == '^' && priority.ordinal() <= Priority.POWER.ordinal()) {
                            result = Math.pow(result, calculate(Priority.POWER));
                            break;
                        }
                    case ')':
                        position--;
                        return result;
                    default:
                        if (result != null)
                            throw new CalculationException(expression, position - 1);
                        if (numMatcher.find(position - 1) && numMatcher.start() == position - 1) {
                            String number = expression.substring(numMatcher.start(), numMatcher.end());
                            if (number.endsWith("%"))
                                result = Double.parseDouble(number.substring(0, number.length() - 1)) / 100;
                            else
                                result = Double.parseDouble(number);
                            position += numMatcher.end() - numMatcher.start() - 1;
                            break;
                        }
                        if (funcMatcher.find(position - 1) && funcMatcher.start() == position - 1) {
                            Function func = Function.findByName(expression.substring(funcMatcher.start(), funcMatcher.end()));
                            position += funcMatcher.end() - funcMatcher.start() - 1;
                            result = func.calculate(calculate(Priority.NEGATE));
                            break;
                        }
                        if (constMatcher.find(position - 1) && constMatcher.start() == position - 1) {
                            result = Constant.findByName(expression.substring(constMatcher.start(), constMatcher.end()));
                            position += constMatcher.end() - constMatcher.start() - 1;
                            break;
                        }
                        throw new CalculationException(expression, position - 1);
                }
            }
            if (result == null)
                throw new CalculationException(expression);
            return result;
        }
    }
}
