package ru.ifmo.md.lesson4.tests;

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
public class CalculatorTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    private void assertException(String s) {
        boolean exceptionHappened = false;
        try {
            CalculationEngineFactory.defaultEngine().calculate(s);
        } catch (CalculationException e) {
            exceptionHappened = true;
        }
        if (!exceptionHappened) {
            Assert.fail("An exception expected");
        }
    }

    @Test
    public void testBasics() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
            Assert.assertEquals(-10d, CalculationEngineFactory.defaultEngine().calculate("-5-5"));
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("--(5+5)"));
            Assert.assertEquals(Double.POSITIVE_INFINITY, CalculationEngineFactory.defaultEngine().calculate("1/0"));
            Assert.assertEquals(Double.NEGATIVE_INFINITY, CalculationEngineFactory.defaultEngine().calculate("-1/0"));
            Assert.assertEquals(Double.NaN, CalculationEngineFactory.defaultEngine().calculate("1/0-1/0"));
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("10*10/10*10/10"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
    @Test
    public void testFain() {
        assertException("London is the capital of Great Britain");
        assertException("*");
        assertException("/");
        assertException("+");
        assertException("-");
        assertException("a+b");
        assertException("()");
        assertException(")))))))000))00))00))00))00000");
        assertException("((((((999((99((99((99(((9999999999");
    }
}
