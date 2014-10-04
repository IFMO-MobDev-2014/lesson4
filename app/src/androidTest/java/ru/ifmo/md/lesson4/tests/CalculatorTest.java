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
public class CalculatorTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void twoPoints() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("0..");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void closingBracket() {
        try {
            CalculationEngineFactory.defaultEngine().calculate(")");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void manySignes() {
        try {
            Assert.assertEquals(-56.,CalculationEngineFactory.defaultEngine().calculate("-------++++++++56"));
        } catch (CalculationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void manyOperations() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("*+");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void manyBrackets() {
        try {
            Assert.assertEquals(54.,CalculationEngineFactory.defaultEngine().calculate("(5+(-3))*(24---(-3))"));
        } catch (CalculationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wrongMult() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("*3+(7-3)");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void wrongDiv() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("/3+(7-3)");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void brackets() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("()");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void bracketsWithPlus() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("(+)");
            Assert.assertEquals(false, true);
        } catch (CalculationException ignored) {
        }
    }

    @Test
    public void silentMult() {
        try {
            Assert.assertEquals(105.,  CalculationEngineFactory.defaultEngine().calculate("(35)(3)"));
        } catch (CalculationException ignored) {
        }
    }

}
