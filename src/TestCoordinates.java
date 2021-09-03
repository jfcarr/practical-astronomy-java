import astro.practical.containers.Angle;
import astro.practical.containers.EquatorialCoordinates;
import astro.practical.containers.HorizonCoordinates;
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

	public void testEquatorialCoordinatesToFromHorizonCoordinates() {
		TestLib testLib = new TestLib();

		HorizonCoordinates horizonCoordinates = paCoordinates.equatorialCoordinatesToHorizonCoordinates(5, 51, 44, 23,
				13, 10, 52);

		testLib.setTestName(
				"Horizon Coordinates for Hour Angle 05:51:44 and Declination 23d 13m 10s and Geographical Latitude 52d")
				.Assert(283, horizonCoordinates.azimuthDegrees).Assert(16, horizonCoordinates.azimuthMinutes)
				.Assert(15.7, horizonCoordinates.azimuthSeconds).Assert(19, horizonCoordinates.altitudeDegrees)
				.Assert(20, horizonCoordinates.altitudeMinutes).Assert(3.64, horizonCoordinates.altitudeSeconds);

		EquatorialCoordinates equatorialCoordinates = paCoordinates.horizonCoordinatesToEquatorialCoordinates(283, 16,
				15.7, 19, 20, 3.64, 52);

		testLib.setTestName(
				"Equatorial Coordinates for Azimuth 283d 16m 15.7s and Altitude 19d 20m 3.64s and Geographical Latitude 52d")
				.Assert(5, equatorialCoordinates.hourAngleHours).Assert(51, equatorialCoordinates.hourAngleMinutes)
				.Assert(44, equatorialCoordinates.hourAngleSeconds).Assert(23, equatorialCoordinates.declinationDegrees)
				.Assert(13, equatorialCoordinates.declinationMinutes)
				.Assert(10, equatorialCoordinates.declinationSeconds);
	}
}
