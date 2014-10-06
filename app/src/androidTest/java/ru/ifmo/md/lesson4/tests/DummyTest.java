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

    public void fail(String expr) {
        try{
            CalculationEngineFactory.defaultEngine().calculate(expr);
            Assert.fail();
        } catch (CalculationException e) {
        }
    }

    @Test
    public void failing(){
            fail("-");
            fail("2-2-");
            fail("(2+3)-(1+3");
            fail("3++1");
            fail("text");
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
            Assert.assertEquals(.1*.2*0.3, CalculationEngineFactory.defaultEngine().calculate(".1*.2*0.3"));
            Assert.assertEquals(8.-.8, CalculationEngineFactory.defaultEngine().calculate("8.-.8"));
            Assert.assertEquals(11d, CalculationEngineFactory.defaultEngine().calculate("8----3"));
            Assert.assertEquals(0d/0d, CalculationEngineFactory.defaultEngine().calculate("0/0"));
            Assert.assertEquals(10d/0d, CalculationEngineFactory.defaultEngine().calculate("10/0"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
}
