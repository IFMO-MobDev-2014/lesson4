package ru.ifmo.md.lesson4.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;
import ru.ifmo.md.lesson4.MyCalculationEngine;
import ru.ifmo.md.lesson4.parser.ExpressionParser;
import ru.ifmo.md.lesson4.parser.number.HelperDouble;

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

    public void correctTestEvaluate(String s, double res) {
        try {
            Assert.assertEquals(res, CalculationEngineFactory.defaultEngine().calculate(s));
        } catch (CalculationException e) {
            e.printStackTrace();
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void testCorrect() {
        correctTestEvaluate("1", 1);
        correctTestEvaluate("1  *  \n  3 + -2", 1 * 3 + -2);
        correctTestEvaluate("1000*1000*1000*1", 1000.0 * 1000 * 1000 * 1);
        correctTestEvaluate("1000*1000*1000*2", 1000.0 * 1000 * 1000 * 2);
        correctTestEvaluate("1000*1000*1000*3", 1000.0 * 1000 * 1000 * 3);
        correctTestEvaluate(Integer.toString(Integer.MIN_VALUE) + " / -1", Integer.MIN_VALUE / -1.0);
        correctTestEvaluate("0*0*0      *   0*0*0*0 * 0 * 0 *0", 0 * 0 * 0 * 0 * 0 * 0 * 0 * 0 * 0 * 0);
        correctTestEvaluate("9*9*9      *   9*9*9*9 * 9 * 9 *9", 9.0 * 9 * 9 * 9 * 9 * 9 * 9 * 9 * 9 * 9);
        correctTestEvaluate("-8*-8*-8      *   -8*-8*-8*-8 * -8 * -8 * 16 + 0 * " + Integer.toString(Integer.MIN_VALUE), -8.0 * -8 * -8 * -8 * -8 * -8 * -8 * -8 * -8 * 16);
        correctTestEvaluate("3000000000", 3000000000.0);
        correctTestEvaluate("00-0000000000000010", -10);
        correctTestEvaluate("(1)-(-(-50*-2))/(-10.0-(--4))", (1) - (-(-50 * -2)) / (-10.0 - (4)));
        correctTestEvaluate("(5)", 5.0);
        correctTestEvaluate(Integer.toString(Integer.MIN_VALUE), Integer.MIN_VALUE);
        correctTestEvaluate("1-1", 0);
    }

    public void incorrectTestEvaluate(String s) {
        double res = 0;
        try {
            res = CalculationEngineFactory.defaultEngine().calculate(s);
        } catch (CalculationException e) {
            return;
        }
        Assert.fail("Incorrect expression was parsed, result is " + Double.toString(res) + "\n");
    }

    @Test
    public void testIncorrect() {
        incorrectTestEvaluate("1 1");
        incorrectTestEvaluate("0 / ----0");
        incorrectTestEvaluate("(1)-(-(-50*-2))/(4-(--4))");
        incorrectTestEvaluate("(5");
        incorrectTestEvaluate("5)");
        incorrectTestEvaluate("(54)) * -1+(0");
        incorrectTestEvaluate("(1))");
        incorrectTestEvaluate("0 / 0");
    }
}
