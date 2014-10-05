package ru.ifmo.md.lesson4;

public class CalculationException extends Exception {
    private final String expression;
    private final Integer position;

    public CalculationException(String expression, Integer position) {
        super("Unexpected token in '" + expression + "' (at position " + position + ")");
        this.expression = expression;
        this.position = position;
    }

    public CalculationException(String expression) {
        super("Failed to parse expression '" + expression + "'");
        this.expression = expression;
        this.position = null;
    }

    public CalculationException() {
        super("Failed to parse an expression");
        this.expression = null;
        this.position = null;
    }

    public String getExpression() {
        return expression;
    }

    public Integer getPosition() {
        return position;
    }
}
