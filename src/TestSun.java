import astro.practical.lib.PASun;
import astro.practical.test.TestLib;
import astro.practical.types.complex.PositionOfSun;

public class TestSun {
    PASun paSun;

    public TestSun() {
        paSun = new PASun();
    }

    public void testApproximatePositionOfSun() {
        TestLib testLib = new TestLib();

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
        TestLib testLib = new TestLib();

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
}
