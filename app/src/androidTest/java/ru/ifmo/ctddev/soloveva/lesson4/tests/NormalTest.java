package ru.ifmo.ctddev.soloveva.lesson4.tests;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

/**
 * Created by maria on 06.10.14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class NormalTest {
    private final CalculationEngine engine = CalculationEngineFactory.defaultEngine();

    @Test
    public void testAddition() throws CalculationException {
        Assert.assertEquals(10d, engine.calculate("5+5"));
    }

    @Test
    public void testSubtraction() throws CalculationException {
        Assert.assertEquals(5d, engine.calculate("10-5"));
    }

    @Test
    public void testUnaryMinus() throws CalculationException {
        Assert.assertEquals(5d, engine.calculate("-5+10"));
    }

    @Test
    public void testUnaryPlus() throws CalculationException {
        Assert.assertEquals(5d, engine.calculate("+10-5"));
    }

    @Test
    public void testProduct() throws CalculationException {
        Assert.assertEquals(25d, engine.calculate("5*5"));
    }

    @Test
    public void testDivision() throws CalculationException {
        Assert.assertEquals(5d, engine.calculate("25/5"));
    }

    @Test
    public void testComplex() throws CalculationException {
        Assert.assertEquals(-2d, engine.calculate("1+2*(4-3)-5/(7-6)"));
    }

    @Test
    public void testBrackets() throws CalculationException {
        Assert.assertEquals(2d, engine.calculate("((1)+(2*(3))-(((5))))"));
    }

    @Test(expected = CalculationException.class)
    public void testInvalid1() throws CalculationException {
        engine.calculate("2+3*");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid2() throws CalculationException {
        engine.calculate("2+3(4-2)");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid3() throws CalculationException {
        engine.calculate("(2+3");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid4() throws CalculationException {
        engine.calculate("2-2*(");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid5() throws CalculationException {
        engine.calculate("2-2*(3-2");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid6() throws CalculationException {
        engine.calculate(")1+2");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid7() throws CalculationException {
        engine.calculate("1+2)");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid8() throws CalculationException {
        engine.calculate("1+2)-1");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid9() throws CalculationException {
        engine.calculate("*1+2");
    }

    @Test(expected = CalculationException.class)
    public void testInvalid10() throws CalculationException {
        engine.calculate("1++2");
    }
}
