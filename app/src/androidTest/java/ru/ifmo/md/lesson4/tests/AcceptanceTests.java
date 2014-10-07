package ru.ifmo.md.lesson4.tests;

import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AcceptanceTests {

    private static void testInvalidExpression(String expression) {
        try {
            CalculationEngineFactory.defaultEngine().calculate(expression);
            Assert.fail("Successfully evaluate " + expression);
        } catch (CalculationException e) {
            Log.d("Test", "Successfully fail");
        }
    }

    @Test
    public void testDivisionByNegative() {
        try {
            Assert.assertEquals(-0.2, CalculationEngineFactory.defaultEngine().calculate("1/-5"));
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
        testInvalidExpression("-----5");
    }

    @Test
    public void testInvalidExpression6() {
        testInvalidExpression("1//5");
    }

    @Test
    public void testInvalidExpression7() {
        testInvalidExpression("1+1+1/");
    }

    @Test
    public void testInvalidExpression8() {
        testInvalidExpression("");
    }
}