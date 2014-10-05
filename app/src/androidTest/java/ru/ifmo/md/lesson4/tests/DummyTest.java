package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    CalculationEngine calculationEngine;
    Random RNG;

    @Before
    public void setup() {
        calculationEngine = CalculationEngineFactory.defaultEngine();
        RNG = new Random(31L);
    }

    @Test
    public void testWhoppingComplex() {
        testExpressionEquals("1+2", 3d);
        testExpressionEquals("(1+2)+4", 7d);
        testExpressionEquals("-(1)+2", 1d);
        testExpressionEquals("1*3", 3d);
        testExpressionEquals("(5+3)*2", 16d);
        testExpressionEquals("2+2*2", 6d);
        testExpressionEquals("4-(-10)", 14d);
        testExpressionEquals("3*(-5)", -15d);
        testExpressionEquals("3-5*(-2)", 13d);
        testExpressionEquals("1-(-3)", 4d);

        testExpressionFail("(");
        testExpressionFail("1/0");
        testExpressionFail("2/0");
        testExpressionFail("*1");
        testExpressionFail("13(3)");
        testExpressionFail("()");
        testExpressionFail("(0+)");
        testExpressionFail("0/0");
        testExpressionFail("3//3");

        testRandomValues();
    }

    private void testExpressionEquals(String description, double expected) {
        try {
            double actual = calculationEngine.calculate(description);
            Assert.assertEquals(expected, actual);
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    private void testExpressionFail(String description) {
        boolean exceptionHappened = false;
        try {
            calculationEngine.calculate(description);
        } catch (CalculationException e) {
            exceptionHappened = true;
        } finally {
            if (!exceptionHappened)
                Assert.fail("It must fail on test " + description);
        }
    }

    private void testRandomValues() {
        String templateExpr = "-(x+(0.2)-x*2)/ 3 * (2-3.1)";
        for (int i = 0; i < 100; i++) {
            double x = RNG.nextDouble();
            double expected = -(x+(0.2)-x*2)/ 3 * (2-3.1);
            String description = templateExpr.replace("x", String.valueOf(x));
            testExpressionEquals(description, expected);
        }
    }
}
