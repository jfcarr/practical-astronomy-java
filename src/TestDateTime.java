import astro.practical.containers.CivilDate;
import astro.practical.test.TestLib;

public class TestDateTime {
	astro.practical.lib.PADateTime paDateTime;

	public TestDateTime() {
		paDateTime = new astro.practical.lib.PADateTime();
	}

	public void testDateOfEaster() {
		CivilDate dateOfEaster;

		dateOfEaster = paDateTime.getDateOfEaster(2003);
		TestLib.testName = "Date of Easter for 2003";
		TestLib.Assert(4, dateOfEaster.month);
		TestLib.Assert((double) 20, dateOfEaster.day);
		TestLib.Assert(2003, dateOfEaster.year);

		dateOfEaster = paDateTime.getDateOfEaster(2019);
		TestLib.testName = "Date of Easter for 2019";
		TestLib.Assert(4, dateOfEaster.month);
		TestLib.Assert((double) 21, dateOfEaster.day);
		TestLib.Assert(2019, dateOfEaster.year);

		dateOfEaster = paDateTime.getDateOfEaster(2020);
		TestLib.testName = "Date of Easter for 2020";
		TestLib.Assert(4, dateOfEaster.month);
		TestLib.Assert((double) 12, dateOfEaster.day);
		TestLib.Assert(2020, dateOfEaster.year);
	}
}
