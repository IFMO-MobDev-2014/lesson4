package ru.ifmo.md.lesson4.parser;

import junit.framework.TestCase;

public class ExpressionParserTest extends TestCase {

    public void testParse() throws Exception {
        Expression expr = ExpressionParser.parse("5^5");
        assertEquals(expr, new Const(5));
    }
}