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
    final double DELTA = 1e-9;

    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    public void assertDoubleEquals(String expression, double expected) {
        try {
            double res = CalculationEngineFactory.defaultEngine().calculate(expression);
            Assert.assertEquals(res, expected, DELTA);
        } catch(CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    public void assertException(String expression) {
        try {
            double res = CalculationEngineFactory.defaultEngine().calculate(expression);
            Assert.fail("No exception happened");
        } catch(Exception e) {
        }
    }

    @Test
    public void testArithmetics() {
        assertDoubleEquals("5+5", 10);
        assertDoubleEquals("3.4-4.3", 3.4 - 4.3);
        assertDoubleEquals("123.456*789.101", 123.456 * 789.101);
        assertDoubleEquals("456.789/10.12", 456.789 / 10.12);
        assertDoubleEquals("(2 + 2.2) * (2 + (2 + 2.2)) / 3.4 - 9.123456", (2 + 2.2) * (2 + (2 + 2.2)) / 3.4 - 9.123456);
    }

    @Test
    public void testParseErrors() {
        assertException("(");
        assertException(")");
        assertException("1.2.3");
        assertException("-");
        assertException("/");
        assertException("+");
        assertException(")1");
        assertException("1+");
    }


}
