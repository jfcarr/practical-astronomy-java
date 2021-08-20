import astro.practical.containers.CivilDate;
import astro.practical.test.TestLib;

public class TestDateTime {
	astro.practical.lib.PADateTime paDateTime;

	public TestDateTime() {
		paDateTime = new astro.practical.lib.PADateTime();
	}

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

	public void testCivilDateToDayNumber() {
		TestLib testLib = new TestLib();

		testLib.setTestName("Day Number for 1/1/2000").Assert(1, paDateTime.civilDateToDayNumber(1, 1, 2000));
		testLib.setTestName("Day Number for 3/1/2000").Assert(61, paDateTime.civilDateToDayNumber(3, 1, 2000));
		testLib.setTestName("Day Number for 6/1/2003").Assert(152, paDateTime.civilDateToDayNumber(6, 1, 2003));
		testLib.setTestName("Day Number for 11/27/2009").Assert(331, paDateTime.civilDateToDayNumber(11, 27, 2009));
	}
}
