package astro.practical.test;

import astro.practical.lib.PAUtil;

public class TestLib {
	public String testName = "No test name specified";

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

	public TestLib setTestName(String testName) {
		this.testName = testName;

		return this;
	}

	public TestLib Assert(int expectedValue, int actualValue) {
		LogMessage((expectedValue == actualValue), expectedValue, actualValue);

		return this;
	}

	public TestLib Assert(String expectedValue, String actualValue) {
		LogMessage((expectedValue == actualValue), expectedValue, actualValue);

		return this;
	}

	public TestLib Assert(Double expectedValue, Double actualValue) {
		LogMessage((PAUtil.round(expectedValue, 8) == PAUtil.round(actualValue, 8)), expectedValue, actualValue);

		return this;
	}
}
