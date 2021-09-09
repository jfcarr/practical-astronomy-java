package astro.practical.test;

import astro.practical.lib.PAUtil;
import astro.practical.types.PAWarningFlag;
import astro.practical.types.RiseSetStatus;

public class TestLib {
	public String testName = "No test name specified";

	/**
	 * Log success/fail information to the console.
	 */
	private boolean LogMessage(Boolean passed, Object expectedValue, Object actualValue) {
		if (passed) {
			System.out.println("[Passed] [" + testName + "] Expected " + String.valueOf(expectedValue) + ", got "
					+ String.valueOf(actualValue));
		} else {
			System.out.println("[Failed] [" + testName + "] Expected " + String.valueOf(expectedValue) + ", got "
					+ String.valueOf(actualValue));
			throw new Error("Test failed.  Exiting...");
		}

		return passed;
	}

	/**
	 * Assign a descriptive name for the current test run.
	 */
	public TestLib setTestName(String testName) {
		this.testName = testName;

		return this;
	}

	/**
	 * Assert that two int values are equal.
	 */
	public TestLib Assert(int expectedValue, int actualValue) {
		LogMessage((expectedValue == actualValue), expectedValue, actualValue);

		return this;
	}

	/**
	 * Assert that two String values are equal.
	 */
	public TestLib Assert(String expectedValue, String actualValue) {
		LogMessage((expectedValue == actualValue), expectedValue, actualValue);

		return this;
	}

	/**
	 * Assert that two double values are equal.
	 */
	public TestLib Assert(double expectedValue, double actualValue) {
		LogMessage((PAUtil.round(expectedValue, 8) == PAUtil.round(actualValue, 8)), expectedValue, actualValue);

		return this;
	}

	/**
	 * Assert that two PAWarningFlag values are equal.
	 */
	public TestLib Assert(PAWarningFlag expectedValue, PAWarningFlag actualValue) {
		LogMessage((expectedValue == actualValue), expectedValue, actualValue);

		return this;
	}

	/**
	 * Assert that two RiseSetStatus values are equal.
	 */
	public TestLib Assert(RiseSetStatus expectedValue, RiseSetStatus actualValue) {
		LogMessage((expectedValue == actualValue), expectedValue, actualValue);

		return this;
	}
}
