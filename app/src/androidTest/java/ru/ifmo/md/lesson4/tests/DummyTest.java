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
//    public void testWhoppingComplex() {
//        try {
//            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
//        } catch (CalculationException e) {
//            Assert.fail("Exception happened " + e);
//        }
//    }
    public void testOK(String test, double res) {
        try {
            Assert.assertEquals(res, CalculationEngineFactory.defaultEngine().calculate(test));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
    public void testFAIL(String test) {
        try {
            Assert.assertEquals(1, CalculationEngineFactory.defaultEngine().calculate(test));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }
    public void runTests() {
        testOK("1+2", 1 + 2);
        testOK("(-4)+(-5)", (-4)+(-5));
        testOK("-5+10", -5+10);
        testOK("45-49", 45-49);
        testOK("-45-(-90)", -45-(-90));
        testOK("-30-100", -30-100);
        testOK("5*9", 5*9);
        testOK("(-100)*4",(-100)*4);
        testOK("(-200)*(-2)", (-200)*(-2));
        testOK("1/2", 1/2);
        testOK("15/5", 15/5);
        testOK("(-100)/20", (-100)/20);
        testOK("(((((((((((3+4+5+6+7+8+9)))))))))))", (((((((((((3+4+5+6+7+8+9))))))))))));
        testOK("(3 + 5) * (2 + 3) / 45 + (4 * 4 + 2)", (3 + 5) * (2 + 3) / 45 + (4 * 4 + 2));
        testOK("1 / 0.000000001", 1 / 0.000000001);
        testOK("0.0001/0.0001", 0.0001/0.0001);
        testOK("25-----25", 25-(-(-(-25)))); //???

        testFAIL("1/0");
        testFAIL("(94*123+323) / 0");
        testFAIL("(-1)/0");
        testFAIL("0/0");
        testFAIL("1/(");
        testFAIL("(8+(6*(4+5)");
        testFAIL("4.00.00.9");
        testFAIL("9.0+.0");
        testFAIL("9.0+0.+");
        testFAIL(".0.");
        testFAIL("(78+8)(77-8)");





    }
}
