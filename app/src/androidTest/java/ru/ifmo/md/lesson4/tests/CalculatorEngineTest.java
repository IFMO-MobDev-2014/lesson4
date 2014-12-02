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

import java.util.Random;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CalculatorEngineTest {
    private static final int FOR_TEST = 100; // I'm litte afraid of overflow difference in scala and java 
    private CalculationEngine engine;
    private Random rand;

    @Before
    public void setup() {
        engine = CalculationEngineFactory.defaultEngine();
        rand = new Random(System.currentTimeMillis());
    }

    @Test
    public void testSimplestOperations() {
        try {
            Assert.assertEquals(10d, engine.calculate("5+5"));
            Assert.assertEquals(25d, engine.calculate("(5+(2*10))"));
            Assert.assertEquals(3d, engine.calculate("6/2"));
            Assert.assertEquals(0.5d, engine.calculate(".5"));
            Assert.assertEquals(512d, engine.calculate("2^3^2"));
            Assert.assertEquals(18.9d, engine.calculate(".5 + .4 + 3^2*2"));
            Assert.assertEquals(512d, engine.calculate("(2^8)/2/(2^-2)"));
            Assert.assertEquals(170d + 2d / 3d, engine.calculate("(2^(3*1)^2)/3"));
//            Assert.assertEquals((10d/3d), CalculationEngineFactory.defaultEngine().calculate("1/3 + 1/3 + 1/3"));
        } catch (CalculationException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testSomeGeneration() {
        try {
            double cur = 0;
            StringBuilder str = new StringBuilder();
            int r;
            double x;
            for (int i = 0; i < FOR_TEST; i++) {
                r = rand.nextInt(4);
                x = rand.nextDouble() % 10; //adequate
                switch (r) {
                    case 0:
                        str.append("+").append(x);
                        cur = cur + x;
                        break;
                    case 1:
                        str.append("*").append(x);
                        cur = cur * x;
                        break;
                    case 2:
                        str.append("-").append(x);
                        cur = cur - x;
                        break;
                    case 3:
                        str.append("/").append(x);
                        cur = cur / x;
                        break;
                }
                Assert.assertEquals(cur, engine.calculate(str.toString()));
            }
        } catch (CalculationException e) {
            e.printStackTrace();
        }
    }
}
