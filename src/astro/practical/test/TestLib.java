package astro.practical.test;

import astro.practical.lib.PAUtil;

public class TestLib {
	public static String testName = "No test name specified";

	private static boolean LogMessage(Boolean passed, Object expectedValue, Object actualValue) {
		if (passed) {
			System.out.println("[Passed] [" + testName + "] Expected " + String.valueOf(expectedValue) + " got "
					+ String.valueOf(actualValue));
		} else {
			System.out.println("[Failed] [" + testName + "] Expected " + String.valueOf(expectedValue) + " got "
					+ String.valueOf(actualValue));
			throw new Error("Test failed.  Exiting...");
		}

		return passed;
	}

	public static boolean Assert(int expectedValue, int actualValue) {
		return LogMessage((expectedValue == actualValue), expectedValue, actualValue);
	}

	public static boolean Assert(String expectedValue, String actualValue) {
		return LogMessage((expectedValue == actualValue), expectedValue, actualValue);
	}

	public static boolean Assert(Double expectedValue, Double actualValue) {
		return LogMessage((PAUtil.round(expectedValue, 8) == PAUtil.round(actualValue, 8)), expectedValue, actualValue);
	}
}
