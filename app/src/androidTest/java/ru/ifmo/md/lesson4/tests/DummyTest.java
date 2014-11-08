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


//@Config(emulateSdk = 18)
//@RunWith(RobolectricTestRunner.class)
    //@Before
public class DummyTest {
    final static private double EPS = 1e-3;


    @Test
    public void testWhoppingComplex() {
        Assert.assertEquals(1,1);
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("-5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(11d, CalculationEngineFactory.defaultEngine().calculate("6+(--5)"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(-0.0, CalculationEngineFactory.defaultEngine().calculate("5*(-0)"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(8d, CalculationEngineFactory.defaultEngine().calculate("2+2*2+2"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("(2 + 2)*2+2"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(true,
              Math.abs(CalculationEngineFactory.defaultEngine().calculate("(2.1111 / 2.2222)*(-1123*2)+11.11")
                      - (-2122.6001071)) < EPS);
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
}
