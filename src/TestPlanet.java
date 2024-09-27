import astro.practical.lib.PAPlanet;
import astro.practical.models.PlanetPosition;
import astro.practical.test.TestLib;

public class TestPlanet {
    PAPlanet paPlanet;

    public TestPlanet() {
        paPlanet = new PAPlanet();
    }

    public void testApproximatePositionOfPlanet() {
        TestLib testLib = new TestLib();

        PlanetPosition planetPosition = paPlanet.approximatePositionOfPlanet(0, 0, 0, false, 0, 22, 11, 2003,
                "Jupiter");

        testLib.setTestName("Approximate Position of Planet")
                .Assert(11, planetPosition.planetRAHour)
                .Assert(11, planetPosition.planetRAMin)
                .Assert(13.8, planetPosition.planetRASec)
                .Assert(6, planetPosition.planetDecDeg)
                .Assert(21, planetPosition.planetDecMin)
                .Assert(25.1, planetPosition.planetDecSec);
    }

    public void testPrecisePositionOfPlanet() {
        TestLib testLib = new TestLib();

        PlanetPosition planetPosition = paPlanet.precisePositionOfPlanet(0, 0, 0, false, 0, 22, 11, 2003, "Jupiter");

        testLib.setTestName("Precise Position of Planet")
                .Assert(11, planetPosition.planetRAHour)
                .Assert(10, planetPosition.planetRAMin)
                .Assert(30.99, planetPosition.planetRASec)
                .Assert(6, planetPosition.planetDecDeg)
                .Assert(25, planetPosition.planetDecMin)
                .Assert(49.46, planetPosition.planetDecSec);
    }
}
