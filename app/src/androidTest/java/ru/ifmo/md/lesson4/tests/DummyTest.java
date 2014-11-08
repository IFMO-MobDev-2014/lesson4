package ru.ifmo.md.lesson4.tests;

import android.util.Pair;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;
import ru.ifmo.md.lesson4.TestGen;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    private static final double eps = 1E-8;
    CalculationEngine myEngine;

    @Before
    public void setup() {
        myEngine = CalculationEngineFactory.defaultEngine();
        //do whatever is necessary before every test

    }

    private void assertEquals(double ans, String expression) {
        try {
            double x = myEngine.calculate(expression);
            Assert.assertTrue(expression + " should be equals to " + ans
                    , Double.isNaN(x) && Double.isNaN(ans)
                    || (Double.isInfinite(x) && Double.isInfinite(ans) && x == ans)
                    || Math.abs(x - ans) < eps);
        } catch (CalculationException e) {
            System.out.println("not failed on:" + e);
            Assert.fail("Shouldn't fail on " + expression + " with " + e);
        }
    }

    private void assertFail(String expression) {
        try {
            myEngine.calculate(expression);
            Assert.fail("Should fail on: " + expression + " but it doesn't want to do it");
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void simpleTests() {
        assertEquals(0d, "5 - 5");
        assertEquals(5d, "----5");
        assertEquals(Double.POSITIVE_INFINITY, "5/0");
        assertEquals(5d, "((((((((((((((((((((((((((5))))))))))))))))))))))))))");
        //assertFail("0 0"); spaces are impossible in the app
        assertFail("()");//impossible in the app
        assertFail("((");
        assertFail("(+)");//impossible in the app
        assertFail("(");
        assertFail("(-3)(-3)");//impossible in the app
        assertEquals(3d, "--3");
        assertFail("-(3)-");//impossible in the app
        assertFail("-3-3,0");//impossible in the app
        assertFail("-3(+2)");//impossible in the app
        assertFail("*7*(3+3)");//impossible in the app
        assertFail(") + (3)");//impossible in the app
        assertFail("(7+(7+3)");
        assertFail("9-");
        assertEquals(7d, "-(+(-(+7)))");
        assertEquals(-0.2d, "1/-5");
        assertFail("(6)(8)");//impossible in the app
        assertFail("1-1(4)");
        assertEquals(-236.4660704834d, "72.0/532*729*177/699-267.0*471/481");
        assertEquals(-789.988715277778, "-(+(-18.0/36*(10.0+13.0/36/16))+(-21.0/7*24+21.0*41+6.0))");
        assertEquals(Double.NEGATIVE_INFINITY, "-25/(21-7*3)");
        assertFail("3)");//impossible in the app
        assertFail("(3");
        assertEquals(6.9d, "7-.1");
        assertFail("7.0.0");
        assertEquals(0, "-3-(-3)");
        assertEquals(-8, "-(-(-8))");
        assertFail("(-.-)");//impossible in the app
        assertFail(".");
        assertEquals(-3, "-3");
        assertEquals(3, "+3");
        assertEquals(9, "9.");
    }

    @Test
    public void lotsOfRandomTests() {
        TestGen gen = new TestGen();
        Pair<Double, String> test;
        for (int i = 0; i < 100; i++) {
            test = gen.nextTest();
            assertEquals(test.first, test.second);
        }
    }
}
