package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RecursiveCalculationTest {
    private static final CalculationEngine calculator = CalculationEngineFactory.defaultEngine();
    private static final Random rnd = new Random();
    private static final double eps = 1e-6;

    private void expectSuccess(double expectedResult, String testingExpression) {
        try {
            Assert.assertEquals(expectedResult, calculator.calculate(testingExpression), eps);
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    private void expectCalculatorException(String testingExpression) {
        boolean exceptionHappened = false;
        try {
            calculator.calculate(testingExpression);
        } catch (CalculationException e) {
            exceptionHappened = true;
        }
        if (!exceptionHappened) {
            Assert.fail("Expected calculation exception");
        }
    }

    @Test
    public void testSimpleOperations() {
        expectSuccess(10d, "5+5");
        expectSuccess(0d, "5-5");
        expectSuccess(25d, "5*5");
        expectSuccess(1d, "5/5");
        expectSuccess(3125d, "5^5");
        expectSuccess(-5d, "-5");
        expectSuccess(5d, "--5");
    }

    @Test
    public void testException() {
        expectCalculatorException("5 5");
        expectCalculatorException("5&5");
        expectCalculatorException("/5");
        expectCalculatorException("*5");
        expectCalculatorException("5$");
        expectCalculatorException("5/ a");
        expectCalculatorException("198247.1928.1");
        expectCalculatorException("7f9");
        expectCalculatorException("((");
        expectCalculatorException("()");
        expectCalculatorException("-");
        expectCalculatorException("3,0");
        expectCalculatorException("876-");
        expectCalculatorException("-.");
        expectCalculatorException(".");
        expectCalculatorException("i'm better than tourist");
    }

    @Test
    public void testOperationsAssociativity() {
        expectSuccess(5d, "5*5/5");
        expectSuccess(5d, "5/5*5");
        expectSuccess(256d, "2^2^3");
    }

    @Test
    public void testWhitespaces() {
        expectSuccess(234d, "            234         ");
        expectSuccess(4569456d, "4569456           ");
        expectSuccess(27354237d, "             27354237");
    }

    @Test
    public void testOperationsMixed() {
        expectSuccess(30d, "5*5+5");
        expectSuccess(30d, "5+5*5");
        expectSuccess(20d, "5*5-5");
        expectSuccess(-20d, "5-5*5");
        expectSuccess(5d, "5*5/5");
        expectSuccess(5d, "5/5*5");
        expectSuccess(15625d, "5*5^5");
        expectSuccess(15625d, "5^5*5");
    }

    @Test
    public void testFloatNumbers() {
        expectSuccess(5.0398d, "5.0398");
        expectSuccess(0.398d, "0.398");
        expectSuccess(0d, "00000000000000.000000");
        expectSuccess(1d, "000000000000001.00000000");
        expectSuccess(9d, "9.");
        expectSuccess(70d, "70.0");
        expectSuccess(.07d, ".07");
    }

    @Test
    public void testFloatOperations() {
        expectSuccess(6.5d, "13/2");
        expectSuccess(0.5d, "0.25*2");
        expectSuccess(0.25d, "0.5^2");
        expectSuccess(4.5d, "5-0.5");
        expectSuccess(0.08, "4/50");
        expectSuccess(0.6, "1-2/5");
    }

    @Test
    public void testBigExpressions() {
        expectSuccess(1000d, "2-38+47*23-45");
        expectSuccess(49792d, "(7-3)*4*389*(5+3)");
        expectSuccess(131072d, "2^(3-(1-2)+8*2-(6-3))");
        expectSuccess(16d, "2^2+2^2+2^2+2^2");
    }

    @Test
    public void testLotOfBrackets() throws Exception {
        final int bracketsCount = 1000;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bracketsCount; i++) {
            sb.append("(");
        }
        sb.append("2+2");
        for (int i = 0; i < bracketsCount; i++) {
            sb.append(")");
        }
        expectSuccess(4d, sb.toString());
    }

    @Test
    public void testVeryBigRandomExpressions() {
        final int tests = 10;
        for (int test = 0; test < tests; test++) {
            final int numbersCount = 1000;
            ArrayList<Integer> numbers = new ArrayList<>();
            ArrayList<Integer> revNumbers = new ArrayList<>();
            for (int i = 0; i < numbersCount; i++) {
                int t = rnd.nextInt(100) + 1;
                numbers.add(t);
                revNumbers.add(t);
            }
            Collections.shuffle(revNumbers);
            StringBuilder expressionString = new StringBuilder();
            expressionString.append(numbers.get(0)).append("/").append(revNumbers.get(0));
            for (int i = 1; i < numbersCount; i++) {
                expressionString.append("*").append(numbers.get(i)).append("/").append(revNumbers.get(i));
            }
            expectSuccess(1d, expressionString.toString());
        }
    }
}
