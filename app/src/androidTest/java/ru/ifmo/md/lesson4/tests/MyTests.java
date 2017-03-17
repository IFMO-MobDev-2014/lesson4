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
/**
 * Created by siziyman on 06.10.2014.
 */
public class MyTests {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(5d, CalculationEngineFactory.defaultEngine().calculate("5 / (0 + 1))"));
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("0 / 123121"));
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("111 * ((123123.111 + 321321.333) / (222222.222 + 222222.222)) / 111"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
        try {
            Assert.assertEquals(123d, CalculationEngineFactory.defaultEngine().calculate("1 / 0"));
            Assert.fail("Exception DIDN'T happen");
        } catch (CalculationException ignored) {
        }
    }
}
