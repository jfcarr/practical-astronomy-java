public class Main {
	public static void main(String[] args) {
		testDateTime();
		testCoordinates();
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
	}
}
