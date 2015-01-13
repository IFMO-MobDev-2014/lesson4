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
public class ToughTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testSomeExpressions() {
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
            Assert.assertEquals(1234d, CalculationEngineFactory.defaultEngine().calculate("((((   10 ) * (5 + (5))) * 10 + 5*4    *10  + 68/2))"));
            Assert.assertEquals(0.0005, CalculationEngineFactory.defaultEngine().calculate("+(13-3)/2/10*0.1/(1/0.01)"));
            Assert.assertEquals(1E18, CalculationEngineFactory.defaultEngine().calculate("10*10*10*10*10*10*10*10*10*10*10*10*10*10*10*10*10*10"));
            Assert.assertEquals(5d, CalculationEngineFactory.defaultEngine().calculate("++++++(++++(--(+++(((((-((-+5))))))))))"));
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("12341234123513418797986123948127346912348712364712347917264-12341234123513418797986123948127346912348712364712347917264"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test(expected = CalculationException.class)
    public void testException1() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("()");
    }

    @Test(expected = CalculationException.class)
    public void testException2() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("1+4*3+");
    }

    @Test(expected = CalculationException.class)
    public void testException3() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("--(5-4)+*2");
    }

    @Test(expected = CalculationException.class)
    public void testException4() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("--(5-4)+2*(23");
    }

    @Test(expected = CalculationException.class)
    public void testException5() throws CalculationException {
        CalculationEngineFactory.defaultEngine().calculate("421341234.1234e12.243+112");
    }
}
