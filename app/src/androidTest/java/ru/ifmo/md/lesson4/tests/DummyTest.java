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
    private CalculationEngine engine = null;

    @Before
    public void setup() {
        //do whatever is necessary before every test
        engine = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testWhoppingComplex() {
        try {
            Assert.assertEquals(10d, engine.calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void testEqual() {
        try {
            Assert.assertEquals(engine.calculate("1.*5-(7-783787*9.3)/88.4+.3*100.1"), 82492.18045248868d);
            Assert.assertEquals(engine.calculate(" 7.*9./7*(4545*(34-5-5-5+1-1)/1./1.0/(.1*10.0))"), 777195d);
            Assert.assertEquals(engine.calculate("2*2*2*2*2*2*2*4*0.5*4"), 1024d);
            Assert.assertEquals(engine.calculate("((((((((((((6*6)-1)-2)-0.1)*5)*10.01)/1))))))"), 1646.645d);
            Assert.assertEquals(engine.calculate("7.1*7.1/5/5/5/5/5/5/5/5/5*10000"), 0.2580992d);
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    private void checkOneError(String expression) {
        try {
            engine.calculate(expression);
            Assert.fail("It must be failed!");
        } catch (CalculationException e) {
        }
    }

    @Test
    public void checkError() {
        checkOneError("...");
        checkOneError("1.1.1.");
        checkOneError("ddd");
        checkOneError("1+*");
        checkOneError("2+*5");
        checkOneError("+");
        checkOneError("*");
        checkOneError("(");
        checkOneError(")");
        checkOneError("()");
        checkOneError("((5)))");
        checkOneError("1*(5))");
    }
}
