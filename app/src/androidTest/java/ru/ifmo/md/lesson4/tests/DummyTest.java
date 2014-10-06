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

    @Test
    public void simpleIntegerTests() {
        try {
            Assert.assertEquals(11d, CalculationEngineFactory.defaultEngine().calculate("5+6"));
            Assert.assertEquals(-1d, CalculationEngineFactory.defaultEngine().calculate("5-6"));
            Assert.assertEquals(30d, CalculationEngineFactory.defaultEngine().calculate("5*6"));
            Assert.assertEquals(1.2d, CalculationEngineFactory.defaultEngine().calculate("6/5"));
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("-5+5"));
        } catch (Exception e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void simpleDoubleTests() {
        try {
            Assert.assertEquals(12d, CalculationEngineFactory.defaultEngine().calculate("5.33+6.67"), 0.002d);
            Assert.assertEquals(-1d, CalculationEngineFactory.defaultEngine().calculate("5.28-6.28"), 0.002d);
            Assert.assertEquals(36.04d, CalculationEngineFactory.defaultEngine().calculate("5.3*6.8"), 0.002d);
            Assert.assertEquals(1.3d, CalculationEngineFactory.defaultEngine().calculate("6.5/5"), 0.002d);
            Assert.assertEquals(-0.45d, CalculationEngineFactory.defaultEngine().calculate("-5.45+5"), 0.002d);
        } catch (Exception e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void doubleTests() {
        try {
            Assert.assertEquals(4d, CalculationEngineFactory.defaultEngine().calculate("((2+2))-0/(--2)*555"), 0.002d);
            Assert.assertEquals(-8.45d, CalculationEngineFactory.defaultEngine().calculate("---(--(-(3 + 5.5)/-2)+-1) - 5.2"), 0.002d);
            Assert.assertEquals(3901.85926d,CalculationEngineFactory.defaultEngine().calculate("39.548976*98.65892"), 0.000002d);
            Assert.assertEquals(7d,CalculationEngineFactory.defaultEngine().calculate("3.33 + 3.67"), 0.000002d);
            Assert.assertEquals(1d,CalculationEngineFactory.defaultEngine().calculate("1/4 + 3/4"), 0.002d);
        } catch (Exception e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test(expected = CalculationException.class)
    public void divisionByZeroTest() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("5/0");
    }

    @Test
    public void complexTests() {

    }
}
