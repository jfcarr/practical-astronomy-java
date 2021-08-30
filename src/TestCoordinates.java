import astro.practical.containers.Angle;
import astro.practical.containers.HourAngle;
import astro.practical.containers.RightAscension;
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

	public void testRightAscensionToFromHourAngle() {
		TestLib testLib = new TestLib();

		HourAngle hourAngle = paCoordinates.rightAscensionToHourAngle(18, 32, 21, 14, 36, 51.67, false, -4, 22, 4, 1980,
				-64);

		testLib.setTestName(
				"Hour Angle for Right Ascension 18h 32m 21s, Local Civil Time 14:36:51.67, Local Date 4/22/1980, Geographical Longitude -64d")
				.Assert(9, hourAngle.hours).Assert(52, hourAngle.minutes).Assert(23.66, hourAngle.seconds);

		RightAscension rightAscension = paCoordinates.hourAngleToRightAscension(9, 52, 23.66, 14, 36, 51.67, false, -4,
				22, 4, 1980, -64);

		testLib.setTestName(
				"Right Ascension for Hour Angle 9h 52m 23.66s, Local Civil Time 14:36:51.67, Local Date 4/22/1980, Geographical Latitude -64d")
				.Assert(18, rightAscension.hours).Assert(32, rightAscension.minutes).Assert(21, rightAscension.seconds);
	}
}
