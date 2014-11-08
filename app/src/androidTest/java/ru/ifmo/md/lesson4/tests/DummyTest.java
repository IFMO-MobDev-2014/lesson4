package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.Calculation.DBZException;
import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {
    CalculationEngine calc;
    @Before
    public void setup() {
        calc = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testSumPosPos() {
        try{
            Assert.assertEquals(100d, calc.calculate("30 + 70"));
        }catch (CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSumPosNeg() {
        try{
            Assert.assertEquals(-40d, calc.calculate("30 + (-70)"));
        }catch (CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSumNegNeg() {
        try{
            Assert.assertEquals(-100d, calc.calculate("(-30) + (-70)"));
        }catch (CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSubtractPosPos() {
        try {
            Assert.assertEquals(-10d, calc.calculate("40 - 50"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSubtractPosNeg() {
        try {
            Assert.assertEquals(90d, calc.calculate("40 - (-50)"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testSubtractNegNeg() {
        try {
            Assert.assertEquals(10d, calc.calculate("(-40) - (-50)"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testMultiplyPosPos() {
        try{
            Assert.assertEquals(504d, calc.calculate("56*9"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testMultiplyPosNeg() {
        try{
            Assert.assertEquals(-504d, calc.calculate("56*(-9)"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testMultiplyNegNeg() {
        try{
            Assert.assertEquals(504d, calc.calculate("(-56)*(-9)"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testDividePosPos() {
        try{
            Assert.assertEquals(68.25d, calc.calculate("546/8"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testDividePosNeg() {
        try{
            Assert.assertEquals(-68.25d, calc.calculate("(-546)/8"));
        }catch(CalculationException e) {
            Assert.fail("Error" + e.toString());
        }
    }

    @Test
    public void testDivideByZero() {
        boolean b = false;
        try{
            Assert.assertEquals(1d, calc.calculate("1/0"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }catch(DBZException e) {
            b = true;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testMoreBrackets() {
        try{
            Assert.assertEquals(4d, calc.calculate("(((2+2)))"));
        }catch(CalculationException e) {
            Assert.fail("Error" + e.toString());
        }
    }

    @Test
    public void testMoreAndMoreBrackets() {
        try{
            Assert.assertEquals(4d, calc.calculate("((((2+(((2)))))))"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void testWrongInput1() {
        boolean b = false;
        try{
            Assert.assertEquals(1d, calc.calculate("1+"));
        }catch(CalculationException e) {
            b = true;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testWrongInput2() {
        boolean b = false;
        try{
            Assert.assertEquals(1d, calc.calculate("+"));
        }catch(CalculationException e) {
            b = true;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testWrongInput3() {
        boolean b = false;
        try{
            Assert.assertEquals(1d, calc.calculate("a + b"));
        }catch(CalculationException e) {
            b = true;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testWrongInput4() {
        boolean b = false;
        try{
            Assert.assertEquals(1d, calc.calculate("1 +* 4"));
        }catch(CalculationException e) {
            b = true;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testWrongInput5() {
        boolean b = false;
        try{
            Assert.assertEquals(1d, calc.calculate("*1"));
        }catch(CalculationException e) {
            b = true;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testWrongInput6() {
        boolean b = false;
        try{
            Assert.assertEquals(1d, calc.calculate("(((1+2)"));
        }catch(CalculationException e) {
            b = true;
        }
        Assert.assertTrue(b);
    }

    @Test
    public void testMixed() {
        try{
            Assert.assertEquals(-53d, calc.calculate("((3) + (-4) * 14 / 2 + 14 * (15 - 17))"));
        }catch(CalculationException e) {
            Assert.fail(e.toString());
        }
    }
}
