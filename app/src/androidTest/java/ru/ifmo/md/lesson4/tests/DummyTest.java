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
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("0"));
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("0+ 1"));
            Assert.assertEquals(2d, CalculationEngineFactory.defaultEngine().calculate("--2"));
            Assert.assertEquals(4d, CalculationEngineFactory.defaultEngine().calculate("2  *2"));
            Assert.assertEquals(8d, CalculationEngineFactory.defaultEngine().calculate("2/0.25"));
            Assert.assertEquals(16d, CalculationEngineFactory.defaultEngine().calculate("16+ (3/2 - 0.05 *100/10 + -(-(-1)))"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened");
        }
        try {
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("1 / 0)"));
            Assert.fail("Exception didn't happen");
        } catch (CalculationException ignored) {
        }
    }
}
