import astro.practical.lib.PAUtil;
import astro.practical.models.CivilDate;
import astro.practical.models.CivilDateTime;
import astro.practical.models.CivilTime;
import astro.practical.models.GreenwichSiderealTime;
import astro.practical.models.LocalSiderealTime;
import astro.practical.models.UniversalDateTime;
import astro.practical.models.UniversalTime;
import astro.practical.test.TestLib;
import astro.practical.types.WarningFlag;

public class TestDateTime {
	astro.practical.lib.PADateTime paDateTime;

	public TestDateTime() {
		paDateTime = new astro.practical.lib.PADateTime();
	}

	/**
	 * Test Date of Easter.
	 */
	public void testDateOfEaster() {
		CivilDate dateOfEaster;

		TestLib testLib = new TestLib();

		dateOfEaster = paDateTime.getDateOfEaster(2003);

		testLib.setTestName("Date of Easter for 2003").Assert(4, dateOfEaster.month)
				.Assert((double) 20, dateOfEaster.day).Assert(2003, dateOfEaster.year);

		dateOfEaster = paDateTime.getDateOfEaster(2019);

		testLib.setTestName("Date of Easter for 2019").Assert(4, dateOfEaster.month)
				.Assert((double) 21, dateOfEaster.day).Assert(2019, dateOfEaster.year);

		dateOfEaster = paDateTime.getDateOfEaster(2020);

		testLib.setTestName("Date of Easter for 2020").Assert(4, dateOfEaster.month)
				.Assert((double) 12, dateOfEaster.day).Assert(2020, dateOfEaster.year);
	}

	/**
	 * Test Civil Date to Day Number.
	 */
	public void testCivilDateToDayNumber() {
		TestLib testLib = new TestLib();

		testLib.setTestName("Day Number for 1/1/2000").Assert(1, paDateTime.civilDateToDayNumber(1, 1, 2000));
		testLib.setTestName("Day Number for 3/1/2000").Assert(61, paDateTime.civilDateToDayNumber(3, 1, 2000));
		testLib.setTestName("Day Number for 6/1/2003").Assert(152, paDateTime.civilDateToDayNumber(6, 1, 2003));
		testLib.setTestName("Day Number for 11/27/2009").Assert(331, paDateTime.civilDateToDayNumber(11, 27, 2009));
	}

	/**
	 * Test Civil Time to/from Decimal Hours.
	 */
	public void testCivilTimeToFromDecimalHours() {
		TestLib testLib = new TestLib();

		testLib.setTestName("Convert Civil Time to Decimal Hours").Assert(18.52416667,
				PAUtil.round(paDateTime.civilTimeToDecimalHours((double) 18, (double) 31, (double) 27), 8));

		CivilTime civilTime = paDateTime.decimalHoursToCivilTime(18.52416667);

		testLib.setTestName("Convert Decimal Hours to Civil Time").Assert((double) 18, civilTime.hours)
				.Assert((double) 31, civilTime.minutes).Assert((double) 27, civilTime.seconds);
	}

	/**
	 * Test Local Civil Time to/from Universal Time.
	 */
	public void testLocalCivilTimeToFromUniversalTime() {
		TestLib testLib = new TestLib();

		UniversalDateTime uDT = paDateTime.localCivilTimeToUniversalTime(3, 37, 0, true, 4, 1, 7, 2013);

		testLib.setTestName("Convert Local Civil Time to Universal Time").Assert(22, uDT.hours).Assert(37, uDT.minutes)
				.Assert(0, uDT.seconds).Assert(30, uDT.day).Assert(6, uDT.month).Assert(2013, uDT.year);

		CivilDateTime cDT = paDateTime.universalTimeToLocalCivilTime(22, 37, 0, true, 4, 30, 6, 2013);

		testLib.setTestName("Convert Universal Time to Local Civil Time").Assert(3, cDT.hours).Assert(37, cDT.minutes)
				.Assert(0, cDT.seconds).Assert(1, cDT.day).Assert(7, cDT.month).Assert(2013, cDT.year);
	}

	/**
	 * Test Universal Time to/from Greenwich Sidereal Time.
	 */
	public void testUniversalTimeToFromGreenwichSiderealTime() {
		TestLib testLib = new TestLib();

		GreenwichSiderealTime gST = paDateTime.universalTimeToGreenwichSiderealTime(14, 36, 51.67, 22, 4, 1980);

		testLib.setTestName("Convert Universal Time to Greenwich Sidereal Time").Assert(4.0, gST.hours)
				.Assert(40.0, gST.minutes).Assert(5.23, gST.seconds);

		UniversalTime uT = paDateTime.greenwichSiderealTimeToUniversalTime(4, 40, 5.23, 22, 4, 1980);

		testLib.setTestName("Convert Greenwich Sidereal Time to Universal Time").Assert(14.0, uT.hours)
				.Assert(36.0, uT.minutes).Assert(51.67, uT.seconds).Assert(WarningFlag.OK, uT.warningFlag);
	}

	/**
	 * Test Greenwich Sidereal Time to/from Local Sidereal Time.
	 */
	public void testGreenwichSiderealTimeToFromLocalSiderealTime() {
		TestLib testLib = new TestLib();

		LocalSiderealTime lST = paDateTime.greenwichSiderealTimeToLocalSiderealTime(4, 40, 5.23, -64);

		testLib.setTestName("Convert Greenwich Sidereal Time to Local Sidereal Time").Assert(0.0, lST.hours)
				.Assert(24.0, lST.minutes).Assert(5.23, lST.seconds);

		GreenwichSiderealTime gST = paDateTime.localSiderealTimeToGreenwichSiderealTime(0, 24, 5.23, -64);

		testLib.setTestName("Convert Local Sidereal Time to Greenwich Sidereal Time").Assert(4.0, gST.hours)
				.Assert(40.0, gST.minutes).Assert(5.23, gST.seconds);
	}
}
