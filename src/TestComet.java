import astro.practical.lib.PAComet;
import astro.practical.models.PositionOfEllipticalComet;
import astro.practical.test.TestLib;

public class TestComet {
    PAComet paComet;

    public TestComet() {
        paComet = new PAComet();
    }

    public void testPositionOfEllipticalComet() {
        TestLib testLib = new TestLib();

        PositionOfEllipticalComet positionOfEllipticalComet = paComet.positionOfEllipticalComet(0, 0, 0, false, 0, 1, 1,
                1984, "Halley");

        testLib.setTestName("Position of Elliptical Comet")
                .Assert(6, positionOfEllipticalComet.cometRAHour)
                .Assert(29, positionOfEllipticalComet.cometRAMin)
                .Assert(10, positionOfEllipticalComet.cometDecDeg)
                .Assert(13, positionOfEllipticalComet.cometDecMin)
                .Assert(8.13, positionOfEllipticalComet.cometDistEarth);
    }
}
