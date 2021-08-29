import astro.practical.containers.Angle;
import astro.practical.lib.PACoordinates;
import astro.practical.lib.PAUtil;
import astro.practical.test.TestLib;

public class TestCoordinates {
	PACoordinates paCoordinates;

	public TestCoordinates() {
		paCoordinates = new PACoordinates();
	}

	public void testAngleToFromDecimalDegrees() {
		TestLib testLib = new TestLib();

		double decimalDegrees = paCoordinates.angleToDecimalDegrees(182, 31, 27);

		testLib.setTestName("Decimal Degrees for 182d 31m 27s").Assert(182.524167, PAUtil.round(decimalDegrees, 6));

		Angle angle = paCoordinates.decimalDegreesToAngle(182.524167);

		testLib.setTestName("Angle for 182.524167 decimal degrees").Assert(182, angle.degrees).Assert(31, angle.minutes)
				.Assert(27, angle.seconds);
	}
}
