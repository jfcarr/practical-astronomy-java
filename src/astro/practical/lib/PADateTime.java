package astro.practical.lib;

import astro.practical.containers.*;

/**
 * Date and Time Calculations
 */
public class PADateTime {
	/**
	 * Gets the date of Easter for the year specified.
	 */
	public CivilDate getDateOfEaster(int inputYear) {
		double year = inputYear;

		double a = year % 19;
		double b = Math.floor(year / 100);
		double c = year % 100;
		double d = Math.floor(b / 4);
		double e = b % 4;
		double f = Math.floor((b + 8) / 25);
		double g = Math.floor((b - f + 1) / 3);
		double h = ((19 * a) + b - d - g + 15) % 30;
		double i = Math.floor(c / 4);
		double k = c % 4;
		double l = (32 + 2 * (e + i) - h - k) % 7;
		double m = Math.floor((a + (11 * h) + (22 * l)) / 451);
		double n = Math.floor((h + l - (7 * m) + 114) / 31);
		double p = (h + l - (7 * m) + 114) % 31;

		double day = p + 1;
		double month = n;

		return new CivilDate((int) month, day, (int) year);
	}

	/**
	 * Calculate day number for a date.
	 */
	public int civilDateToDayNumber(int month, int day, int year) {
		if (month <= 2) {
			month = month - 1;
			month = (PAUtil.isLeapYear(year)) ? month * 62 : month * 63;
			month = (int) Math.floor((double) month / 2);
		} else {
			month = (int) Math.floor(((double) month + 1) * 30.6);
			month = (PAUtil.isLeapYear(year)) ? month - 62 : month - 63;
		}

		return month + day;
	}

}
