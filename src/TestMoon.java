import astro.practical.lib.PAMoon;
import astro.practical.models.ApproximatePositionOfMoon;
import astro.practical.models.MoonDistAngDiamHorParallax;
import astro.practical.models.MoonPhase;
import astro.practical.models.MoonriseAndMoonset;
import astro.practical.models.PrecisePositionOfMoon;
import astro.practical.models.TimesOfNewMoonAndFullMoon;
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

    void testTimesOfNewMoonAndFullMoon() {
        TimesOfNewMoonAndFullMoon timesOfNewMoonAndFullMoon = paMoon.timesOfNewMoonAndFullMoon(false, 0, 1, 9, 2003);

        testLib.setTestName("Times of New Moon and Full Moon")
                .Assert(17, timesOfNewMoonAndFullMoon.nmLocalTimeHour)
                .Assert(27, timesOfNewMoonAndFullMoon.nmLocalTimeMin)
                .Assert(27, timesOfNewMoonAndFullMoon.nmLocalDateDay)
                .Assert(8, timesOfNewMoonAndFullMoon.nmLocalDateMonth)
                .Assert(2003, timesOfNewMoonAndFullMoon.nmLocalDateYear)
                .Assert(16, timesOfNewMoonAndFullMoon.fmLocalTimeHour)
                .Assert(36, timesOfNewMoonAndFullMoon.fmLocalTimeMin)
                .Assert(10, timesOfNewMoonAndFullMoon.fmLocalDateDay)
                .Assert(9, timesOfNewMoonAndFullMoon.fmLocalDateMonth)
                .Assert(2003, timesOfNewMoonAndFullMoon.fmLocalDateYear);
    }

    void testMoonDistAngDiamHorParallax() {
        MoonDistAngDiamHorParallax moonDistAngDiamHorParallax = paMoon.moonDistAngDiamHorParallax(0, 0, 0, false, 0, 1,
                9, 2003);

        testLib.setTestName("Moon distance, angular diameter, and horizontal parallax")
                .Assert(367964, moonDistAngDiamHorParallax.earthMoonDist)
                .Assert(0, moonDistAngDiamHorParallax.angDiameterDeg)
                .Assert(32, moonDistAngDiamHorParallax.angDiameterMin)
                .Assert(0, moonDistAngDiamHorParallax.horParallaxDeg)
                .Assert(59, moonDistAngDiamHorParallax.horParallaxMin)
                .Assert(35.49, moonDistAngDiamHorParallax.horParallaxSec);
    }

    void testMoonriseAndMoonset() {
        MoonriseAndMoonset moonriseAndMoonset = paMoon.moonriseAndMoonset(6, 3, 1986, false, -5, -71.05, 42.3667);

        testLib.setTestName("Moonrise and Moonset")
                .Assert(4, moonriseAndMoonset.mrLTHour)
                .Assert(21, moonriseAndMoonset.mrLTMin)
                .Assert(6, moonriseAndMoonset.mrLocalDateDay)
                .Assert(3, moonriseAndMoonset.mrLocalDateMonth)
                .Assert(1986, moonriseAndMoonset.mrLocalDateYear)
                .Assert(127.33, moonriseAndMoonset.mrAzimuthDeg)
                .Assert(13, moonriseAndMoonset.msLTHour)
                .Assert(8, moonriseAndMoonset.msLTMin)
                .Assert(6, moonriseAndMoonset.msLocalDateDay)
                .Assert(3, moonriseAndMoonset.msLocalDateMonth)
                .Assert(1986, moonriseAndMoonset.msLocalDateYear)
                .Assert(234.06, moonriseAndMoonset.msAzimuthDeg);
    }
}
