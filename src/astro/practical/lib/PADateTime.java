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

	/**
	 * Convert Civil Time (hours,minutes,seconds) to Decimal Hours
	 */
	public double civilTimeToDecimalHours(double hours, double minutes, double seconds) {
		return PAMacros.hmsToDH(hours, minutes, seconds);
	}

	/**
	 * Convert Decimal Hours to Civil Time (hours,minutes,seconds)
	 */
	public CivilTime decimalHoursToCivilTime(double decimalHours) {
		double hours = PAMacros.decimalHoursHour(decimalHours);
		double minutes = PAMacros.decimalHoursMinute(decimalHours);
		double seconds = PAMacros.decimalHoursSecond(decimalHours);

		return new CivilTime(hours, minutes, seconds);
	}

	/**
	 * Convert local Civil Time to Universal Time
	 */
	public UniversalDateTime localCivilTimeToUniversalTime(double lctHours, double lctMinutes, double lctSeconds,
			boolean isDaylightSavings, int zoneCorrection, double localDay, int localMonth, int localYear) {
		double lct = this.civilTimeToDecimalHours(lctHours, lctMinutes, lctSeconds);

		int daylightSavingsOffset = (isDaylightSavings) ? 1 : 0;

		double utInterim = lct - daylightSavingsOffset - zoneCorrection;
		double gdayInterim = localDay + (utInterim / 24);

		double jd = PAMacros.civilDateToJulianDate(gdayInterim, localMonth, localYear);

		double gDay = PAMacros.julianDateDay(jd);
		int gMonth = PAMacros.julianDateMonth(jd);
		int gYear = PAMacros.julianDateYear(jd);

		double ut = 24 * (gDay - Math.floor(gDay));

		return new UniversalDateTime(PAMacros.decimalHoursHour(ut), PAMacros.decimalHoursMinute(ut),
				(int) PAMacros.decimalHoursSecond(ut), (int) Math.floor(gDay), gMonth, gYear);
	}

	/**
	 * Convert Universal Time to local Civil Time
	 */
	public CivilDateTime universalTimeToLocalCivilTime(double utHours, double utMinutes, double utSeconds,
			boolean isDaylightSavings, int zoneCorrection, int gwDay, int gwMonth, int gwYear) {
		int dstValue = (isDaylightSavings) ? 1 : 0;
		double ut = PAMacros.hmsToDH(utHours, utMinutes, utSeconds);
		double zoneTime = ut + zoneCorrection;
		double localTime = zoneTime + dstValue;
		double localJDPlusLocalTime = PAMacros.civilDateToJulianDate(gwDay, gwMonth, gwYear) + (localTime / 24);
		double localDay = PAMacros.julianDateDay(localJDPlusLocalTime);
		double integerDay = Math.floor(localDay);
		int localMonth = PAMacros.julianDateMonth(localJDPlusLocalTime);
		int localYear = PAMacros.julianDateYear(localJDPlusLocalTime);

		double lct = 24 * (localDay - integerDay);

		return new CivilDateTime(PAMacros.decimalHoursHour(lct), PAMacros.decimalHoursMinute(lct),
				(int) PAMacros.decimalHoursSecond(lct), (int) integerDay, localMonth, localYear);
	}
}
