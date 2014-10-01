package ru.ifmo.md.lesson4.Expression.Parser;

public class ParsingException extends Exception {
    private Integer errPos;
    private String expected;

    public ParsingException(String msg) {
        super(msg);
    }

    public ParsingException(String exp, Integer errPos, String expected) {
        super(
                "Parsing error in expression \"" + exp +
                        "\" on position " + errPos + ":\n\t" +
                        "Invalid symbol \"" + exp.charAt(errPos) + "\",\n\t" +
                        expected + " expected."
        );
        this.errPos = errPos;
        this.expected = expected;
    }
}
