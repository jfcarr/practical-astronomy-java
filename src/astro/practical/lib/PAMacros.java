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

	/**
	 * Convert Right Ascension to Hour Angle
	 * 
	 * Original macro name: RAHA
	 */
	public static double rightAscensionToHourAngle(double raHours, double raMinutes, double raSeconds, double lctHours,
			double lctMinutes, double lctSeconds, int daylightSaving, int zoneCorrection, double localDay,
			int localMonth, int localYear, double geographicalLongitude) {
		double a = localCivilTimeToUniversalTime(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection,
				localDay, localMonth, localYear);
		double b = localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection,
				localDay, localMonth, localYear);
		int c = localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection, localDay,
				localMonth, localYear);
		int d = localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection, localDay,
				localMonth, localYear);
		double e = universalTimeToGreenwichSiderealTime(a, 0, 0, b, c, d);
		double f = greenwichSiderealTimeToLocalSiderealTime(e, 0, 0, geographicalLongitude);
		double g = hmsToDH(raHours, raMinutes, raSeconds);
		double h = f - g;

		return (h < 0) ? 24 + h : h;
	}

	/**
	 * Convert Hour Angle to Right Ascension
	 * 
	 * Original macro name: HARA
	 */
	public static double hourAngleToRightAscension(double hourAngleHours, double hourAngleMinutes,
			double hourAngleSeconds, double lctHours, double lctMinutes, double lctSeconds, int daylightSaving,
			int zoneCorrection, double localDay, int localMonth, int localYear, double geographicalLongitude) {
		double a = localCivilTimeToUniversalTime(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection,
				localDay, localMonth, localYear);
		double b = localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection,
				localDay, localMonth, localYear);
		int c = localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection, localDay,
				localMonth, localYear);
		int d = localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection, localDay,
				localMonth, localYear);
		double e = universalTimeToGreenwichSiderealTime(a, 0, 0, b, c, d);
		double f = greenwichSiderealTimeToLocalSiderealTime(e, 0, 00, geographicalLongitude);
		double g = hmsToDH(hourAngleHours, hourAngleMinutes, hourAngleSeconds);
		double h = f - g;

		return (h < 0) ? 24 + h : h;
	}

	/**
	 * Convert Local Civil Time to Universal Time
	 * 
	 * Original macro name: LctUT
	 */
	public static double localCivilTimeToUniversalTime(double lctHours, double lctMinutes, double lctSeconds,
			int daylightSaving, int zoneCorrection, double localDay, int localMonth, int localYear) {
		double a = hmsToDH(lctHours, lctMinutes, lctSeconds);
		double b = a - daylightSaving - zoneCorrection;
		double c = localDay + (b / 24);
		double d = civilDateToJulianDate(c, localMonth, localYear);
		double e = julianDateDay(d);
		double e1 = Math.floor(e);

		return 24 * (e - e1);
	}

	/**
	 * Determine Greenwich Day for Local Time
	 * 
	 * Original macro name: LctGDay
	 */
	public static double localCivilTimeGreenwichDay(double lctHours, double lctMinutes, double lctSeconds,
			int daylightSaving, int zoneCorrection, double localDay, int localMonth, int localYear) {
		double a = hmsToDH(lctHours, lctMinutes, lctSeconds);
		double b = a - daylightSaving - zoneCorrection;
		double c = localDay + (b / 24);
		double d = civilDateToJulianDate(c, localMonth, localYear);
		double e = julianDateDay(d);

		return Math.floor(e);
	}

	/**
	 * Determine Greenwich Month for Local Time
	 * 
	 * Original macro name: LctGMonth
	 */
	public static int localCivilTimeGreenwichMonth(double lctHours, double lctMinutes, double lctSeconds,
			int daylightSaving, int zoneCorrection, double localDay, int localMonth, int localYear) {
		double a = hmsToDH(lctHours, lctMinutes, lctSeconds);
		double b = a - daylightSaving - zoneCorrection;
		double c = localDay + (b / 24);
		double d = civilDateToJulianDate(c, localMonth, localYear);

		return julianDateMonth(d);
	}

	/**
	 * Determine Greenwich Year for Local Time
	 * 
	 * Original macro name: LctGYear
	 */
	public static int localCivilTimeGreenwichYear(double lctHours, double lctMinutes, double lctSeconds,
			int daylightSaving, int zoneCorrection, double localDay, int localMonth, int localYear) {
		double a = hmsToDH(lctHours, lctMinutes, lctSeconds);
		double b = a - daylightSaving - zoneCorrection;
		double c = localDay + (b / 24);
		double d = civilDateToJulianDate(c, localMonth, localYear);

		return julianDateYear(d);
	}

	/**
	 * Convert Universal Time to Greenwich Sidereal Time
	 * 
	 * Original macro name: UTGST
	 */
	public static double universalTimeToGreenwichSiderealTime(double uHours, double uMinutes, double uSeconds,
			double greenwichDay, int greenwichMonth, int greenwichYear) {
		double a = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear);
		double b = a - 2451545;
		double c = b / 36525;
		double d = 6.697374558 + (2400.051336 * c) + (0.000025862 * c * c);
		double e = d - (24 * Math.floor(d / 24));
		double f = hmsToDH(uHours, uMinutes, uSeconds);
		double g = f * 1.002737909;
		double h = e + g;

		return h - (24 * Math.floor(h / 24));
	}

	/**
	 * Convert Greenwich Sidereal Time to Local Sidereal Time
	 * 
	 * Original macro name: GSTLST
	 */
	public static double greenwichSiderealTimeToLocalSiderealTime(double greenwichHours, double greenwichMinutes,
			double greenwichSeconds, double geographicalLongitude) {
		double a = hmsToDH(greenwichHours, greenwichMinutes, greenwichSeconds);
		double b = geographicalLongitude / 15;
		double c = a + b;

		return c - (24 * Math.floor(c / 24));
	}
}
