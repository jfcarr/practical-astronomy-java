import astro.practical.containers.Aberration;
import astro.practical.containers.Angle;
import astro.practical.containers.EclipticCoordinates;
import astro.practical.containers.EquatorialCoordinatesHA;
import astro.practical.containers.EquatorialCoordinatesRA;
import astro.practical.containers.GalacticCoordinates;
import astro.practical.containers.HorizonCoordinates;
import astro.practical.containers.HourAngle;
import astro.practical.containers.Nutation;
import astro.practical.containers.RightAscension;
import astro.practical.containers.RiseSet;
import astro.practical.lib.PACoordinates;
import astro.practical.lib.PAUtil;
import astro.practical.test.TestLib;
import astro.practical.types.PAAngleMeasure;
import astro.practical.types.RightAscensionDeclination;
import astro.practical.types.RiseSetStatus;
import astro.practical.types.CoordinateType;

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

	public void testAngleBetweenTwoObjects() {
		TestLib testLib = new TestLib();

		Angle angle = paCoordinates.angleBetweenTwoObjects(5, 13, 31.7, -8, 13, 30, 6, 44, 13.4, -16, 41, 11,
				PAAngleMeasure.Hours);

		testLib.setTestName(
				"Angle for RA (1) 5h 13m 31.7s and Declination (1) -8d 13m 30s and RA (2) 6h 44m 13.4s and Declination (2) -16d 41m 11s and measurement in hours")
				.Assert(23, angle.degrees).Assert(40, angle.minutes).Assert(25.86, angle.seconds);
	}

	public void testRisingAndSetting() {
		TestLib testLib = new TestLib();

		RiseSet riseSet = paCoordinates.risingAndSetting(23, 39, 20, 21, 42, 0, 24, 8, 2010, 64, 30, 0.5667);

		testLib.setTestName(
				"Rise and Set Times for Right Ascension 23h 39m 20s and Declination 21d 42m 0s and Greenwich Date 8/24/2010 and Geographical Longitude/Latitude 64d/30d and Vertical Shift 0.5667d")
				.Assert(RiseSetStatus.OK, riseSet.riseSetStatus).Assert(14, riseSet.utRiseHour)
				.Assert(16, riseSet.utRiseMin).Assert(4, riseSet.utSetHour).Assert(10, riseSet.utSetMin)
				.Assert(64.36, riseSet.azRise).Assert(295.64, riseSet.azSet);
	}

	public void testCorrectForPrecession() {
		TestLib testLib = new TestLib();

		RightAscensionDeclination rightAscensionDeclination = paCoordinates.correctForPrecession(9, 10, 43, 14, 23, 25,
				0.923, 1, 1950, 1, 6, 1979);

		testLib.setTestName(
				"Corrected Precession for Right Ascension 9h 10m 43s and Declination 14d 23m 25s and Epoch 1 Date 1/0.923/1950 and Epoch 2 Date 6/1/1979")
				.Assert(9, rightAscensionDeclination.rightAscensionHours)
				.Assert(12, rightAscensionDeclination.rightAscensionMinutes)
				.Assert(20.18, rightAscensionDeclination.rightAscensionSeconds)
				.Assert(14, rightAscensionDeclination.declinationDegrees)
				.Assert(16, rightAscensionDeclination.declinationMinutes)
				.Assert(9.12, rightAscensionDeclination.declinationSeconds);
	}

	public void testNutationInEclipticLongitudeAndObliquity() {
		TestLib testLib = new TestLib();

		Nutation nutation = paCoordinates.nutationInEclipticLongitudeAndObliquity(1, 9, 1988);

		testLib.setTestName("Nutation in Ecliptic Longitude and Obliquity for Greenwich Date 9/1/1988")
				.Assert(0.001525808, PAUtil.round(nutation.eclipticLongitudeDegrees, 9))
				.Assert(0.0025671, PAUtil.round(nutation.obliquityDegrees, 7));
	}

	public void testCorrectForAberration() {
		TestLib testLib = new TestLib();

		Aberration aberration = paCoordinates.correctForAberration(0, 0, 0, 8, 9, 1988, 352, 37, 10.1, -1, 32, 56.4);

		testLib.setTestName(
				"Ecliptic Coordinates, corrected for aberration, for UT 00:00:00 and Greenwich Date 9/8/1988 and Ecliptic Longitude 352d 37m 10.1s and Ecliptic Latitude -1d 32m 56.4s")
				.Assert(352, aberration.apparentEclLongDeg).Assert(37, aberration.apparentEclLongMin)
				.Assert(30.45, aberration.apparentEclLongSec).Assert(-1, aberration.apparentEclLatDeg)
				.Assert(32, aberration.apparentEclLatMin).Assert(56.33, aberration.apparentEclLatSec);
	}

	public void testRefraction() {
		TestLib testLib = new TestLib();

		RightAscensionDeclination correctedCoordinates = paCoordinates.atmosphericRefraction(23, 14, 0, 40, 10, 0,
				CoordinateType.TRUE, 0.17, 51.2036110, 0, 0, 23, 3, 1987, 1, 1, 24, 1012, 21.7);

		testLib.setTestName("RA/Dec, corrected for atmospheric refraction")
				.Assert(23, correctedCoordinates.rightAscensionHours)
				.Assert(13, correctedCoordinates.rightAscensionMinutes)
				.Assert(44.74, correctedCoordinates.rightAscensionSeconds)
				.Assert(40, correctedCoordinates.declinationDegrees).Assert(19, correctedCoordinates.declinationMinutes)
				.Assert(45.76, correctedCoordinates.declinationSeconds);
	}

	public void testParallax() {
		TestLib testLib = new TestLib();

		RightAscensionDeclination correctedCoordinates = paCoordinates.correctionsForGeocentricParallax(22, 35, 19, -7,
				41, 13, CoordinateType.TRUE, 1.019167, -100, 50, 60, 0, -6, 26, 2, 1979, 10, 45, 0);

		testLib.setTestName("RA/Dec, corrected for geocentric parallax")
				.Assert(22, correctedCoordinates.rightAscensionHours)
				.Assert(36, correctedCoordinates.rightAscensionMinutes)
				.Assert(43.22, correctedCoordinates.rightAscensionSeconds)
				.Assert(-8, correctedCoordinates.declinationDegrees).Assert(32, correctedCoordinates.declinationMinutes)
				.Assert(17.4, correctedCoordinates.declinationSeconds);
	}
}
