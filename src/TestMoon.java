import astro.practical.lib.PAMoon;
import astro.practical.models.ApproximatePositionOfMoon;
import astro.practical.models.MoonPhase;
import astro.practical.models.PrecisePositionOfMoon;
import astro.practical.test.TestLib;
import astro.practical.types.AccuracyLevel;

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

    void testPrecisePositionOfMoon() {
        PrecisePositionOfMoon precisePositionOfMoon = paMoon.precisePositionOfMoon(0, 0, 0, false, 0, 1, 9, 2003);

        testLib.setTestName("Precise Position of Moon")
                .Assert(14, precisePositionOfMoon.moonRAHour)
                .Assert(12, precisePositionOfMoon.moonRAMin)
                .Assert(10.21, precisePositionOfMoon.moonRASec)
                .Assert(-11, precisePositionOfMoon.moonDecDeg)
                .Assert(34, precisePositionOfMoon.moonDecMin)
                .Assert(57.83, precisePositionOfMoon.moonDecSec)
                .Assert(367964, precisePositionOfMoon.earthMoonDistKM)
                .Assert(0.993191, precisePositionOfMoon.moonHorParallaxDeg);
    }

    void testMoonPhase() {
        MoonPhase moonPhase = paMoon.moonPhase(0, 0, 0, false, 0, 1, 9, 2003, AccuracyLevel.APPROXIMATE);

        testLib.setTestName("Moon Phase and Bright Limb")
                .Assert(0.22, moonPhase.moonPhase)
                .Assert(-71.58, moonPhase.paBrightLimbDeg);
    }
}
