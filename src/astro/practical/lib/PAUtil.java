package astro.practical.lib;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PAUtil {
	/**
	 * Round a double value to specified number of places.
	 */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Determine if year is a leap year.
	 */
	public static boolean isLeapYear(int inputYear) {
		double year = inputYear;

		if (year % 4 == 0) {
			if (year % 100 == 0)
				return (year % 400 == 0) ? true : false;
			else
				return true;
		} else
			return false;
	}

}
