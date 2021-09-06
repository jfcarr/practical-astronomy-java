import astro.practical.containers.Angle;
import astro.practical.containers.EclipticCoordinates;
import astro.practical.containers.EquatorialCoordinatesHA;
import astro.practical.containers.EquatorialCoordinatesRA;
import astro.practical.containers.GalacticCoordinates;
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

		EquatorialCoordinatesHA equatorialCoordinates = paCoordinates.horizonCoordinatesToEquatorialCoordinates(283, 16,
				15.7, 19, 20, 3.64, 52);

		testLib.setTestName(
				"Equatorial Coordinates for Azimuth 283d 16m 15.7s and Altitude 19d 20m 3.64s and Geographical Latitude 52d")
				.Assert(5, equatorialCoordinates.hourAngleHours).Assert(51, equatorialCoordinates.hourAngleMinutes)
				.Assert(44, equatorialCoordinates.hourAngleSeconds).Assert(23, equatorialCoordinates.declinationDegrees)
				.Assert(13, equatorialCoordinates.declinationMinutes)
				.Assert(10, equatorialCoordinates.declinationSeconds);
	}

	public void testMeanObliquityOfTheEcliptic() {
		TestLib testLib = new TestLib();

		double meanObliquityOfTheEcliptic = PAUtil.round(paCoordinates.meanObliquityOfTheEcliptic(6, 7, 2009), 8);

		testLib.setTestName("Mean Obliquity of the Ecliptic for 7/6/2009").Assert(23.43805531,
				meanObliquityOfTheEcliptic);
	}

	public void testEclipticCoordinateToFromEquatorialCoordinate() {
		TestLib testLib = new TestLib();

		EquatorialCoordinatesRA equatorialCoordinatesRA = paCoordinates.eclipticCoordinateToEquatorialCoordinate(139,
				41, 10, 4, 52, 31, 6, 7, 2009);

		testLib.setTestName(
				"Equatorial Coordinates for Ecliptic Longitude 139d 41m 10s and Ecliptic Latitude 4d 52m 31s and Greenwich Date 7/6/2009")
				.Assert(9, equatorialCoordinatesRA.rightAscensionHours)
				.Assert(34, equatorialCoordinatesRA.rightAscensionMinutes)
				.Assert(53.4, equatorialCoordinatesRA.rightAscensionSeconds)
				.Assert(19, equatorialCoordinatesRA.declinationDegrees)
				.Assert(32, equatorialCoordinatesRA.declinationMinutes)
				.Assert(8.52, equatorialCoordinatesRA.declinationSeconds);

		EclipticCoordinates eclipticCoordinates = paCoordinates.equatorialCoordinateToEclipticCoordinate(9, 34, 53.4,
				19, 32, 8.52, 6, 7, 2009);

		testLib.setTestName(
				"Ecliptic Coordinates for Right Ascension 9h 34m 53.4s and Declination 19d 32h 8.52s and Greenwich Date 7/6/2009")
				.Assert(139, eclipticCoordinates.longitudeDegrees).Assert(41, eclipticCoordinates.longitudeMinutes)
				.Assert(9.97, eclipticCoordinates.longitudeSeconds).Assert(4, eclipticCoordinates.latitudeDegrees)
				.Assert(52, eclipticCoordinates.latitudeMinutes).Assert(30.99, eclipticCoordinates.latitudeSeconds);
	}

	public void testEquatorialCoordinateToFromGalacticCoordinate() {
		TestLib testLib = new TestLib();

		GalacticCoordinates galacticCoordinates = paCoordinates.equatorialCoordinateToGalacticCoordinate(10, 21, 0, 10,
				3, 11);

		testLib.setTestName(
				"Galactic Coordinates for Equatorial Longitude 10d 21m 0s and Equatorial Latitude 10d 3m 11s")
				.Assert(232, galacticCoordinates.longitudeDegrees).Assert(14, galacticCoordinates.longitudeMinutes)
				.Assert(52.38, galacticCoordinates.longitudeSeconds).Assert(51, galacticCoordinates.latitudeDegrees)
				.Assert(7, galacticCoordinates.latitudeMinutes).Assert(20.16, galacticCoordinates.latitudeSeconds);

		EquatorialCoordinatesRA equatorialCoordinatesRA = paCoordinates.galacticCoordinateToEquatorialCoordinate(232,
				14, 52.38, 51, 7, 20.16);

		testLib.setTestName(
				"Equatorial Coordinates for Galactic Longitude 232d 14m 52.38s and Galactic Latitude 51d 7m 20.16s")
				.Assert(10, equatorialCoordinatesRA.rightAscensionHours)
				.Assert(21, equatorialCoordinatesRA.rightAscensionMinutes)
				.Assert(0, equatorialCoordinatesRA.rightAscensionSeconds)
				.Assert(10, equatorialCoordinatesRA.declinationDegrees)
				.Assert(3, equatorialCoordinatesRA.declinationMinutes)
				.Assert(11, equatorialCoordinatesRA.declinationSeconds);
	}
}
