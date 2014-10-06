package ru.ifmo.md.lesson4.tests;

import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import ru.ifmo.md.lesson4.CalculationEngine;
import ru.ifmo.md.lesson4.CalculationEngineFactory;
import ru.ifmo.md.lesson4.CalculationException;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    CalculationEngine engine;

    public void expectFail(String s) {
        try {
            engine.calculate(s);
            Assert.fail("Expected fail");
        } catch (CalculationException ex) {
        }
    }

    public void expectEqual(String s, double d) {
        try {
            Assert.assertEquals(engine.calculate(s), d, 1e-10);
        } catch (CalculationException e) {
            Assert.fail("Unexpected fail");
        }
    }

    @Before
    public void setup() {
        engine = CalculationEngineFactory.defaultEngine();
    }

    @Test
    public void testCorrectness1() {
        expectEqual("-----3", -3.0);
    }

    @Test
    public void testCorrectness2() {
        expectEqual("0/0", Double.NaN);
    }

    @Test
    public void testCorrectness3() {
        expectEqual("2+(3/3)-1*4", -1.0);
    }

    @Test
    public void testCorrectness4() {
        expectEqual("1/10000000000000000", 1e-16);
    }

    @Test
    public void testCorrectness5() {
        expectEqual("((((((23*23))))))", 529);
    }

    @Test
    public void testCorrectness6() {
        expectEqual("-.4", -0.4);
    }

    @Test
    public void testCorrectness7() {
        expectEqual("(23/23)*44+(11+(--33)*74/33-14+(55/11*2+5*2))-66*11+322-999-1-11-1-1-1", -1283);
    }

    @Test
    public void testCorrectness8() {
        expectEqual("(7-3)*4*389*(5+3)", 49792.0);
    }

    @Test
    public void testCorrectness9() {
        expectEqual("30000000000000000000000000000000/10000000000000000000000000000000", 3.0);
    }

    @Test
    public void testCorrectness10() {
        expectEqual("1/0", Double.POSITIVE_INFINITY);
    }

    @Test
    public void testFail1() {
        expectFail("()");
    }

    @Test
    public void testFail2() {
        expectFail("30000000000000000000000000000000//10000000000000000000000000000000");
    }

    @Test
    public void testFail3() {
        expectFail("(123)*)1233");
    }

    @Test
    public void testFail4() {
        expectFail("0.111-+23");
    }

    @Test
    public void testFail5() {
        expectFail("(23.3/(333)");
    }

    @Test
    public void testFail6() {
        expectFail("66(3)");
    }

    @Test
    public void testFail7() {
        expectFail("(2*44)+44+4)");
    }

    @Test
    public void someDummyTests() {
        Random r = new Random();
        int length = 30;
        for (int i = 0; i < 10; i++) {
            double num = r.nextDouble();
            String expr = String.valueOf(num);
            double answer = num;
            for (int j = 0; j < length; j++) {
                num = r.nextDouble();
                int rand = r.nextInt(4);
                if (rand == 0) {
                    expr = '(' + expr + ")+" + String.valueOf(num);
                    answer += num;
                }
                if (rand == 1) {
                    expr = '(' + expr + ")-" + String.valueOf(num);
                    answer -= num;
                }
                if (rand == 2) {
                    expr = '(' + expr + ")*" + String.valueOf(num);
                    answer *= num;
                }
                if (rand == 3) {
                    expr = '(' + expr + ")/" + String.valueOf(num);
                    answer /= num;
                }
            }
            expectEqual(expr, answer);
        }
    }


}
