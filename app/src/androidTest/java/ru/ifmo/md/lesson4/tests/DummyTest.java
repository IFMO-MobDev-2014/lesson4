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

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    private CalculationEngine engine;

    protected void assertFail(String expr) {
        try {
            engine.calculate(expr);
            Assert.fail("not failed on " + expr);
        } catch (CalculationException e) {
        }
    }

    protected void assertEqual(String expr, double result) {
        try {
            if (Math.abs(engine.calculate(expr) - result) < 1.E-9) {
                return;
            }
        } catch (CalculationException e) {
        }
        Assert.fail(expr + " != " + result);
    }

    @Before
    public void setup() {
        //do whatever is necessary before every test
        engine = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void testParser() {
        assertFail("0 0");
        assertFail("((");
        assertFail("()");
        assertFail("(+)");
        assertFail("(");
        assertFail("(-3)(-3)");
        assertFail("-(3)-");
        assertFail("-3-3,0");
        assertFail("-3(+2)");
        assertFail("*7*(3+3)");
        assertFail(") + (3)");
        assertFail("(7+(7+3)");
        assertFail("9-");
        assertFail("(6)(8)");
        assertFail("1-1(4)");
        assertFail("7.0.0");
        assertFail("(");
        assertFail("(-.-)");
        assertFail(".");

        assertEqual("-(+(-(+7)))", 7.);
        assertEqual("1/-5", -0.2);
        assertEqual("72.0/532*729*177/699-267.0*471/481",
                72.0 / 532 * 729 * 177 / 699 - 267.0 * 471 / 481);
        assertEqual("-(+(-18.0/36*(10.0+13.0/36/16))+(-21.0/7*24+21.0*41+6.0))",
                -(+(-18.0 / 36 * (10.0 + 13.0 / 36 / 16)) + (-21.0 / 7 * 24 + 21.0 * 41 + 6.0)));

    }

}