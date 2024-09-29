public class Main {
	public static void main(String[] args) {
		testDateTime();
		testCoordinates();
		testSun();
		testPlanet();
		testComet();
		testBinary();
		testMoon();
	}

	public static void testDateTime() {
		TestDateTime testDateTime = new TestDateTime();

		testDateTime.testDateOfEaster();
		testDateTime.testCivilDateToDayNumber();
		testDateTime.testCivilTimeToFromDecimalHours();
		testDateTime.testLocalCivilTimeToFromUniversalTime();
		testDateTime.testUniversalTimeToFromGreenwichSiderealTime();
		testDateTime.testGreenwichSiderealTimeToFromLocalSiderealTime();
	}

	public static void testCoordinates() {
		TestCoordinates testCoordinates = new TestCoordinates();

		testCoordinates.testAngleToFromDecimalDegrees();
		testCoordinates.testRightAscensionToFromHourAngle();
		testCoordinates.testEquatorialCoordinatesToFromHorizonCoordinates();
		testCoordinates.testMeanObliquityOfTheEcliptic();
		testCoordinates.testEclipticCoordinateToFromEquatorialCoordinate();
		testCoordinates.testEquatorialCoordinateToFromGalacticCoordinate();
		testCoordinates.testAngleBetweenTwoObjects();
		testCoordinates.testRisingAndSetting();
		testCoordinates.testCorrectForPrecession();
		testCoordinates.testNutationInEclipticLongitudeAndObliquity();
		testCoordinates.testCorrectForAberration();
		testCoordinates.testRefraction();
		testCoordinates.testParallax();
		testCoordinates.testHeliographicCoordinates();
		testCoordinates.testCarringtonRotationNumber();
		testCoordinates.testSelenographicCoordinates1();
		testCoordinates.testSelenographicCoordinates2();
	}

	public static void testSun() {
		TestSun testSun = new TestSun();

		testSun.testApproximatePositionOfSun();
		testSun.testPrecisePositionOfSun();
		testSun.testSunDistanceAndAngularSize();
		testSun.testSunriseAndSunset();
		testSun.testMorningAndEveningTwilight();
		testSun.testEquationOfTime();
		testSun.testSolarElongation();
	}

	public static void testPlanet() {
		TestPlanet testPlanet = new TestPlanet();

		testPlanet.testApproximatePositionOfPlanet();
		testPlanet.testPrecisePositionOfPlanet();
		testPlanet.testVisualAspectsOfAPlanet();
	}

	public static void testComet() {
		TestComet testComet = new TestComet();

		testComet.testPositionOfEllipticalComet();
		testComet.testPositionOfParabolicComet();
	}

	public static void testBinary() {
		TestBinary testBinary = new TestBinary();

		testBinary.testBinaryStarOrbit();
	}

	public static void testMoon() {
		TestMoon testMoon = new TestMoon();

		testMoon.testApproximatePositionOfMoon();
		testMoon.testPrecisePositionOfMoon();
		testMoon.testMoonPhase();
		testMoon.testTimesOfNewMoonAndFullMoon();
	}
}
