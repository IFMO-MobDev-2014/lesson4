package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;
import ru.ifmo.md.lesson4.MyCalcEngine.Expression;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    private CalculationEngine evaluator;

    @Before
    public void setup() {
        evaluator = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void basicCorrectnessTests() {
        testParsingAndEval(3d, "3.0");
        testParsingAndEval(3d, "--3.0");
        testParsingAndEval(3d, "((((((3))))))");
        testParsingAndEval(-3d, "-9.0E1 / 3E1");
        testParsingAndEval(3d, "+3.0");
        testParsingAndEval(-3d, "+-3.0");
        testParsingAndEval(150, "30 * 50 / 10.0");
        testParsingAndEval(Double.NaN, "0 / 0");
        testParsingAndEval(Double.POSITIVE_INFINITY, "100 / 0");
    }

    @Test
    public void testFailures(){
        shouldFailOn("()");
        shouldFailOn("1/");
        shouldFailOn("1 1");
        shouldFailOn("((1)");
        shouldFailOn("(+)");
    }

    private void shouldFailOn(String expression) {
        try {
            evaluator.calculate(expression);
            fail("Expected to fail on " + expression);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void autoGeneratedTests(){
        ExprGenerator gen = new ExprGenerator();
        for (int i = 0; i < 25; i++) {
            ExprGenerator.TestExpression e = gen.generateExpression(i);
            testExprEvaluation(e.getAnswer(), e.getExpression());
        }
    }

    private void testExprEvaluation(double answer, Expression expression){
        double result = 0;
        try {
            result = expression.eval();
            assertEquals(answer, result);
        } catch (CalculationException e) {
            fail("Exception happened " + e + "answer = " + answer + " result = " + result);
        }
    }

    private void testParsingAndEval(double answer, String expr){
        try {
            assertEquals(answer, evaluator.calculate(expr));
        } catch (CalculationException e) {
            fail("Exception happened " + e);
        }
    }
}
