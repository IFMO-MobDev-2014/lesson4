package ru.ifmo.md.lesson4.tests;

/**
 * Created by vitalik on 06.10.14.
 */
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


public class MyTest {
    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(25d, CalculationEngineFactory.defaultEngine().calculate("(5+5) * 2 + 5"));
            Assert.assertEquals(5d, CalculationEngineFactory.defaultEngine().calculate(".4 + 4.6"));
            Assert.assertEquals(1152d, CalculationEngineFactory.defaultEngine().calculate("(223 + 374 - 21)  / 2 * 4"));
            Assert.assertEquals(3d, CalculationEngineFactory.defaultEngine().calculate("2/2*3"));
            Assert.assertEquals(-2d, CalculationEngineFactory.defaultEngine().calculate("2/ -1"));
            Assert.assertEquals(-253.5d, CalculationEngineFactory.defaultEngine().calculate("-255+(3/2)"));
            Assert.assertEquals(1475637d, CalculationEngineFactory.defaultEngine().calculate("(8873+321) * 321 / 2"));
            Assert.assertEquals(0.5d, CalculationEngineFactory.defaultEngine().calculate("2.5/5"));
            Assert.assertEquals(80d, CalculationEngineFactory.defaultEngine().calculate("200/2.5"));
            Assert.assertEquals(-80d, CalculationEngineFactory.defaultEngine().calculate("200/-2.5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
}
