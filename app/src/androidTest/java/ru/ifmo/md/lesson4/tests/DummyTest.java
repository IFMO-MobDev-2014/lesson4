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
            Assert.assertEquals(10D, CalculationEngineFactory.defaultEngine().calculate("5+5"), 1e-5);
            Assert.assertEquals(999.2D, CalculationEngineFactory.defaultEngine().calculate("5 - (2 + 5) + 1e3 + 1.2"), 1e-5);
            Assert.assertEquals(0.0D, CalculationEngineFactory.defaultEngine().calculate("-5 * (5 + -5)"), 1e-5);
            Assert.assertEquals(1024D, CalculationEngineFactory.defaultEngine().calculate("2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2"), 1e-5);
            Assert.assertEquals(3D / 5, CalculationEngineFactory.defaultEngine().calculate("(1 + (1 + 1)) / (7 - (1 + 1))"), 1e-5);
            Assert.assertEquals(1e6 - 2.39, CalculationEngineFactory.defaultEngine().calculate("1e3 * 1E3 - 239 / 100"), 1e-5);
            Assert.assertEquals(-5D, CalculationEngineFactory.defaultEngine().calculate("-(-(-(-(-5))))"), 1e-5);
            Assert.assertEquals(0.00001/0.00001, CalculationEngineFactory.defaultEngine().calculate("0.00001/0.00001"), 1e-5);
            try {
                double x = CalculationEngineFactory.defaultEngine().calculate("(1 + (1 + 1)) / (7 - (1 + 1)");
                Assert.fail("Fail to fail :(");
            } catch (CalculationException e) {

            }
            try {
                double x = CalculationEngineFactory.defaultEngine().calculate("1ee3");
                Assert.fail("Fail to fail :(");
            } catch (CalculationException e) {

            }
            try {
                double x = CalculationEngineFactory.defaultEngine().calculate("1.0 - 5.a");
                Assert.fail("Fail to fail :(");
            } catch (CalculationException e) {

            }
            try {
                double x = CalculationEngineFactory.defaultEngine().calculate("5.0 + 0.+");
                Assert.fail("Fail to fail :(");
            } catch (CalculationException e) {

            }
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
}
