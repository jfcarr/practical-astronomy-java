import astro.practical.lib.PAMoon;
import astro.practical.models.ApproximatePositionOfMoon;
import astro.practical.test.TestLib;

public class TestMoon {
    PAMoon paMoon;
    TestLib testLib;

    public TestMoon() {
        paMoon = new PAMoon();
        testLib = new TestLib();
    }

    void testApproximatePositionOfMoon() {
        ApproximatePositionOfMoon approximatePositionOfMoon = paMoon.approximatePositionOfMoon(0, 0, 0, false, 0, 1, 9,
                2003);

        testLib.setTestName("Approximate Position of Moon")
                .Assert(14, approximatePositionOfMoon.moonRAHour)
                .Assert(12, approximatePositionOfMoon.moonRAMin)
                .Assert(42.31, approximatePositionOfMoon.moonRASec)
                .Assert(-11, approximatePositionOfMoon.moonDecDeg)
                .Assert(31, approximatePositionOfMoon.moonDecMin)
                .Assert(38.27, approximatePositionOfMoon.moonDecSec);
    }
}
