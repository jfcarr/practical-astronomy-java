package astro.practical.lib;

import astro.practical.types.CoordinateType;
import astro.practical.types.ParallaxHelper;

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

	/**
	 * Convert Degrees Minutes Seconds to Decimal Degrees
	 * 
	 * Original macro name: DMSDD
	 */
	public static double degreesMinutesSecondsToDecimalDegrees(double degrees, double minutes, double seconds) {
		double a = Math.abs(seconds) / 60;
		double b = (Math.abs(minutes) + a) / 60;
		double c = Math.abs(degrees) + b;

		return (degrees < 0 || minutes < 0 || seconds < 0) ? -c : c;
	}

	/**
	 * Convert W to Degrees
	 * 
	 * Original macro name: Degrees
	 */
	public static double wToDegrees(double w) {
		return w * 57.29577951;
	}

	/**
	 * Convert Equatorial Coordinates to Azimuth (in decimal degrees)
	 * 
	 * Original macro name: EQAz
	 */
	public static double equatorialCoordinatesToAzimuth(double hourAngleHours, double hourAngleMinutes,
			double hourAngleSeconds, double declinationDegrees, double declinationMinutes, double declinationSeconds,
			double geographicalLatitude) {
		double a = hmsToDH(hourAngleHours, hourAngleMinutes, hourAngleSeconds);
		double b = a * 15;
		double c = Math.toRadians(b);
		double d = degreesMinutesSecondsToDecimalDegrees(declinationDegrees, declinationMinutes, declinationSeconds);
		double e = Math.toRadians(d);
		double f = Math.toRadians(geographicalLatitude);
		double g = Math.sin(e) * Math.sin(f) + Math.cos(e) * Math.cos(f) * Math.cos(c);
		double h = -Math.cos(e) * Math.cos(f) * Math.sin(c);
		double i = Math.sin(e) - (Math.sin(f) * g);
		double j = wToDegrees(Math.atan2(h, i));

		return j - 360.0 * Math.floor(j / 360);
	}

	/**
	 * Convert Equatorial Coordinates to Altitude (in decimal degrees)
	 * 
	 * Original macro name: EQAlt
	 */
	public static double equatorialCoordinatesToAltitude(double hourAngleHours, double hourAngleMinutes,
			double hourAngleSeconds, double declinationDegrees, double declinationMinutes, double declinationSeconds,
			double geographicalLatitude) {
		double a = hmsToDH(hourAngleHours, hourAngleMinutes, hourAngleSeconds);
		double b = a * 15;
		double c = Math.toRadians(b);
		double d = degreesMinutesSecondsToDecimalDegrees(declinationDegrees, declinationMinutes, declinationSeconds);
		double e = Math.toRadians(d);
		double f = Math.toRadians(geographicalLatitude);
		double g = Math.sin(e) * Math.sin(f) + Math.cos(e) * Math.cos(f) * Math.cos(c);

		return wToDegrees(Math.asin(g));
	}

	/**
	 * Return Degrees part of Decimal Degrees
	 * 
	 * Original macro name: DDDeg
	 */
	public static double decimalDegreesDegrees(double decimalDegrees) {
		double a = Math.abs(decimalDegrees);
		double b = a * 3600;
		double c = PAUtil.round(b - 60 * Math.floor(b / 60), 2);
		double e = (c == 60) ? 60 : b;

		return (decimalDegrees < 0) ? -(Math.floor(e / 3600)) : Math.floor(e / 3600);
	}

	/**
	 * Return Minutes part of Decimal Degrees
	 * 
	 * Original macro name: DDMin
	 */
	public static double decimalDegreesMinutes(double decimalDegrees) {
		double a = Math.abs(decimalDegrees);
		double b = a * 3600;
		double c = PAUtil.round(b - 60 * Math.floor(b / 60), 2);
		double e = (c == 60) ? b + 60 : b;

		return Math.floor(e / 60) % 60;
	}

	/**
	 * Return Seconds part of Decimal Degrees
	 * 
	 * Original macro name: DDSec
	 */
	public static double decimalDegreesSeconds(double decimalDegrees) {
		double a = Math.abs(decimalDegrees);
		double b = a * 3600;
		double c = PAUtil.round(b - 60 * Math.floor(b / 60), 2);
		double d = (c == 60) ? 0 : c;

		return d;
	}

	/**
	 * Convert Decimal Degrees to Degree-Hours
	 * 
	 * Original macro name: DDDH
	 */
	public static double decimalDegreesToDegreeHours(double decimalDegrees) {
		return decimalDegrees / 15;
	}

	/**
	 * Convert Degree-Hours to Decimal Degrees
	 * 
	 * Original macro name: DHDD
	 */
	public static double degreeHoursToDecimalDegrees(double degreeHours) {
		return degreeHours * 15;
	}

	/**
	 * Convert Horizon Coordinates to Declination (in decimal degrees)
	 * 
	 * Original macro name: HORDec
	 */
	public static double horizonCoordinatesToDeclination(double azimuthDegrees, double azimuthMinutes,
			double azimuthSeconds, double altitudeDegrees, double altitudeMinutes, double altitudeSeconds,
			double geographicalLatitude) {
		double a = degreesMinutesSecondsToDecimalDegrees(azimuthDegrees, azimuthMinutes, azimuthSeconds);
		double b = degreesMinutesSecondsToDecimalDegrees(altitudeDegrees, altitudeMinutes, altitudeSeconds);
		double c = Math.toRadians(a);
		double d = Math.toRadians(b);
		double e = Math.toRadians(geographicalLatitude);
		double f = Math.sin(d) * Math.sin(e) + Math.cos(d) * Math.cos(e) * Math.cos(c);

		return wToDegrees(Math.asin(f));
	}

	/**
	 * Convert Horizon Coordinates to Hour Angle (in decimal degrees)
	 * 
	 * Original macro name: HORHa
	 */
	public static double horizonCoordinatesToHourAngle(double azimuthDegrees, double azimuthMinutes,
			double azimuthSeconds, double altitudeDegrees, double altitudeMinutes, double altitudeSeconds,
			double geographicalLatitude) {
		double a = degreesMinutesSecondsToDecimalDegrees(azimuthDegrees, azimuthMinutes, azimuthSeconds);
		double b = degreesMinutesSecondsToDecimalDegrees(altitudeDegrees, altitudeMinutes, altitudeSeconds);
		double c = Math.toRadians(a);
		double d = Math.toRadians(b);
		double e = Math.toRadians(geographicalLatitude);
		double f = Math.sin(d) * Math.sin(e) + Math.cos(d) * Math.cos(e) * Math.cos(c);
		double g = -Math.cos(d) * Math.cos(e) * Math.sin(c);
		double h = Math.sin(d) - Math.sin(e) * f;
		double i = decimalDegreesToDegreeHours(wToDegrees(Math.atan2(g, h)));

		return i - 24 * Math.floor(i / 24);
	}

	/**
	 * Obliquity of the Ecliptic for a Greenwich Date
	 * 
	 * Original macro name: Obliq
	 */
	public static double obliq(double greenwichDay, int greenwichMonth, int greenwichYear) {
		double a = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear);
		double b = a - 2415020;
		double c = (b / 36525) - 1;
		double d = c * (46.815 + c * (0.0006 - (c * 0.00181)));
		double e = d / 3600;

		return 23.43929167 - e + nutatObl(greenwichDay, greenwichMonth, greenwichYear);
	}

	/**
	 * Nutation of Obliquity
	 * 
	 * Original macro name: NutatObl
	 */
	public static double nutatObl(double greenwichDay, int greenwichMonth, int greenwichYear) {
		double dj = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear) - 2415020;
		double t = dj / 36525;
		double t2 = t * t;

		double a = 100.0021358 * t;
		double b = 360 * (a - Math.floor(a));

		double l1 = 279.6967 + 0.000303 * t2 + b;
		double l2 = 2 * Math.toRadians(l1);

		a = 1336.855231 * t;
		b = 360 * (a - Math.floor(a));

		double d1 = 270.4342 - 0.001133 * t2 + b;
		double d2 = 2 * Math.toRadians(d1);

		a = 99.99736056 * t;
		b = 360 * (a - Math.floor(a));

		double m1 = Math.toRadians(358.4758 - 0.00015 * t2 + b);

		a = 1325.552359 * t;
		b = 360 * (a - Math.floor(a));

		double m2 = Math.toRadians(296.1046 + 0.009192 * t2 + b);

		a = 5.372616667 * t;
		b = 360 * (a - Math.floor(a));

		double n1 = Math.toRadians(259.1833 + 0.002078 * t2 - b);

		double n2 = 2 * n1;

		double ddo = (9.21 + 0.00091 * t) * Math.cos(n1);
		ddo = ddo + (0.5522 - 0.00029 * t) * Math.cos(l2) - 0.0904 * Math.cos(n2);
		ddo = ddo + 0.0884 * Math.cos(d2) + 0.0216 * Math.cos(l2 + m1);
		ddo = ddo + 0.0183 * Math.cos(d2 - n1) + 0.0113 * Math.cos(d2 + m2);
		ddo = ddo - 0.0093 * Math.cos(l2 - m1) - 0.0066 * Math.cos(l2 - n1);

		return ddo / 3600;
	}

	/**
	 * Convert Local Sidereal Time to Greenwich Sidereal Time
	 * 
	 * Original macro name: LSTGST
	 */
	public static double localSiderealTimeToGreenwichSiderealTime(double localHours, double localMinutes,
			double localSeconds, double longitude) {
		double a = hmsToDH(localHours, localMinutes, localSeconds);
		double b = longitude / 15;
		double c = a - b;

		return c - (24 * Math.floor(c / 24));
	}

	/**
	 * Convert Greenwich Sidereal Time to Universal Time
	 * 
	 * Original macro name: GSTUT
	 */
	public static double greenwichSiderealTimeToUniversalTime(double greenwichSiderealHours,
			double greenwichSiderealMinutes, double greenwichSiderealSeconds, double greenwichDay, int greenwichMonth,
			int greenwichYear) {
		double a = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear);
		double b = a - 2451545;
		double c = b / 36525;
		double d = 6.697374558 + (2400.051336 * c) + (0.000025862 * c * c);
		double e = d - (24 * Math.floor(d / 24));
		double f = hmsToDH(greenwichSiderealHours, greenwichSiderealMinutes, greenwichSiderealSeconds);
		double g = f - e;
		double h = g - (24 * Math.floor(g / 24));

		return h * 0.9972695663;
	}

	/**
	 * Calculate Sun's ecliptic longitude
	 * 
	 * Original macro name: SunLong
	 */
	public static double sunLong(double lch, double lcm, double lcs, int ds, int zc, double ld, int lm, int ly) {
		double aa = localCivilTimeGreenwichDay(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double bb = localCivilTimeGreenwichMonth(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double cc = localCivilTimeGreenwichYear(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double ut = localCivilTimeToUniversalTime(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double dj = civilDateToJulianDate(aa, bb, cc) - 2415020;
		double t = (dj / 36525) + (ut / 876600);
		double t2 = t * t;
		double a = 100.0021359 * t;
		double b = 360.0 * (a - Math.floor(a));

		double l = 279.69668 + 0.0003025 * t2 + b;
		a = 99.99736042 * t;
		b = 360.0 * (a - Math.floor(a));

		double m1 = 358.47583 - (0.00015 + 0.0000033 * t) * t2 + b;
		double ec = 0.01675104 - 0.0000418 * t - 0.000000126 * t2;

		double am = Math.toRadians(m1);
		double at = trueAnomaly(am, ec);

		a = 62.55209472 * t;
		b = 360.0 * (a - Math.floor(a));

		double a1 = Math.toRadians(153.23 + b);
		a = 125.1041894 * t;
		b = 360.0 * (a - Math.floor(a));

		double b1 = Math.toRadians(216.57 + b);
		a = 91.56766028 * t;
		b = 360.0 * (a - Math.floor(a));

		double c1 = Math.toRadians(312.69 + b);
		a = 1236.853095 * t;
		b = 360.0 * (a - Math.floor(a));

		double d1 = Math.toRadians(350.74 - 0.00144 * t2 + b);
		double e1 = Math.toRadians(231.19 + 20.2 * t);
		a = 183.1353208 * t;
		b = 360.0 * (a - Math.floor(a));

		double d2 = 0.00134 * Math.cos(a1) + 0.00154 * Math.cos(b1) + 0.002 * Math.cos(c1);
		d2 = d2 + 0.00179 * Math.sin(d1) + 0.00178 * Math.sin(e1);
		double d3 = 0.00000543 * Math.sin(a1) + 0.00001575 * Math.sin(b1);
		d3 = d3 + 0.00001627 * Math.sin(c1) + 0.00003076 * Math.cos(d1);

		double sr = at + Math.toRadians(l - m1 + d2);
		double tp = 6.283185308;

		sr = sr - tp * Math.floor(sr / tp);

		return wToDegrees(sr);
	}

	/**
	 * Solve Kepler's equation, and return value of the true anomaly in radians
	 * 
	 * Original macro name: TrueAnomaly
	 */
	public static double trueAnomaly(double am, double ec) {
		double tp = 6.283185308;
		double m = am - tp * Math.floor(am / tp);
		double ae = m;

		while (true) {
			double d = ae - (ec * Math.sin(ae)) - m;
			if (Math.abs(d) < 0.000001) {
				break;
			}
			d = d / (1.0 - (ec * Math.cos(ae)));
			ae = ae - d;
		}
		double a = Math.sqrt((1 + ec) / (1 - ec)) * Math.tan(ae / 2);
		double at = 2.0 * Math.atan(a);

		return at;
	}

	/**
	 * Calculate effects of refraction
	 * 
	 * Original macro name: Refract
	 */
	public static double refract(double y2, CoordinateType sw, double pr, double tr) {
		double y = Math.toRadians(y2);

		double d = (sw == CoordinateType.TRUE) ? -1.0 : 1.0;

		if (d == -1) {
			double y3 = y;
			double y1 = y;
			double r1 = 0.0;

			while (true) {
				double yNew = y1 + r1;
				double rfNew = refract_L3035(pr, tr, yNew, d);

				if (y < -0.087)
					return 0;

				double r2 = rfNew;

				if ((r2 == 0) || (Math.abs(r2 - r1) < 0.000001)) {
					double qNew = y3;

					return wToDegrees(qNew + rfNew);
				}

				r1 = r2;
			}
		}

		double rf = refract_L3035(pr, tr, y, d);

		if (y < -0.087)
			return 0;

		double q = y;

		return wToDegrees(q + rf);
	}

	/**
	 * Helper function for Refract
	 */
	public static double refract_L3035(double pr, double tr, double y, double d) {
		if (y < 0.2617994) {
			if (y < -0.087)
				return 0;

			double yd = wToDegrees(y);
			double a = ((0.00002 * yd + 0.0196) * yd + 0.1594) * pr;
			double b = (273.0 + tr) * ((0.0845 * yd + 0.505) * yd + 1);

			return Math.toRadians(-(a / b) * d);

		}

		return -d * 0.00007888888 * pr / ((273.0 + tr) * Math.tan(y));
	}

	/**
	 * Calculate corrected hour angle in decimal hours
	 * 
	 * Original macro name: ParallaxHA
	 */
	public static double parallaxHA(double hh, double hm, double hs, double dd, double dm, double ds, CoordinateType sw,
			double gp, double ht, double hp) {
		double a = Math.toRadians(gp);
		double c1 = Math.cos(a);
		double s1 = Math.sin(a);

		double u = Math.atan(0.996647 * s1 / c1);
		double c2 = Math.cos(u);
		double s2 = Math.sin(u);
		double b = ht / 6378160;

		double rs = (0.996647 * s2) + (b * s1);

		double rc = c2 + (b * c1);
		double tp = 6.283185308;

		double rp = 1.0 / Math.sin(Math.toRadians(hp));

		double x = Math.toRadians(degreeHoursToDecimalDegrees(hmsToDH(hh, hm, hs)));
		double x1 = x;
		double y = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(dd, dm, ds));
		double y1 = y;

		double d = (sw == CoordinateType.TRUE) ? 1.0 : -1.0;

		if (d == 1) {
			ParallaxHelper result = parallaxHA_L2870(x, y, rc, rp, rs, tp);

			return decimalDegreesToDegreeHours(wToDegrees(result.p));
		}

		double p1 = 0.0;
		double q1 = 0.0;
		double xLoop = x;
		double yLoop = y;

		while (true) {
			ParallaxHelper result = parallaxHA_L2870(xLoop, yLoop, rc, rp, rs, tp);
			double p2 = result.p - xLoop;
			double q2 = result.q - yLoop;

			double aa = Math.abs(p2 - p1);
			double bb = Math.abs(q2 - q1);

			if ((aa < 0.000001) && (bb < 0.000001)) {
				double p = x1 - p2;

				return decimalDegreesToDegreeHours(wToDegrees(p));
			}

			xLoop = x1 - p2;
			yLoop = y1 - q2;
			p1 = p2;
			q1 = q2;
		}

		// return DecimalDegreesToDegreeHours(Degrees(0));
	}

	/**
	 * Helper function for parallax_ha
	 */
	public static ParallaxHelper parallaxHA_L2870(double x, double y, double rc, double rp, double rs, double tp) {
		double cx = Math.cos(x);
		double sy = Math.sin(y);
		double cy = Math.cos(y);

		double aa = (rc * Math.sin(x)) / ((rp * cy) - (rc * cx));

		double dx = Math.atan(aa);
		double p = x + dx;
		double cp = Math.cos(p);

		p = p - tp * Math.floor(p / tp);
		double q = Math.atan(cp * (rp * sy - rs) / (rp * cy * cx - rc));

		return new ParallaxHelper(p, q);
	}

	/**
	 * Calculate corrected declination in decimal degrees
	 * 
	 * Original macro name: ParallaxDec
	 */
	public static double parallaxDec(double hh, double hm, double hs, double dd, double dm, double ds,
			CoordinateType sw, double gp, double ht, double hp) {
		double a = Math.toRadians(gp);
		double c1 = Math.cos(a);
		double s1 = Math.sin(a);

		double u = Math.atan(0.996647 * s1 / c1);

		double c2 = Math.cos(u);
		double s2 = Math.sin(u);
		double b = ht / 6378160;
		double rs = (0.996647 * s2) + (b * s1);

		double rc = c2 + (b * c1);
		double tp = 6.283185308;

		double rp = 1.0 / Math.sin(Math.toRadians(hp));

		double x = Math.toRadians(degreeHoursToDecimalDegrees(hmsToDH(hh, hm, hs)));
		double x1 = x;

		double y = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(dd, dm, ds));
		double y1 = y;

		double d = (sw == CoordinateType.TRUE) ? 1.0 : -1.0;

		if (d == 1) {
			ParallaxHelper result = parallaxDec_L2870(x, y, rc, rp, rs, tp);

			return wToDegrees(result.q);
		}

		double p1 = 0.0;
		// double q1 = 0.0;

		double xLoop = x;
		double yLoop = y;

		while (true) {
			ParallaxHelper result = parallaxDec_L2870(xLoop, yLoop, rc, rp, rs, tp);
			double p2 = result.p - xLoop;
			double q2 = result.q - yLoop;
			double aa = Math.abs(p2 - p1);

			if ((aa < 0.000001) && (b < 0.000001)) {
				var q = y1 - q2;

				return wToDegrees(q);
			}
			xLoop = x1 - p2;
			yLoop = y1 - q2;
			p1 = p2;
			// q1 = q2;
		}

		// return Degrees(0.0);
	}

	/**
	 * Helper function for parallax_dec
	 */
	public static ParallaxHelper parallaxDec_L2870(double x, double y, double rc, double rp, double rs, double tp) {
		double cx = Math.cos(x);
		double sy = Math.sin(y);
		double cy = Math.cos(y);

		double aa = (rc * Math.sin(x)) / ((rp * cy) - (rc * cx));
		double dx = Math.atan(aa);
		double p = x + dx;
		double cp = Math.cos(p);

		p = p - tp * Math.floor(p / tp);
		double q = Math.atan(cp * (rp * sy - rs) / (rp * cy * cx - rc));

		return new ParallaxHelper(p, q);
	}

}
