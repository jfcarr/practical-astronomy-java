package astro.practical.lib;

import astro.practical.types.CoordinateType;
import astro.practical.types.PAWarningFlag;
import astro.practical.types.RiseSetStatus;
import astro.practical.types.TwilightStatus;
import astro.practical.types.TwilightType;
import astro.practical.types.complex.ESunRSL3710;
import astro.practical.types.complex.ETwilightL3710;
import astro.practical.types.complex.ParallaxHelper;
import astro.practical.types.complex.SunriseAZL3710;
import astro.practical.types.complex.SunriseLCTL3710;
import astro.practical.types.complex.SunsetAZL3710;
import astro.practical.types.complex.SunsetLCTL3710;
import astro.practical.types.complex.TwilightAMLCTL3710;
import astro.practical.types.complex.TwilightPMLCTL3710;

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
	 * Convert Universal Time to Local Civil Time
	 * 
	 * Original macro name: UTLct
	 */
	public static double universalTimeToLocalCivilTime(double uHours, double uMinutes, double uSeconds,
			int daylightSaving, int zoneCorrection, double greenwichDay, int greenwichMonth, int greenwichYear) {
		double a = hmsToDH(uHours, uMinutes, uSeconds);
		double b = a + zoneCorrection;
		double c = b + daylightSaving;
		double d = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear) + (c / 24);
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
	 * Nutation amount to be added in ecliptic longitude, in degrees.
	 * 
	 * Original macro name: NutatLong
	 */
	public static double nutatLong(double gd, int gm, int gy) {
		double dj = civilDateToJulianDate(gd, gm, gy) - 2415020;
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

		double m1 = 358.4758 - 0.00015 * t2 + b;
		m1 = Math.toRadians(m1);

		a = 1325.552359 * t;
		b = 360 * (a - Math.floor(a));

		double m2 = 296.1046 + 0.009192 * t2 + b;
		m2 = Math.toRadians(m2);

		a = 5.372616667 * t;
		b = 360 * (a - Math.floor(a));

		double n1 = 259.1833 + 0.002078 * t2 - b;
		n1 = Math.toRadians(n1);

		double n2 = 2.0 * n1;

		double dp = (-17.2327 - 0.01737 * t) * Math.sin(n1);
		dp += (-1.2729 - 0.00013 * t) * Math.sin(l2) + 0.2088 * Math.sin(n2);
		dp -= 0.2037 * Math.sin(d2) + (0.1261 - 0.00031 * t) * Math.sin(m1);
		dp += 0.0675 * Math.sin(m2) - (0.0497 - 0.00012 * t) * Math.sin(l2 + m1);
		dp -= 0.0342 * Math.sin(d2 - n1) - 0.0261 * Math.sin(d2 + m2);
		dp += 0.0214 * Math.sin(l2 - m1) - 0.0149 * Math.sin(l2 - d2 + m2);
		dp += 0.0124 * Math.sin(l2 - n1) + 0.0114 * Math.sin(d2 - m2);

		return dp / 3600;
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
	 * Status of conversion of Greenwich Sidereal Time to Universal Time.
	 * 
	 * Original macro name: eGSTUT
	 */
	public static PAWarningFlag eGstUt(double gsh, double gsm, double gss, double gd, int gm, int gy) {
		double a = civilDateToJulianDate(gd, gm, gy);
		double b = a - 2451545;
		double c = b / 36525;
		double d = 6.697374558 + (2400.051336 * c) + (0.000025862 * c * c);
		double e = d - (24 * Math.floor(d / 24));
		double f = hmsToDH(gsh, gsm, gss);
		double g = f - e;
		double h = g - (24 * Math.floor(g / 24));

		return ((h * 0.9972695663) < (4.0 / 60.0)) ? PAWarningFlag.WARNING : PAWarningFlag.OK;
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
	 * Solve Kepler's equation, and return value of the eccentric anomaly in radians
	 * 
	 * Original macro name: EccentricAnomaly
	 */
	public static double eccentricAnomaly(double am, double ec) {
		double tp = 6.283185308;
		double m = am - tp * Math.floor(am / tp);
		double ae = m;

		while (true) {
			double d = ae - (ec * Math.sin(ae)) - m;

			if (Math.abs(d) < 0.000001) {
				break;
			}

			d /= (1 - (ec * Math.cos(ae)));
			ae -= d;
		}

		return ae;
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

	/**
	 * Calculate Sun's angular diameter in decimal degrees
	 * 
	 * Original macro name: SunDia
	 */
	public static double sunDia(double lch, double lcm, double lcs, int ds, int zc, double ld, int lm, int ly) {
		double a = sunDist(lch, lcm, lcs, ds, zc, ld, lm, ly);

		return 0.533128 / a;
	}

	/**
	 * Calculate Sun's distance from the Earth in astronomical units
	 * 
	 * Original macro name: SunDist
	 */
	public static double sunDist(double lch, double lcm, double lcs, int ds, int zc, double ld, int lm, int ly) {
		double aa = localCivilTimeGreenwichDay(lch, lcm, lcs, ds, zc, ld, lm, ly);
		int bb = localCivilTimeGreenwichMonth(lch, lcm, lcs, ds, zc, ld, lm, ly);
		int cc = localCivilTimeGreenwichYear(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double ut = localCivilTimeToUniversalTime(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double dj = civilDateToJulianDate(aa, bb, cc) - 2415020;

		double t = (dj / 36525) + (ut / 876600);
		double t2 = t * t;

		double a = 100.0021359 * t;
		double b = 360 * (a - Math.floor(a));
		a = 99.99736042 * t;
		b = 360 * (a - Math.floor(a));
		double m1 = 358.47583 - (0.00015 + 0.0000033 * t) * t2 + b;
		double ec = 0.01675104 - 0.0000418 * t - 0.000000126 * t2;

		double am = Math.toRadians(m1);
		double ae = eccentricAnomaly(am, ec);

		a = 62.55209472 * t;
		b = 360 * (a - Math.floor(a));
		double a1 = Math.toRadians(153.23 + b);
		a = 125.1041894 * t;
		b = 360 * (a - Math.floor(a));
		double b1 = Math.toRadians(216.57 + b);
		a = 91.56766028 * t;
		b = 360 * (a - Math.floor(a));
		double c1 = Math.toRadians(312.69 + b);
		a = 1236.853095 * t;
		b = 360 * (a - Math.floor(a));
		double d1 = Math.toRadians(350.74 - 0.00144 * t2 + b);
		@SuppressWarnings("unused")
		double e1 = Math.toRadians(231.19 + 20.2 * t);
		a = 183.1353208 * t;
		b = 360 * (a - Math.floor(a));
		double h1 = Math.toRadians(353.4 + b);

		double d3 = 0.00000543 * Math.sin(a1) + 0.00001575 * Math.sin(b1)
				+ (0.00001627 * Math.sin(c1) + 0.00003076 * Math.cos(d1)) + (0.00000927 * Math.sin(h1));

		return 1.0000002 * (1 - ec * Math.cos(ae)) + d3;
	}

	/**
	 * Calculate geocentric ecliptic longitude for the Moon
	 * 
	 * Original macro name: MoonLong
	 */
	public static double moonLong(double lh, double lm, double ls, int ds, int zc, double dy, int mn, int yr) {
		double ut = localCivilTimeToUniversalTime(lh, lm, ls, ds, zc, dy, mn, yr);
		double gd = localCivilTimeGreenwichDay(lh, lm, ls, ds, zc, dy, mn, yr);
		int gm = localCivilTimeGreenwichMonth(lh, lm, ls, ds, zc, dy, mn, yr);
		int gy = localCivilTimeGreenwichYear(lh, lm, ls, ds, zc, dy, mn, yr);
		double t = ((civilDateToJulianDate(gd, gm, gy) - 2415020) / 36525) + (ut / 876600);
		double t2 = t * t;

		double m1 = 27.32158213;
		double m2 = 365.2596407;
		double m3 = 27.55455094;
		double m4 = 29.53058868;
		double m5 = 27.21222039;
		double m6 = 6798.363307;
		double q = civilDateToJulianDate(gd, gm, gy) - 2415020 + (ut / 24);
		m1 = q / m1;
		m2 = q / m2;
		m3 = q / m3;
		m4 = q / m4;
		m5 = q / m5;
		m6 = q / m6;
		m1 = 360 * (m1 - Math.floor(m1));
		m2 = 360 * (m2 - Math.floor(m2));
		m3 = 360 * (m3 - Math.floor(m3));
		m4 = 360 * (m4 - Math.floor(m4));
		m5 = 360 * (m5 - Math.floor(m5));
		m6 = 360 * (m6 - Math.floor(m6));

		double ml = 270.434164 + m1 - (0.001133 - 0.0000019 * t) * t2;
		double ms = 358.475833 + m2 - (0.00015 + 0.0000033 * t) * t2;
		double md = 296.104608 + m3 + (0.009192 + 0.0000144 * t) * t2;
		double me1 = 350.737486 + m4 - (0.001436 - 0.0000019 * t) * t2;
		double mf = 11.250889 + m5 - (0.003211 + 0.0000003 * t) * t2;
		double na = 259.183275 - m6 + (0.002078 + 0.0000022 * t) * t2;
		double a = Math.toRadians(51.2 + 20.2 * t);
		double s1 = Math.sin(a);
		double s2 = Math.sin(Math.toRadians(na));
		double b = 346.56 + (132.87 - 0.0091731 * t) * t;
		double s3 = 0.003964 * Math.sin(Math.toRadians(b));
		double c = Math.toRadians(na + 275.05 - 2.3 * t);
		double s4 = Math.sin(c);
		ml = ml + 0.000233 * s1 + s3 + 0.001964 * s2;
		ms -= 0.001778 * s1;
		md = md + 0.000817 * s1 + s3 + 0.002541 * s2;
		mf = mf + s3 - 0.024691 * s2 - 0.004328 * s4;
		me1 = me1 + 0.002011 * s1 + s3 + 0.001964 * s2;
		double e = 1.0 - (0.002495 + 0.00000752 * t) * t;
		double e2 = e * e;
		ml = Math.toRadians(ml);
		ms = Math.toRadians(ms);
		me1 = Math.toRadians(me1);
		mf = Math.toRadians(mf);
		md = Math.toRadians(md);

		double l = 6.28875 * Math.sin(md) + 1.274018 * Math.sin(2.0 * me1 - md);
		l = l + 0.658309 * Math.sin(2.0 * me1) + 0.213616 * Math.sin(2.0 * md);
		l = l - e * 0.185596 * Math.sin(ms) - 0.114336 * Math.sin(2.0 * mf);
		l += 0.058793 * Math.sin(2.0 * (me1 - md));
		l += 0.057212 * e * Math.sin(2.0 * me1 - ms - md) + 0.05332 * Math.sin(2.0 * me1 + md);
		l += 0.045874 * e * Math.sin(2.0 * me1 - ms) + 0.041024 * e * Math.sin(md - ms);
		l -= 0.034718 * Math.sin(me1) - e * 0.030465 * Math.sin(ms + md);
		l += 0.015326 * Math.sin(2.0 * (me1 - mf)) - 0.012528 * Math.sin(2.0 * mf + md);
		l -= 0.01098 * Math.sin(2.0 * mf - md) + 0.010674 * Math.sin(4.0 * me1 - md);
		l += 0.010034 * Math.sin(3.0 * md) + 0.008548 * Math.sin(4.0 * me1 - 2.0 * md);
		l -= e * 0.00791 * Math.sin(ms - md + 2.0 * me1) - e * 0.006783 * Math.sin(2.0 * me1 + ms);
		l += 0.005162 * Math.sin(md - me1) + e * 0.005 * Math.sin(ms + me1);
		l += 0.003862 * Math.sin(4.0 * me1) + e * 0.004049 * Math.sin(md - ms + 2.0 * me1);
		l += 0.003996 * Math.sin(2.0 * (md + me1)) + 0.003665 * Math.sin(2.0 * me1 - 3.0 * md);
		l += e * 0.002695 * Math.sin(2.0 * md - ms) + 0.002602 * Math.sin(md - 2.0 * (mf + me1));
		l += e * 0.002396 * Math.sin(2.0 * (me1 - md) - ms) - 0.002349 * Math.sin(md + me1);
		l += e2 * 0.002249 * Math.sin(2.0 * (me1 - ms)) - e * 0.002125 * Math.sin(2.0 * md + ms);
		l -= e2 * 0.002079 * Math.sin(2.0 * ms) + e2 * 0.002059 * Math.sin(2.0 * (me1 - ms) - md);
		l -= 0.001773 * Math.sin(md + 2.0 * (me1 - mf)) - 0.001595 * Math.sin(2.0 * (mf + me1));
		l += e * 0.00122 * Math.sin(4.0 * me1 - ms - md) - 0.00111 * Math.sin(2.0 * (md + mf));
		l += 0.000892 * Math.sin(md - 3.0 * me1) - e * 0.000811 * Math.sin(ms + md + 2.0 * me1);
		l += e * 0.000761 * Math.sin(4.0 * me1 - ms - 2.0 * md);
		l += e2 * 0.000704 * Math.sin(md - 2.0 * (ms + me1));
		l += e * 0.000693 * Math.sin(ms - 2.0 * (md - me1));
		l += e * 0.000598 * Math.sin(2.0 * (me1 - mf) - ms);
		l += 0.00055 * Math.sin(md + 4.0 * me1) + 0.000538 * Math.sin(4.0 * md);
		l += e * 0.000521 * Math.sin(4.0 * me1 - ms) + 0.000486 * Math.sin(2.0 * md - me1);
		l += e2 * 0.000717 * Math.sin(md - 2.0 * ms);

		double mm = unwind(ml + Math.toRadians(l));

		return wToDegrees(mm);
	}

	/**
	 * Calculate geocentric ecliptic latitude for the Moon
	 * 
	 * Original macro name: MoonLat
	 */
	public static double moonLat(double lh, double lm, double ls, int ds, int zc, double dy, int mn, int yr) {
		double ut = localCivilTimeToUniversalTime(lh, lm, ls, ds, zc, dy, mn, yr);
		double gd = localCivilTimeGreenwichDay(lh, lm, ls, ds, zc, dy, mn, yr);
		int gm = localCivilTimeGreenwichMonth(lh, lm, ls, ds, zc, dy, mn, yr);
		int gy = localCivilTimeGreenwichYear(lh, lm, ls, ds, zc, dy, mn, yr);
		double t = ((civilDateToJulianDate(gd, gm, gy) - 2415020) / 36525) + (ut / 876600);
		double t2 = t * t;

		double m1 = 27.32158213;
		double m2 = 365.2596407;
		double m3 = 27.55455094;
		double m4 = 29.53058868;
		double m5 = 27.21222039;
		double m6 = 6798.363307;
		double q = civilDateToJulianDate(gd, gm, gy) - 2415020 + (ut / 24);
		m1 = q / m1;
		m2 = q / m2;
		m3 = q / m3;
		m4 = q / m4;
		m5 = q / m5;
		m6 = q / m6;
		m1 = 360 * (m1 - Math.floor(m1));
		m2 = 360 * (m2 - Math.floor(m2));
		m3 = 360 * (m3 - Math.floor(m3));
		m4 = 360 * (m4 - Math.floor(m4));
		m5 = 360 * (m5 - Math.floor(m5));
		m6 = 360 * (m6 - Math.floor(m6));

		@SuppressWarnings("unused")
		double ml = 270.434164 + m1 - (0.001133 - 0.0000019 * t) * t2;
		double ms = 358.475833 + m2 - (0.00015 + 0.0000033 * t) * t2;
		double md = 296.104608 + m3 + (0.009192 + 0.0000144 * t) * t2;
		double me1 = 350.737486 + m4 - (0.001436 - 0.0000019 * t) * t2;
		double mf = 11.250889 + m5 - (0.003211 + 0.0000003 * t) * t2;
		double na = 259.183275 - m6 + (0.002078 + 0.0000022 * t) * t2;

		double a = Math.toRadians(51.2 + 20.2 * t);
		double s1 = Math.sin(a);
		double s2 = Math.sin(Math.toRadians(na));
		double b = 346.56 + (132.87 - 0.0091731 * t) * t;
		double s3 = 0.003964 * Math.sin(Math.toRadians(b));
		double c = Math.toRadians(na + 275.05 - 2.3 * t);
		double s4 = Math.sin(c);

		ml += 0.000233 * s1 + s3 + 0.001964 * s2;
		ms -= 0.001778 * s1;
		md += 0.000817 * s1 + s3 + 0.002541 * s2;
		mf += s3 - 0.024691 * s2 - 0.004328 * s4;
		me1 += 0.002011 * s1 + s3 + 0.001964 * s2;

		double e = 1.0 - (0.002495 + 0.00000752 * t) * t;
		double e2 = e * e;

		ms = Math.toRadians(ms);
		na = Math.toRadians(na);
		me1 = Math.toRadians(me1);
		mf = Math.toRadians(mf);
		md = Math.toRadians(md);

		double g = 5.128189 * Math.sin(mf) + 0.280606 * Math.sin(md + mf);
		g += 0.277693 * Math.sin(md - mf) + 0.173238 * Math.sin(2.0 * me1 - mf);
		g += 0.055413 * Math.sin(2.0 * me1 + mf - md) + 0.046272 * Math.sin(2.0 * me1 - mf - md);
		g += 0.032573 * Math.sin(2.0 * me1 + mf) + 0.017198 * Math.sin(2.0 * md + mf);
		g += 0.009267 * Math.sin(2.0 * me1 + md - mf) + 0.008823 * Math.sin(2.0 * md - mf);
		g += e * 0.008247 * Math.sin(2.0 * me1 - ms - mf) + 0.004323 * Math.sin(2.0 * (me1 - md) - mf);
		g += 0.0042 * Math.sin(2.0 * me1 + mf + md) + e * 0.003372 * Math.sin(mf - ms - 2.0 * me1);
		g += e * 0.002472 * Math.sin(2.0 * me1 + mf - ms - md);
		g += e * 0.002222 * Math.sin(2.0 * me1 + mf - ms);
		g += e * 0.002072 * Math.sin(2.0 * me1 - mf - ms - md);
		g += e * 0.001877 * Math.sin(mf - ms + md) + 0.001828 * Math.sin(4.0 * me1 - mf - md);
		g -= e * 0.001803 * Math.sin(mf + ms) - 0.00175 * Math.sin(3.0 * mf);
		g += e * 0.00157 * Math.sin(md - ms - mf) - 0.001487 * Math.sin(mf + me1);
		g -= e * 0.001481 * Math.sin(mf + ms + md) + e * 0.001417 * Math.sin(mf - ms - md);
		g += e * 0.00135 * Math.sin(mf - ms) + 0.00133 * Math.sin(mf - me1);
		g += 0.001106 * Math.sin(mf + 3.0 * md) + 0.00102 * Math.sin(4.0 * me1 - mf);
		g += 0.000833 * Math.sin(mf + 4.0 * me1 - md) + 0.000781 * Math.sin(md - 3.0 * mf);
		g += 0.00067 * Math.sin(mf + 4.0 * me1 - 2.0 * md) + 0.000606 * Math.sin(2.0 * me1 - 3.0 * mf);
		g += 0.000597 * Math.sin(2.0 * (me1 + md) - mf);
		g += e * 0.000492 * Math.sin(2.0 * me1 + md - ms - mf) + 0.00045 * Math.sin(2.0 * (md - me1) - mf);
		g += 0.000439 * Math.sin(3.0 * md - mf) + 0.000423 * Math.sin(mf + 2.0 * (me1 + md));
		g += 0.000422 * Math.sin(2.0 * me1 - mf - 3.0 * md) - e * 0.000367 * Math.sin(ms + mf + 2.0 * me1 - md);
		g -= e * 0.000353 * Math.sin(ms + mf + 2.0 * me1) + 0.000331 * Math.sin(mf + 4.0 * me1);
		g += e * 0.000317 * Math.sin(2.0 * me1 + mf - ms + md);
		g += e2 * 0.000306 * Math.sin(2.0 * (me1 - ms) - mf) - 0.000283 * Math.sin(md + 3.0 * mf);

		double w1 = 0.0004664 * Math.cos(na);
		double w2 = 0.0000754 * Math.cos(c);
		double bm = Math.toRadians(g) * (1.0 - w1 - w2);

		return wToDegrees(bm);
	}

	/**
	 * Calculate horizontal parallax for the Moon
	 * 
	 * Original macro name: MoonHP
	 */
	public static double moonHP(double lh, double lm, double ls, int ds, int zc, double dy, int mn, int yr) {
		double ut = localCivilTimeToUniversalTime(lh, lm, ls, ds, zc, dy, mn, yr);
		double gd = localCivilTimeGreenwichDay(lh, lm, ls, ds, zc, dy, mn, yr);
		int gm = localCivilTimeGreenwichMonth(lh, lm, ls, ds, zc, dy, mn, yr);
		int gy = localCivilTimeGreenwichYear(lh, lm, ls, ds, zc, dy, mn, yr);
		double t = ((civilDateToJulianDate(gd, gm, gy) - 2415020) / 36525) + (ut / 876600);
		double t2 = t * t;

		double m1 = 27.32158213;
		double m2 = 365.2596407;
		double m3 = 27.55455094;
		double m4 = 29.53058868;
		double m5 = 27.21222039;
		double m6 = 6798.363307;
		double q = civilDateToJulianDate(gd, gm, gy) - 2415020 + (ut / 24);
		m1 = q / m1;
		m2 = q / m2;
		m3 = q / m3;
		m4 = q / m4;
		m5 = q / m5;
		m6 = q / m6;
		m1 = 360 * (m1 - Math.floor(m1));
		m2 = 360 * (m2 - Math.floor(m2));
		m3 = 360 * (m3 - Math.floor(m3));
		m4 = 360 * (m4 - Math.floor(m4));
		m5 = 360 * (m5 - Math.floor(m5));
		m6 = 360 * (m6 - Math.floor(m6));

		@SuppressWarnings("unused")
		double ml = 270.434164 + m1 - (0.001133 - 0.0000019 * t) * t2;
		double ms = 358.475833 + m2 - (0.00015 + 0.0000033 * t) * t2;
		double md = 296.104608 + m3 + (0.009192 + 0.0000144 * t) * t2;
		double me1 = 350.737486 + m4 - (0.001436 - 0.0000019 * t) * t2;
		double mf = 11.250889 + m5 - (0.003211 + 0.0000003 * t) * t2;
		double na = 259.183275 - m6 + (0.002078 + 0.0000022 * t) * t2;
		double a = Math.toRadians(51.2 + 20.2 * t);
		double s1 = Math.sin(a);
		double s2 = Math.sin(Math.toRadians(na));
		double b = 346.56 + (132.87 - 0.0091731 * t) * t;
		double s3 = 0.003964 * Math.sin(Math.toRadians(b));
		double c = Math.toRadians(na + 275.05 - 2.3 * t);
		double s4 = Math.sin(c);

		ml += 0.000233 * s1 + s3 + 0.001964 * s2;
		ms -= 0.001778 * s1;
		md += 0.000817 * s1 + s3 + 0.002541 * s2;
		mf += s3 - 0.024691 * s2 - 0.004328 * s4;
		me1 += 0.002011 * s1 + s3 + 0.001964 * s2;

		double e = 1.0 - (0.002495 + 0.00000752 * t) * t;
		double e2 = e * e;

		ms = Math.toRadians(ms);
		me1 = Math.toRadians(me1);
		mf = Math.toRadians(mf);
		md = Math.toRadians(md);

		double pm = 0.950724 + 0.051818 * Math.cos(md) + 0.009531 * Math.cos(2.0 * me1 - md);
		pm += 0.007843 * Math.cos(2.0 * me1) + 0.002824 * Math.cos(2.0 * md);
		pm += 0.000857 * Math.cos(2.0 * me1 + md) + e * 0.000533 * Math.cos(2.0 * me1 - ms);
		pm += e * 0.000401 * Math.cos(2.0 * me1 - md - ms);
		pm += e * 0.00032 * Math.cos(md - ms) - 0.000271 * Math.cos(me1);
		pm -= e * 0.000264 * Math.cos(ms + md) - 0.000198 * Math.cos(2.0 * mf - md);
		pm += 0.000173 * Math.cos(3.0 * md) + 0.000167 * Math.cos(4.0 * me1 - md);
		pm -= e * 0.000111 * Math.cos(ms) + 0.000103 * Math.cos(4.0 * me1 - 2.0 * md);
		pm -= 0.000084 * Math.cos(2.0 * md - 2.0 * me1) - e * 0.000083 * Math.cos(2.0 * me1 + ms);
		pm += 0.000079 * Math.cos(2.0 * me1 + 2.0 * md) + 0.000072 * Math.cos(4.0 * me1);
		pm += e * 0.000064 * Math.cos(2.0 * me1 - ms + md) - e * 0.000063 * Math.cos(2.0 * me1 + ms - md);
		pm += e * 0.000041 * Math.cos(ms + me1) + e * 0.000035 * Math.cos(2.0 * md - ms);
		pm -= 0.000033 * Math.cos(3.0 * md - 2.0 * me1) + 0.00003 * Math.cos(md + me1);
		pm -= 0.000029 * Math.cos(2.0 * (mf - me1)) - e * 0.000029 * Math.cos(2.0 * md + ms);
		pm += e2 * 0.000026 * Math.cos(2.0 * (me1 - ms)) - 0.000023 * Math.cos(2.0 * (mf - me1) + md);
		pm += e * 0.000019 * Math.cos(4.0 * me1 - ms - md);

		return pm;
	}

	/**
	 * Convert angle in radians to equivalent angle in degrees.
	 * 
	 * Original macro name: Unwind
	 */
	public static double unwind(double w) {
		return w - 6.283185308 * Math.floor(w / 6.283185308);
	}

	/**
	 * Convert angle in degrees to equivalent angle in the range 0 to 360 degrees.
	 * 
	 * Original macro name: UnwindDeg
	 */
	public static double unwindDeg(double w) {
		return w - 360 * Math.floor(w / 360);
	}

	/**
	 * Mean ecliptic longitude of the Sun at the epoch
	 * 
	 * Original macro name: SunElong
	 */
	public static double sunELong(double gd, int gm, int gy) {
		double t = (civilDateToJulianDate(gd, gm, gy) - 2415020) / 36525;
		double t2 = t * t;
		double x = 279.6966778 + 36000.76892 * t + 0.0003025 * t2;

		return x - 360 * Math.floor(x / 360);
	}

	/**
	 * Longitude of the Sun at perigee
	 * 
	 * Original macro name: SunPeri
	 */
	public static double sunPeri(double gd, int gm, int gy) {
		double t = (civilDateToJulianDate(gd, gm, gy) - 2415020) / 36525;
		double t2 = t * t;
		double x = 281.2208444 + 1.719175 * t + 0.000452778 * t2;

		return x - 360 * Math.floor(x / 360);
	}

	/**
	 * Eccentricity of the Sun-Earth orbit
	 * 
	 * Original macro name: SunEcc
	 */
	public static double sunEcc(double gd, int gm, int gy) {
		double t = (civilDateToJulianDate(gd, gm, gy) - 2415020) / 36525;
		double t2 = t * t;

		return 0.01675104 - 0.0000418 * t - 0.000000126 * t2;
	}

	/**
	 * Ecliptic - Declination (degrees)
	 * 
	 * Original macro name: ECDec
	 */
	public static double ecDec(double eld, double elm, double els, double bd, double bm, double bs, double gd, int gm,
			int gy) {
		double a = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(eld, elm, els));
		double b = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(bd, bm, bs));
		double c = Math.toRadians(obliq(gd, gm, gy));
		double d = Math.sin(b) * Math.cos(c) + Math.cos(b) * Math.sin(c) * Math.sin(a);

		return wToDegrees(Math.asin(d));
	}

	/**
	 * Ecliptic - Right Ascension (degrees)
	 * 
	 * Original macro name: ECRA
	 */
	public static double ecRA(double eld, double elm, double els, double bd, double bm, double bs, double gd, int gm,
			int gy) {
		double a = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(eld, elm, els));
		double b = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(bd, bm, bs));
		double c = Math.toRadians(obliq(gd, gm, gy));
		double d = Math.sin(a) * Math.cos(c) - Math.tan(b) * Math.sin(c);
		double e = Math.cos(a);
		double f = wToDegrees(Math.atan2(d, e));

		return f - 360 * Math.floor(f / 360);
	}

	/**
	 * Calculate Sun's true anomaly, i.e., how much its orbit deviates from a true
	 * circle to an ellipse.
	 * 
	 * Original macro name: SunTrueAnomaly
	 */
	public static double sunTrueAnomaly(double lch, double lcm, double lcs, int ds, int zc, double ld, int lm, int ly) {
		double aa = localCivilTimeGreenwichDay(lch, lcm, lcs, ds, zc, ld, lm, ly);
		int bb = localCivilTimeGreenwichMonth(lch, lcm, lcs, ds, zc, ld, lm, ly);
		int cc = localCivilTimeGreenwichYear(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double ut = localCivilTimeToUniversalTime(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double dj = civilDateToJulianDate(aa, bb, cc) - 2415020;

		double t = (dj / 36525) + (ut / 876600);
		double t2 = t * t;

		double a = 99.99736042 * t;
		double b = 360 * (a - Math.floor(a));

		double m1 = 358.47583 - (0.00015 + 0.0000033 * t) * t2 + b;
		double ec = 0.01675104 - 0.0000418 * t - 0.000000126 * t2;

		double am = Math.toRadians(m1);

		return wToDegrees(trueAnomaly(am, ec));
	}

	/**
	 * Calculate local civil time of sunrise.
	 * 
	 * Original macro name: SunriseLCT
	 */
	public static double sunriseLCT(double ld, int lm, int ly, int ds, int zc, double gl, double gp) {
		double di = 0.8333333;
		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		SunriseLCTL3710 result1 = sunriseLCTL3710(gd, gm, gy, sr, di, gp);

		double xx;
		if (result1.s != RiseSetStatus.OK) {
			xx = -99.0;
		} else {
			double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
			double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

			if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK) {
				xx = -99.0;
			} else {
				sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
				SunriseLCTL3710 result2 = sunriseLCTL3710(gd, gm, gy, sr, di, gp);

				if (result2.s != RiseSetStatus.OK) {
					xx = -99.0;
				} else {
					x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);
					ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);
					xx = universalTimeToLocalCivilTime(ut, 0, 0, ds, zc, gd, gm, gy);
				}
			}
		}

		return xx;
	}

	/**
	 * Helper function for sunrise_lct()
	 */
	public static SunriseLCTL3710 sunriseLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0.0, 0.0, y, 0.0, 0.0, di, gp);

		return new SunriseLCTL3710(a, x, y, la, s);
	}

	/// Calculate local civil time of sunset.
	///
	/// Original macro name: SunsetLCT
	public static double sunsetLCT(double ld, int lm, int ly, int ds, int zc, double gl, double gp) {
		double di = 0.8333333;
		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		SunsetLCTL3710 result1 = sunsetLCTL3710(gd, gm, gy, sr, di, gp);

		double xx;
		if (result1.s != RiseSetStatus.OK) {
			xx = -99.0;
		} else {
			double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
			double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

			if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK) {
				xx = -99.0;
			} else {
				sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
				SunsetLCTL3710 result2 = sunsetLCTL3710(gd, gm, gy, sr, di, gp);

				if (result2.s != RiseSetStatus.OK) {
					xx = -99;
				} else {
					x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);
					ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);
					xx = universalTimeToLocalCivilTime(ut, 0, 0, ds, zc, gd, gm, gy);
				}
			}
		}
		return xx;
	}

	/**
	 * Helper function for sunsetLCT().
	 */
	public static SunsetLCTL3710 sunsetLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0.0, 0.0, 0.0, 0.0, 0.0, gd, gm, gy);
		double y = ecDec(a, 0.0, 0.0, 0.0, 0.0, 0.0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeSet(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new SunsetLCTL3710(a, x, y, la, s);
	}

	/// Sunrise/Sunset calculation status.
	///
	/// Original macro name: eSunRS
	public static RiseSetStatus eSunRS(double ld, int lm, int ly, int ds, int zc, double gl, double gp) {
		double di = 0.8333333;
		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		ESunRSL3710 result1 = eSunRSL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK) {
			return result1.s;
		} else {
			double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
			double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);
			sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
			ESunRSL3710 result2 = eSunRSL3710(gd, gm, gy, sr, di, gp);
			if (result2.s != RiseSetStatus.OK) {
				return result2.s;
			} else {
				x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);

				if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK) {
					RiseSetStatus s = RiseSetStatus.GST_UT_CONVERSION_WARNING;

					return s;
				}

				return result2.s;
			}
		}
	}

	/**
	 * Helper function for e_sun_rs()
	 */
	public static ESunRSL3710 eSunRSL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new ESunRSL3710(a, x, y, la, s);
	}

	/**
	 * Calculate azimuth of sunrise.
	 * 
	 * Original macro name: SunriseAz
	 */
	public static double sunriseAZ(double ld, int lm, int ly, int ds, int zc, double gl, double gp) {
		double di = 0.8333333;
		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		SunriseAZL3710 result1 = sunriseAZL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK) {
			return -99.0;
		}

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK) {
			return -99.0;
		}

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
		SunriseAZL3710 result2 = sunriseAZL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK) {
			return -99.0;
		}

		return riseSetAzimuthRise(decimalDegreesToDegreeHours(x), 0, 0, result2.y, 0.0, 0.0, di, gp);
	}

	/**
	 * Helper function for sunrise_az()
	 */
	public static SunriseAZL3710 sunriseAZL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new SunriseAZL3710(a, x, y, la, s);
	}

	/**
	 * Calculate azimuth of sunset.
	 * 
	 * Original macro name: SunsetAz
	 */
	public static double sunsetAZ(double ld, int lm, int ly, int ds, int zc, double gl, double gp) {
		double di = 0.8333333;
		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		SunsetAZL3710 result1 = sunsetAZL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK) {
			return -99.0;
		}

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK) {
			return -99.0;
		}

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		SunsetAZL3710 result2 = sunsetAZL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK) {
			return -99.0;
		}
		return riseSetAzimuthSet(decimalDegreesToDegreeHours(x), 0, 0, result2.y, 0, 0, di, gp);
	}

	/**
	 * Helper function for sunset_az()
	 */
	public static SunsetAZL3710 sunsetAZL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeSet(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new SunsetAZL3710(a, x, y, la, s);
	}

	/**
	 * Local sidereal time of rise, in hours.
	 * 
	 * Original macro name: RSLSTR
	 */
	public static double riseSetLocalSiderealTimeRise(double rah, double ram, double ras, double dd, double dm,
			double ds, double vd, double g) {
		double a = hmsToDH(rah, ram, ras);
		double b = Math.toRadians(degreeHoursToDecimalDegrees(a));
		double c = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(dd, dm, ds));
		double d = Math.toRadians(vd);
		double e = Math.toRadians(g);
		double f = -(Math.sin(d) + Math.sin(e) * Math.sin(c)) / (Math.cos(e) * Math.cos(c));
		double h = (Math.abs(f) < 1) ? Math.acos(f) : 0;
		double i = decimalDegreesToDegreeHours(wToDegrees(b - h));

		return i - 24 * Math.floor(i / 24);
	}

	/**
	 * Local sidereal time of setting, in hours.
	 * 
	 * Original macro name: RSLSTS
	 */
	public static double riseSetLocalSiderealTimeSet(double rah, double ram, double ras, double dd, double dm,
			double ds, double vd, double g) {
		double a = hmsToDH(rah, ram, ras);
		double b = Math.toRadians(degreeHoursToDecimalDegrees(a));
		double c = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(dd, dm, ds));
		double d = Math.toRadians(vd);
		double e = Math.toRadians(g);
		double f = -(Math.sin(d) + Math.sin(e) * Math.sin(c)) / (Math.cos(e) * Math.cos(c));
		double h = (Math.abs(f) < 1) ? Math.acos(f) : 0;
		double i = decimalDegreesToDegreeHours(wToDegrees(b + h));

		return i - 24 * Math.floor(i / 24);
	}

	/**
	 * Azimuth of rising, in degrees.
	 * 
	 * Original macro name: RSAZR
	 */
	public static double riseSetAzimuthRise(double rah, double ram, double ras, double dd, double dm, double ds,
			double vd, double g) {
		@SuppressWarnings("unused")
		double a = hmsToDH(rah, ram, ras);
		double c = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(dd, dm, ds));
		double d = Math.toRadians(vd);
		double e = Math.toRadians(g);
		double f = (Math.sin(c) + Math.sin(d) * Math.sin(e)) / (Math.cos(d) * Math.cos(e));
		double h = eRS(rah, ram, ras, dd, dm, ds, vd, g) == RiseSetStatus.OK ? Math.acos(f) : 0;
		double i = wToDegrees(h);

		return i - 360 * Math.floor(i / 360);
	}

	/**
	 * Azimuth of setting, in degrees.
	 * 
	 * Original macro name: RSAZS
	 */
	public static double riseSetAzimuthSet(double rah, double ram, double ras, double dd, double dm, double ds,
			double vd, double g) {
		@SuppressWarnings("unused")
		double a = hmsToDH(rah, ram, ras);
		double c = Math.toRadians(degreesMinutesSecondsToDecimalDegrees(dd, dm, ds));
		double d = Math.toRadians(vd);
		double e = Math.toRadians(g);
		double f = (Math.sin(c) + Math.sin(d) * Math.sin(e)) / (Math.cos(d) * Math.cos(e));
		double h = eRS(rah, ram, ras, dd, dm, ds, vd, g) == RiseSetStatus.OK ? Math.acos(f) : 0;
		double i = 360 - wToDegrees(h);

		return i - 360 * Math.floor(i / 360);
	}

	/**
	 * Rise/Set status
	 * 
	 * Original macro name: eRS
	 */
	public static RiseSetStatus eRS(double rah, double ram, double ras, double dd, double dm, double ds, double vd,
			double g) {
		@SuppressWarnings("unused")
		double a = hmsToDH(rah, ram, ras);
		double c = degreesMinutesSecondsToDecimalDegrees(dd, dm, ds);
		c = Math.toRadians(c);
		double d = Math.toRadians(vd);
		double e = Math.toRadians(g);
		double f = -(Math.sin(d) + Math.sin(e) * Math.sin(c)) / (Math.cos(e) * Math.cos(c));

		RiseSetStatus returnValue = RiseSetStatus.OK;
		if (f >= 1)
			returnValue = RiseSetStatus.NEVERRISES;
		if (f <= -1)
			returnValue = RiseSetStatus.CIRCUMPOLAR;

		return returnValue;
	}

	/**
	 * Calculate morning twilight start, in local time.
	 * 
	 * Original macro name: TwilightAMLCT
	 */
	public static double twilightAMLCT(double ld, int lm, int ly, int ds, int zc, double gl, double gp,
			TwilightType tt) {
		double di = (double) tt.value;

		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		TwilightAMLCTL3710 result1 = twilightAMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK)
			return -99.0;

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK)
			return -99.0;

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		TwilightAMLCTL3710 result2 = twilightAMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK)
			return -99.0;

		x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);
		ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		double xx = universalTimeToLocalCivilTime(ut, 0, 0, ds, zc, gd, gm, gy);

		return xx;
	}

	/**
	 * Helper function for twilight_am_lct()
	 */
	public static TwilightAMLCTL3710 twilightAMLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new TwilightAMLCTL3710(a, x, y, la, s);
	}

	/**
	 * Calculate evening twilight end, in local time.
	 * 
	 * Original macro name: TwilightPMLCT
	 */
	public static double twilightPMLCT(double ld, int lm, int ly, int ds, int zc, double gl, double gp,
			TwilightType tt) {
		double di = (double) tt.value;

		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		TwilightPMLCTL3710 result1 = twilightPMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK)
			return 0.0;

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK)
			return 0.0;

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		TwilightPMLCTL3710 result2 = twilightPMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK)
			return 0.0;

		x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);
		ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		return universalTimeToLocalCivilTime(ut, 0, 0, ds, zc, gd, gm, gy);
	}

	/**
	 * Helper function for twilight_pm_lct()
	 */
	public static TwilightPMLCTL3710 twilightPMLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeSet(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new TwilightPMLCTL3710(a, x, y, la, s);
	}

	/**
	 * Twilight calculation status.
	 *
	 * Original macro name: eTwilight
	 */
	public static TwilightStatus eTwilight(double ld, int lm, int ly, int ds, int zc, double gl, double gp,
			TwilightType tt) {
		double di = (double) tt.value;

		double gd = localCivilTimeGreenwichDay(12, 0, 0, ds, zc, ld, lm, ly);
		int gm = localCivilTimeGreenwichMonth(12, 0, 0, ds, zc, ld, lm, ly);
		int gy = localCivilTimeGreenwichYear(12, 0, 0, ds, zc, ld, lm, ly);
		double sr = sunLong(12, 0, 0, ds, zc, ld, lm, ly);

		ETwilightL3710 result1 = eTwilightL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != TwilightStatus.OK)
			return result1.s;

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);
		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		ETwilightL3710 result2 = eTwilightL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != TwilightStatus.OK)
			return result2.s;

		x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);

		if (eGstUt(x, 0, 0, gd, gm, gy) != PAWarningFlag.OK) {
			result2.s = TwilightStatus.GST_TO_UT_CONVERSION_WARNING;

			return result2.s;
		}

		return result2.s;
	}

	/**
	 * Helper function for e_twilight()
	 */
	public static ETwilightL3710 eTwilightL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		TwilightStatus ts = TwilightStatus.OK;
		if (s == RiseSetStatus.CIRCUMPOLAR)
			ts = TwilightStatus.LASTS_ALL_NIGHT;
		if (s == RiseSetStatus.NEVERRISES)
			ts = TwilightStatus.SUN_TOO_FAR_BELOW_HORIZON;

		return new ETwilightL3710(a, x, y, la, ts);
	}
}
