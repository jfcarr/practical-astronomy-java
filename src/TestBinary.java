import astro.practical.lib.PABinary;
import astro.practical.models.BinaryStarOrbit;
import astro.practical.test.TestLib;

public class TestBinary {
    PABinary paBinary;
    TestLib testLib;

    public TestBinary() {
        paBinary = new PABinary();
        testLib = new TestLib();
    }

    public void testBinaryStarOrbit() {
        BinaryStarOrbit binaryStarOrbit = paBinary.binaryStarOrbit(1, 1, 1980, "eta-Cor");

        testLib.setTestName("Binary Star Orbit")
                .Assert(318.5, binaryStarOrbit.positionAngleDeg)
                .Assert(0.41, binaryStarOrbit.separationArcsec);
    }
}
