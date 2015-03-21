package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.example.alex.lesson4.CalculationEngineFactory;
import com.example.alex.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class NormalTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testInteger() {
        try {
            Assert.assertEquals(352.9d, CalculationEngineFactory.defaultEngine().calculate("(5/6+2-8+13*(18/5+6)-2)*3"));
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("1/2*3/4*5/6*7/8*9/9*8/7*6/5*4/3*2/1"));
            Assert.assertEquals(5d, CalculationEngineFactory.defaultEngine().calculate("-1*-5"));
            Assert.assertEquals(21d, CalculationEngineFactory.defaultEngine().calculate("-1*(13+8)*-1"));
            Assert.assertEquals(-123456d, CalculationEngineFactory.defaultEngine().calculate("-123456+123456+123456*(-123456/123456)"));
            Assert.assertEquals(777777777d, CalculationEngineFactory.defaultEngine().calculate("12345679*63"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void testDouble() {
        try {
            Assert.assertEquals(135802467.9d, CalculationEngineFactory.defaultEngine().calculate("1.1*123456789"));
            Assert.assertEquals(0.0000011d, CalculationEngineFactory.defaultEngine().calculate("0.0000001*(0.011*1000)"));
            Assert.assertEquals(0.00017672413d, CalculationEngineFactory.defaultEngine().calculate("0.328/1856"), 0.00000000001d);
            Assert.assertEquals(-0.1d, CalculationEngineFactory.defaultEngine().calculate("-0.1+0.1+0.1*(-0.1/0.1)"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test (expected = CalculationException.class)
    public void testDBZ() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("1/(-1+1)");
    }
}
