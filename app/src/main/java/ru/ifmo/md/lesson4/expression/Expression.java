package ru.ifmo.md.lesson4.expression;

import java.util.Map;

/**
 * Expression is the interface for all type of expressions met in the math logics.
 * Expressions can be evaluated, generate Java code, be converted to string,
 * matched with other expression.
 * @author  Daniar Itegulov
 * @since %I% %G%
 * @version 1.1
 */
public interface Expression {
    /**
     * Returns the result of evaluating the expression. Variable values must be
     * specified in {@link java.util.Map}. Throws {@link IllegalArgumentException}
     * if some variables value hasn't been specified.
     * @return          result of evaluating the expression
    */
    public double calculate();
}
