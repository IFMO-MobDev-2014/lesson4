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
        checkCorrect(12d, "5 + 7");
        checkCorrect(0.9285716709323021d, "1/(1+1/(1+3   * 4-06/(1.0+3.214-126312.3123))) + 0.0");
        checkCorrect(0d, "(((0)))");
        checkCorrect(19.1d, "2*2*2*2----3.1");
        checkCorrect(-3d, "-(3)");
        checkCorrect(6d, "-(-3-3)");
        checkCorrect(-3d, "-----(((3.00000)))");
        checkCorrect(666d, "666.000-0.00000");
        checkCorrect(0.25d, "0.25");
        checkCorrect(-7.0/15, "-7/-3/-5");
        checkCorrect(-2,"-2.");
        checkCorrect(6,"2+2*2");
        checkCorrect(2d*(2135123d-1263123.0+0.3)-1523512d/123512d/(12351d+5123d-123d/3.0121351)+3-6, "2*(2135123-1263123.0+0.3)-1523512/123512/(12351+5123-123/3.0121351)+3-6");

        checkException("1/0");
        checkException("0/0");
        checkException("1/(0.0-0.7+0.7)");
        checkException("+1");
        checkException("333/(333/(333/(1-1)))");
        checkException("5.(5)");
        checkException(".2");
        checkException(".");
        checkException("()");
        checkException("(((123412-1)");
        checkException("(");
        checkException(")");
        checkException("");
        checkException("1+)");
        checkException("1.0+1.0)");

    }

    public void checkCorrect(double x, String curTest) {
        double y = 0.0;
        try {
            y = CalculationEngineFactory.defaultEngine().calculate(curTest);
        } catch (CalculationException err) {
            Assert.fail("Incorrect exception");
        } finally {
            Assert.assertEquals(x, y);
        }
    }

    public void checkException(String curTest) {
        try {
            double tmp = CalculationEngineFactory.defaultEngine().calculate(curTest);
            Assert.fail("Expected exception");
        } catch (CalculationException ignored) {
        }
    }

}
