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
}
