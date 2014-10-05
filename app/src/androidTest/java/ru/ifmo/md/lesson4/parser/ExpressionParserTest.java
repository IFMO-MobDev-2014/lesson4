package ru.ifmo.md.lesson4.parser;

import junit.framework.TestCase;

public class ExpressionParserTest extends TestCase {

    public void testParse() throws Exception {
        double expr = ExpressionParser.parse("5").evaluate();
        //assertEquals(expr, 5);
    }
}