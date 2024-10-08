import astro.practical.lib.PASun;
import astro.practical.models.EquationOfTime;
import astro.practical.models.MorningAndEveningTwilight;
import astro.practical.models.PositionOfSun;
import astro.practical.models.SunDistanceAndAngularSize;
import astro.practical.models.SunriseAndSunset;
import astro.practical.test.TestLib;
import astro.practical.types.RiseSetStatus;
import astro.practical.types.TwilightStatus;
import astro.practical.types.TwilightType;

public class TestSun {
        PASun paSun;
        TestLib testLib;

        public TestSun() {
                paSun = new PASun();
                testLib = new TestLib();
        }

        public void testApproximatePositionOfSun() {
                PositionOfSun approximatePositionOfSun = paSun.approximatePositionOfSun(0, 0, 0, 27, 7, 2003, false,
                                0);

                testLib.setTestName("Approximate Position of Sun")
                                .Assert(8, approximatePositionOfSun.sunRAHour)
                                .Assert(23, approximatePositionOfSun.sunRAMin)
                                .Assert(33.73, approximatePositionOfSun.sunRASec)
                                .Assert(19, approximatePositionOfSun.sunDecDeg)
                                .Assert(21, approximatePositionOfSun.sunDecMin)
                                .Assert(14.33, approximatePositionOfSun.sunDecSec);
        }

        public void testPrecisePositionOfSun() {
                PositionOfSun precisePositionOfSun = paSun.precisePositionOfSun(0, 0, 0, 27, 7, 1988, false,
                                0);

                testLib.setTestName("Precise Position of Sun")
                                .Assert(8, precisePositionOfSun.sunRAHour)
                                .Assert(26, precisePositionOfSun.sunRAMin)
                                .Assert(3.83, precisePositionOfSun.sunRASec)
                                .Assert(19, precisePositionOfSun.sunDecDeg)
                                .Assert(12, precisePositionOfSun.sunDecMin)
                                .Assert(49.72, precisePositionOfSun.sunDecSec);
        }

        public void testSunDistanceAndAngularSize() {
                SunDistanceAndAngularSize sunDistanceAndAngularSize = paSun.sunDistanceAndAngularSize(0, 0, 0, 27, 7,
                                1988,
                                false, 0);

                testLib.setTestName("Sun Distance and Angular Size")
                                .Assert(151920130, sunDistanceAndAngularSize.sunDistKm)
                                .Assert(0, sunDistanceAndAngularSize.sunAngSizeDeg)
                                .Assert(31, sunDistanceAndAngularSize.sunAngSizeMin)
                                .Assert(29.93, sunDistanceAndAngularSize.sunAngSizeSec);
        }

        public void testSunriseAndSunset() {
                SunriseAndSunset sunriseAndSunset = paSun.sunriseAndSunset(10, 3, 1986, false, -5, -71.05, 42.37);

                testLib.setTestName("Sunrise and Sunset")
                                .Assert(6, sunriseAndSunset.localSunriseHour)
                                .Assert(5, sunriseAndSunset.localSunriseMinute)
                                .Assert(17, sunriseAndSunset.localSunsetHour)
                                .Assert(45, sunriseAndSunset.localSunsetMinute)
                                .Assert(94.83, sunriseAndSunset.azimuthOfSunriseDeg)
                                .Assert(265.43, sunriseAndSunset.azimuthOfSunsetDeg)
                                .Assert(RiseSetStatus.OK, sunriseAndSunset.status);
        }

        public void testMorningAndEveningTwilight() {
                MorningAndEveningTwilight morningAndEveningTwilight = paSun.morningAndEveningTwilight(7, 9, 1979, false,
                                0, 0, 52, TwilightType.ASTRONOMICAL);

                testLib.setTestName("Morning and Evening Twilight")
                                .Assert(3, morningAndEveningTwilight.amTwilightBeginsHour)
                                .Assert(17, morningAndEveningTwilight.amTwilightBeginsMin)
                                .Assert(20, morningAndEveningTwilight.pmTwilightEndsHour)
                                .Assert(37, morningAndEveningTwilight.pmTwilightEndsMin)
                                .Assert(TwilightStatus.OK, morningAndEveningTwilight.status);
        }

        public void testEquationOfTime() {
                EquationOfTime equationOfTime = paSun.equationOfTime(27, 7, 2010);

                testLib.setTestName("Equation of Time")
                                .Assert(6, equationOfTime.equationOfTimeMin)
                                .Assert(31.52, equationOfTime.equationOfTimeSec);
        }

        public void testSolarElongation() {
                double solarElongation = paSun.solarElongation(10, 6, 45, 11, 57, 27, 27.8333333, 7, 2010);

                testLib.setTestName("Solar Elongation")
                                .Assert(24.78, solarElongation);
        }
}
