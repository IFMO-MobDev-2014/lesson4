package ru.ifmo.md.lesson4.tests;

import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AcceptanceTests {

    private static final double EPS = 0.0001;

    private static void testInvalidExpression(String expression) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expression);
            Assert.fail("Successfully evaluate " + expression);
        } catch (CalculationException e) {
            Log.d("Test", "Successfully fail");
        }
    }

    private static void assertEquals(double expected, double actual) {
        Assert.assertTrue(Math.abs(expected - actual) < EPS);
    }

    private static void testValidExpression(double expected, String expression) {
        try {
            double result = CalculationEngineFactory.defaultEngine().calculate(expression);
            assertEquals(expected, result);
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void testInvalidExpression1() {
        testInvalidExpression(")");
    }

    @Test
    public void testInvalidExpression2() {
        testInvalidExpression("(");
    }

    @Test
    public void testInvalidExpression3() {
        testInvalidExpression("()");
    }

    @Test
    public void testInvalidExpression4() {
        testInvalidExpression("-");
    }

    @Test
    public void testInvalidExpression5() {
        testInvalidExpression("1//5");
    }

    @Test
    public void testInvalidExpression6() {
        testInvalidExpression("");
    }

    @Test
    public void testInvalidExpression7() {
        testInvalidExpression("1+1+1/");
    }

    @Test
    public void testInvalidExpression8() {
        testInvalidExpression("1+1+()");
    }

    @Test
    public void testValidExpression1() {
        testValidExpression(2, "1+1");
    }

    @Test
    public void testValidExpression2() {
        testValidExpression(1.05, "(0.1+0.005)/0.1");
    }


    @Test
    public void testValidExpression3() {
        testValidExpression(0, "((0.1+0.005)*0.0)");
    }

    @Test
    public void testValidExpression4() {
        testValidExpression(0.1, "(0.1+0.005*0.0)");
    }

    @Test
    public void testValidExpression5() {
        testValidExpression(1, "1");
    }

    @Test
    public void testValidExpression6() {
        testValidExpression(-1, "-1");
    }

    @Test
    public void testValidExpression7() {
        testValidExpression(42, "(((42)))");
    }

    @Test
    public void testValidExpression8() {
        testValidExpression(-42, "((-(42)))");
    }

    @Test
    public void testValidExpression9() {
        testValidExpression(0.2, "1/1/5");
    }

    @Test
    public void testValidExpression10() {
        testValidExpression(50, "10/(1/5)");
    }

    @Test
    public void testValidExpression11() {
        testValidExpression(1.05, "(0.1+0.005)/0.1");
    }


    @Test
    public void testDivisionByZero1() {
        try {
            double result = CalculationEngineFactory.defaultEngine().calculate("-1/0");
            Assert.assertEquals(Double.NEGATIVE_INFINITY, result);
        } catch (CalculationException e) {
            Log.d("Test", "Valid Failure");
        }
    }

    @Test
    public void testDivisionByZero2() {
        try {
            double result = CalculationEngineFactory.defaultEngine().calculate("1/(10-10)");
            Assert.assertEquals(Double.POSITIVE_INFINITY, result);
        } catch (CalculationException e) {
            Log.d("Test", "Valid Failure");
        }
    }

    @Test
    public void testDivisionByZero3() {
        try {
            double result = CalculationEngineFactory.defaultEngine().calculate("(10-10)/(10-10)");
            Assert.assertTrue(Double.isNaN(result));
        } catch (CalculationException e) {
            Log.d("Test", "Valid Failure");
        }
    }
}