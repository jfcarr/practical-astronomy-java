public class Main {
	public static void main(String[] args) {
		testDateTime();
	}

	public static void testDateTime() {
		TestDateTime testDateTime = new TestDateTime();

		testDateTime.testDateOfEaster();
		testDateTime.testCivilDateToDayNumber();
	}
}
