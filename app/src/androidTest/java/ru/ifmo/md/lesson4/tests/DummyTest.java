package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.engine.CalculationEngine;
import ru.ifmo.md.lesson4.engine.CalculationEngineFactory;
import ru.ifmo.md.lesson4.exceptions.CalculationException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    private static final double eps = 1e-7;

    private CalculationEngine engine;

    private void assertDoubleEquals(double a, double b) {
        assertTrue(Math.abs(a - b) < eps);
    }

    private void assertFail(String s) {
        try {
            engine.calculate(s);
        } catch (CalculationException e) {
            return;
        } catch (Exception e) {
            fail("CalculationException didn't appeared!");
        }

        fail("CalculationException didn't appeared!");
    }

    public void assertCalculationIsRight(String s, double result) {
        try {
            assertDoubleEquals(engine.calculate(s), result);
        } catch (Exception e) {
            fail("Calculation error!");
        }
    }

    @Before
    public void setup() {
        //do whatever is necessary before every test
        engine = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testWork() throws Exception {
        assertCalculationIsRight("1+2+3+4", 10);
        assertCalculationIsRight("1/2", 0.5);
        assertCalculationIsRight("(1+2+3/2+3/2)*2.5", 15);
    }

    @Test
    public void testParsing() throws Exception {
        assertFail("-");
        assertFail("293182d");
        assertFail("32+(2+3))");
        assertFail(")2 + 3");
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
}
