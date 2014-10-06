package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

//Sergey Budkov 2536

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    public void failing(String expr) {
        try{
            CalculationEngineFactory.defaultEngine().calculate(expr);
            Assert.fail();
        } catch (CalculationException e) {
        }
    }

    @Test
    public void failTests(){
        failing("");
        failing("/**");
        failing("12+-54+-");
        failing("1/7/8.9.9");
        failing("isEmpty");
        failing("(3/((45))");
        failing("*--*8");
        failing("65..65/65");
        failing("((((((78-98))()))");
        failing("78/78/78*.*78/78/78");
    }

    @Test
    public void rightTests() {
        try {
            Assert.assertEquals(1d, CalculationEngineFactory.defaultEngine().calculate("6-7+2"));
            Assert.assertEquals(231d, CalculationEngineFactory.defaultEngine().calculate("----231"));
            Assert.assertEquals(56d*59d/62.65d+(68d/71d), CalculationEngineFactory.defaultEngine().calculate("56*59/62.65+(68/71)"));
            Assert.assertEquals(4.2+1.3/1.8, CalculationEngineFactory.defaultEngine().calculate("4.2+1.3/1.8"));
            Assert.assertEquals(0.1*0.2+0.3-(-8), CalculationEngineFactory.defaultEngine().calculate("0.1*0.2+0.3--8"));
            CalculationEngineFactory.defaultEngine().calculate("5+5");
            CalculationEngineFactory.defaultEngine().calculate("5+0.1");
            CalculationEngineFactory.defaultEngine().calculate("--2");
            CalculationEngineFactory.defaultEngine().calculate("(5+5)/(2-1)");
            CalculationEngineFactory.defaultEngine().calculate("3*4/0.47775");
        } catch (CalculationException e){
            Assert.fail();
        }
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
