package astro.practical.lib;

public class PAMacros {

	/**
	 * Convert a Civil Time (hours,minutes,seconds) to Decimal Hours
	 * 
	 * Original macro name: HMSDH
	 */
	public static double hmsToDH(double hours, double minutes, double seconds) {
		double fHours = hours;
		double fMinutes = minutes;
		double fSeconds = seconds;

		double a = Math.abs(fSeconds) / 60;
		double b = (Math.abs(fMinutes) + a) / 60;
		double c = Math.abs(fHours) + b;

		return (fHours < 0 || fMinutes < 0 || fSeconds < 0) ? -c : c;
	}

	/**
	 * Return the hour part of a Decimal Hours
	 * 
	 * Original macro name: DHHour
	 */
	public static int decimalHoursHour(double decimalHours) {
		double a = Math.abs(decimalHours);
		double b = a * 3600;
		double c = PAUtil.round((b - 60 * Math.floor(b / 60)), 2);
		double e = (c == 60) ? b + 60 : b;

		return (decimalHours < 0) ? (int) -(Math.floor(e / 3600)) : (int) Math.floor(e / 3600);
	}

	/**
	 * Return the minutes part of a Decimal Hours
	 * 
	 * Original macro name: DHMin
	 */
	public static int decimalHoursMinute(double decimalHours) {
		double a = Math.abs(decimalHours);
		double b = a * 3600;
		double c = PAUtil.round(b - 60 * Math.floor(b / 60), 2);
		double e = (c == 60) ? b + 60 : b;

		return (int) Math.floor(e / 60) % 60;
	}

	/**
	 * Return the seconds part of a Decimal Hours
	 * 
	 * Original macro name: DHSec
	 */
	public static double decimalHoursSecond(double decimalHours) {
		double a = Math.abs(decimalHours);
		double b = a * 3600;
		double c = PAUtil.round(b - 60 * Math.floor(b / 60), 2);
		double d = (c == 60) ? 0 : c;

		return d;
	}

	/**
	 * Convert a Greenwich Date/Civil Date (day,month,year) to Julian Date
	 * 
	 * Original macro name: CDJD
	 */
	public static double civilDateToJulianDate(double day, double month, double year) {
		double fDay = (double) day;
		double fMonth = (double) month;
		double fYear = (double) year;

		double y = (fMonth < 3) ? fYear - 1 : fYear;
		double m = (fMonth < 3) ? fMonth + 12 : fMonth;

		double b;

		if (fYear > 1582) {
			double a = Math.floor(y / 100);
			b = 2 - a + Math.floor(a / 4);
		} else {
			if (fYear == 1582 && fMonth > 10) {
				double a = Math.floor(y / 100);
				b = 2 - a + Math.floor(a / 4);
			} else {
				if (fYear == 1582 && fMonth == 10 && fDay >= 15) {
					double a = Math.floor(y / 100);
					b = 2 - a + Math.floor(a / 4);
				} else
					b = 0;
			}
		}

		double c = (y < 0) ? Math.floor(((365.25 * y) - 0.75)) : Math.floor(365.25 * y);
		double d = Math.floor(30.6001 * (m + 1.0));

		return b + c + d + fDay + 1720994.5;
	}

	/**
	 * Returns the day part of a Julian Date
	 * 
	 * Original macro name: JDCDay
	 */
	public static double julianDateDay(double julianDate) {
		double i = Math.floor(julianDate + 0.5);
		double f = julianDate + 0.5 - i;
		double a = Math.floor((i - 1867216.25) / 36524.25);
		double b = (i > 2299160) ? i + 1 + a - Math.floor(a / 4) : i;
		double c = b + 1524;
		double d = Math.floor((c - 122.1) / 365.25);
		double e = Math.floor(365.25 * d);
		double g = Math.floor((c - e) / 30.6001);

		return c - e + f - Math.floor(30.6001 * g);
	}

	/**
	 * Returns the month part of a Julian Date
	 * 
	 * Original macro name: JDCMonth
	 */
	public static int julianDateMonth(double julianDate) {
		double i = Math.floor(julianDate + 0.5);
		double a = Math.floor((i - 1867216.25) / 36524.25);
		double b = (i > 2299160) ? i + 1 + a - Math.floor(a / 4) : i;
		double c = b + 1524;
		double d = Math.floor((c - 122.1) / 365.25);
		double e = Math.floor(365.25 * d);
		double g = Math.floor((c - e) / 30.6001);

		double returnValue = (g < 13.5) ? g - 1 : g - 13;

		return (int) returnValue;
	}

	/**
	 * Returns the year part of a Julian Date
	 * 
	 * Original macro name: JDCYear
	 */
	public static int julianDateYear(double julianDate) {
		double i = Math.floor(julianDate + 0.5);
		double a = Math.floor((i - 1867216.25) / 36524.25);
		double b = (i > 2299160) ? i + 1.0 + a - Math.floor(a / 4.0) : i;
		double c = b + 1524;
		double d = Math.floor((c - 122.1) / 365.25);
		double e = Math.floor(365.25 * d);
		double g = Math.floor((c - e) / 30.6001);
		double h = (g < 13.5) ? g - 1 : g - 13;

		double returnValue = (h > 2.5) ? d - 4716 : d - 4715;

		return (int) returnValue;
	}
}
