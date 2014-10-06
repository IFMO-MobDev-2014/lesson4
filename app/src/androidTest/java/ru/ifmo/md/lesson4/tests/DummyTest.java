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
        try {
            Assert.assertEquals(10d, CalculationEngineFactory.defaultEngine().calculate("5+5"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void onlyNumberTesting() {
        try {
            Assert.assertEquals(100d, CalculationEngineFactory.defaultEngine().calculate("100"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals(100.018309324d, CalculationEngineFactory.defaultEngine().calculate("100.018309324"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals(0d, CalculationEngineFactory.defaultEngine().calculate("0"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals(-1235d, CalculationEngineFactory.defaultEngine().calculate("-1235"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals(-1235.24235d, CalculationEngineFactory.defaultEngine().calculate("-1235.24235"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            CalculationEngineFactory.defaultEngine().calculate("2721648726347.838742.3482374");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate(".3482374");
            Assert.fail();
        } catch (CalculationException ignored) {}
    }

    @Test
    public void sumTest() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("1+");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate("+1");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            Assert.assertEquals(158037.636465d, CalculationEngineFactory.defaultEngine().calculate("157583.636465+454"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((324982734234234d+0.9485093d), CalculationEngineFactory.defaultEngine().calculate("324982734234234+0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d+0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002+0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d+-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002+-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((0d+-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("0+-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }


    @Test
    public void subTest() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("1-");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            Assert.assertEquals((157583.636465d-454d), CalculationEngineFactory.defaultEngine().calculate("157583.636465-454"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((324982734234234d-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("324982734234234-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d+0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002--0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((0d+0.9485093d), CalculationEngineFactory.defaultEngine().calculate("0--0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void mulTest() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("1*");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate("*1");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            Assert.assertEquals((157583.636465d*454d), CalculationEngineFactory.defaultEngine().calculate("157583.636465*454"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((324982734234234d*0.9485093d), CalculationEngineFactory.defaultEngine().calculate("324982734234234*0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d*0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002*0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d*-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002*-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((0d*-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("0*-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }


    @Test
    public void divTest() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("1/");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate("/1");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            Assert.assertEquals((157583.636465d/454d), CalculationEngineFactory.defaultEngine().calculate("157583.636465/454"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((324982734234234d/0.9485093d), CalculationEngineFactory.defaultEngine().calculate("324982734234234/0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d/0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002/0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((-324982734234234.0002d/-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("-324982734234234.0002/-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((0d/-0.9485093d), CalculationEngineFactory.defaultEngine().calculate("0/-0.9485093"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }


    @Test
    public void infNaNTest() {
        try {
            Assert.assertTrue(Double.isInfinite(CalculationEngineFactory.defaultEngine().calculate("73648274/0")));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertTrue(Double.isInfinite(CalculationEngineFactory.defaultEngine().calculate("-73648274/0")));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }


        try {
            Assert.assertTrue(Double.isNaN(CalculationEngineFactory.defaultEngine().calculate("0/0")));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void multipleOperationTest() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("124+*2310");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate("124/*2310");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate("124-*2310");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            Assert.assertEquals((73246823d+834d*362636.3874d-0.38742873d+32490.994d/234.9d), CalculationEngineFactory.defaultEngine().calculate("73246823+834*362636.3874-0.38742873+32490.994/234.9"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals((2834.999d-128437d+82739812.994d*212.9d/27374d-24412d*44d), CalculationEngineFactory.defaultEngine().calculate("2834.999-128437+82739812.994*212.9/27374-24412*44"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

    @Test
    public void bracketTest() {
        try {
            CalculationEngineFactory.defaultEngine().calculate("2721648726347.838742(3482374)");
            Assert.fail();
        } catch (CalculationException ignored) {}


        try {
            CalculationEngineFactory.defaultEngine().calculate("2721648726347.838742+(3482374");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate("(2721648726347.838742+3482374");
            Assert.fail();
        } catch (CalculationException ignored) {}

        try {
            CalculationEngineFactory.defaultEngine().calculate("2721648726347.838742+3482374)");
            Assert.fail();
        } catch (CalculationException ignored) {}


        try {
            Assert.assertEquals(((73246823d+384792834d)*(362636.3874d-(0.38742873d+32490.994d)/234.9d)), CalculationEngineFactory.defaultEngine().calculate("(73246823+384792834)*(362636.3874-(0.38742873+32490.994)/234.9)"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }

        try {
            Assert.assertEquals(((2834.999d-128437d+(82739812.994d*212.9d))/((27374d-24412d)*44d)), CalculationEngineFactory.defaultEngine().calculate("(2834.999-128437+(82739812.994*212.9))/((27374-24412)*44)"));
        } catch (CalculationException e) {
            Assert.fail("Exception happened " + e);
        }
    }

}