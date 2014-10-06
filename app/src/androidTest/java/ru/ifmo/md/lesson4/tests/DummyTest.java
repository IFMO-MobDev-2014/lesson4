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
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }


    private void testEq(double expected, String expression) {
        try {
            Assert.assertEquals(expected, CalculationEngineFactory.defaultEngine().calculate(expression));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e + " for expression: " + e.toString());
        }
    }

    @Test
    public void simpleTests() {
        testEq(2d, "1+1");
        testEq(3d, "1.+2");
        testEq(0d, "-1+1");
        testEq(6d, "2+2*2");
        testEq(6d, "15-(3*3)");
        testEq(12d, "2*2+24/3");
        testEq(100d, "100000/1000");
    }
}
