import astro.practical.lib.PAComet;
import astro.practical.models.PositionOfEllipticalComet;
import astro.practical.models.PositionOfParabolicComet;
import astro.practical.test.TestLib;

public class TestComet {
    PAComet paComet;
    TestLib testLib;

    public TestComet() {
        paComet = new PAComet();
        testLib = new TestLib();
    }

    public void testPositionOfEllipticalComet() {
        PositionOfEllipticalComet positionOfEllipticalComet = paComet.positionOfEllipticalComet(0, 0, 0, false, 0, 1, 1,
                1984, "Halley");

        testLib.setTestName("Position of Elliptical Comet")
                .Assert(6, positionOfEllipticalComet.cometRAHour)
                .Assert(29, positionOfEllipticalComet.cometRAMin)
                .Assert(10, positionOfEllipticalComet.cometDecDeg)
                .Assert(13, positionOfEllipticalComet.cometDecMin)
                .Assert(8.13, positionOfEllipticalComet.cometDistEarth);
    }

    public void testPositionOfParabolicComet() {
        PositionOfParabolicComet positionOfParabolicComet = paComet.positionOfParabolicComet(0, 0, 0, false, 0, 25, 12,
                1977, "Kohler");

        testLib.setTestName("Position of Parabolic Comet")
                .Assert(23, positionOfParabolicComet.cometRAHour)
                .Assert(17, positionOfParabolicComet.cometRAMin)
                .Assert(11.53, positionOfParabolicComet.cometRASec)
                .Assert(-33, positionOfParabolicComet.cometDecDeg)
                .Assert(42, positionOfParabolicComet.cometDecMin)
                .Assert(26.42, positionOfParabolicComet.cometDecSec)
                .Assert(1.11, positionOfParabolicComet.cometDistEarth);
    }
}
