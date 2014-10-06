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
    public void bigTest() {
        try {
            Assert.assertEquals(33609.9, CalculationEngineFactory.defaultEngine().calculate("33333+53*(3-5)-15928.5/777+1+2+3-5+2/5+777+3*(1-(3+4*(7+8))*2)"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void manyUnparsableBrackets() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("5-(((((");
            Assert.assertEquals(false, true);
        } catch (Exception ignored) {
        }
    }

    @Test
    public void divisionByZero() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("53-(6+3/0)");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void usualTest_1() {
        try {
            Assert.assertEquals(11810d, CalculationEngineFactory.defaultEngine().calculate("12345+(6*(7-3)-4)-555"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void usualTest_2() {
        try {
            Assert.assertEquals(33d, CalculationEngineFactory.defaultEngine().calculate("(2)+(3)+(4)*7"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void usualTest_3() {
        try {
            Assert.assertEquals(-1922.967, CalculationEngineFactory.defaultEngine().calculate("2.7-553.53*3.9-1+234.1"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void usualTest_4() {
        try {
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("1+0"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void usualTest_5() {
        try {
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("1-1"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

}
