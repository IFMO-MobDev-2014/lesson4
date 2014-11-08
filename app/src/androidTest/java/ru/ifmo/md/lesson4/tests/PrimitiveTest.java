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
public class PrimitiveTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(100d, CalculationEngineFactory.defaultEngine().calculate("25*4"));
            Assert.assertEquals(30d, CalculationEngineFactory.defaultEngine().calculate("180/6"));
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("2.5*0.4"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("100/0"));
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("10..5/7"));
            Assert.fail("Exception not caught!");
        } catch (CalculationException ignored) {
        }
    }
}