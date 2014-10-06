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
            Assert.assertEquals(5d, CalculationEngineFactory.defaultEngine().calculate("5"));
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("5-5"));
            Assert.assertEquals(25d, CalculationEngineFactory.defaultEngine().calculate("5*5"));
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("5/5"));
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("5*0"));
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("0*5"));
            Assert.assertEquals(25d, CalculationEngineFactory.defaultEngine().calculate("(5*5)"));
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("(100-100)/1"));
            Assert.assertEquals(100d, CalculationEngineFactory.defaultEngine().calculate("(100-99)*(100)"));
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("(100-99+1-1.0)/1.0"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
}
