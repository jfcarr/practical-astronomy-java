package astro.practical.lib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import astro.practical.models.L3710;
import astro.practical.models.L3710Twilight;
import astro.practical.models.LunarEclipseOccurrenceL6855;
import astro.practical.models.MoonAzL6700;
import astro.practical.models.MoonLongLatHP;
import astro.practical.models.MoonL6680;
import astro.practical.models.MoonL6700;
import astro.practical.models.MoonLcDMY;
import astro.practical.models.NewMoonFullMoonL6855;
import astro.practical.models.PCometLongLatDist;
import astro.practical.models.ParallaxHelper;
import astro.practical.models.PlanetCoordinates;
import astro.practical.models.PlanetLongL4685;
import astro.practical.models.PlanetLongL4735;
import astro.practical.models.PlanetLongL4810;
import astro.practical.models.PlanetLongL4945;
import astro.practical.models.data.PlanetDataPrecise;
import astro.practical.types.AngleMeasure;
import astro.practical.types.CoordinateType;
import astro.practical.types.LunarEclipseOccurrence;
import astro.practical.types.WarningFlag;
import astro.practical.types.RiseSetStatus;
import astro.practical.types.TwilightStatus;
import astro.practical.types.TwilightType;

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
	 * Get Local Civil Day for Universal Time
	 * 
	 * Original macro name: UTLcDay
	 */
	public static double universalTimeLocalCivilDay(double uHours, double uMinutes, double uSeconds,
			int daylightSaving, int zoneCorrection, double greenwichDay, int greenwichMonth, int greenwichYear) {
		double a = hmsToDH(uHours, uMinutes, uSeconds);
		double b = a + zoneCorrection;
		double c = b + daylightSaving;
		double d = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear) + (c / 24.0);
		double e = julianDateDay(d);
		double e1 = Math.floor(e);

		return e1;
	}

	/**
	 * Get Local Civil Month for Universal Time
	 * 
	 * Original macro name: UTLcMonth
	 */
	public static int universalTimeLocalCivilMonth(double uHours, double uMinutes, double uSeconds, int daylightSaving,
			int zoneCorrection, double greenwichDay, int greenwichMonth, int greenwichYear) {
		double a = hmsToDH(uHours, uMinutes, uSeconds);
		double b = a + zoneCorrection;
		double c = b + daylightSaving;
		double d = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear) + (c / 24.0);

		return julianDateMonth(d);
	}

	/**
	 * Get Local Civil Year for Universal Time
	 * 
	 * Original macro name: UTLcYear
	 */
	public static int universalTimeLocalCivilYear(double uHours, double uMinutes, double uSeconds, int daylightSaving,
			int zoneCorrection, double greenwichDay, int greenwichMonth, int greenwichYear) {
		double a = hmsToDH(uHours, uMinutes, uSeconds);
		double b = a + zoneCorrection;
		double c = b + daylightSaving;
		double d = civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear) + (c / 24.0);

		return julianDateYear(d);
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
		dp = dp + (-1.2729 - 0.00013 * t) * Math.sin(l2) + 0.2088 * Math.sin(n2);
		dp = dp - 0.2037 * Math.sin(d2) + (0.1261 - 0.00031 * t) * Math.sin(m1);
		dp = dp + 0.0675 * Math.sin(m2) - (0.0497 - 0.00012 * t) * Math.sin(l2 + m1);
		dp = dp - 0.0342 * Math.sin(d2 - n1) - 0.0261 * Math.sin(d2 + m2);
		dp = dp + 0.0214 * Math.sin(l2 - m1) - 0.0149 * Math.sin(l2 - d2 + m2);
		dp = dp + 0.0124 * Math.sin(l2 - n1) + 0.0114 * Math.sin(d2 - m2);

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
	public static WarningFlag eGstUt(double gsh, double gsm, double gss, double gd, int gm, int gy) {
		double a = civilDateToJulianDate(gd, gm, gy);
		double b = a - 2451545;
		double c = b / 36525;
		double d = 6.697374558 + (2400.051336 * c) + (0.000025862 * c * c);
		double e = d - (24 * Math.floor(d / 24));
		double f = hmsToDH(gsh, gsm, gss);
		double g = f - e;
		double h = g - (24 * Math.floor(g / 24));

		return ((h * 0.9972695663) < (4.0 / 60.0)) ? WarningFlag.WARNING : WarningFlag.OK;
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

	/** Helper function for refract */
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

	/** Helper function for parallaxHA */
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

	/** Helper function for parallaxDec */
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
		l = l + 0.057212 * e * Math.sin(2.0 * me1 - ms - md) + 0.05332 * Math.sin(2.0 * me1 + md);
		l = l + 0.045874 * e * Math.sin(2.0 * me1 - ms) + 0.041024 * e * Math.sin(md - ms);
		l = l - 0.034718 * Math.sin(me1) - e * 0.030465 * Math.sin(ms + md);
		l = l + 0.015326 * Math.sin(2.0 * (me1 - mf)) - 0.012528 * Math.sin(2.0 * mf + md);
		l = l - 0.01098 * Math.sin(2.0 * mf - md) + 0.010674 * Math.sin(4.0 * me1 - md);
		l = l + 0.010034 * Math.sin(3.0 * md) + 0.008548 * Math.sin(4.0 * me1 - 2.0 * md);
		l = l - e * 0.00791 * Math.sin(ms - md + 2.0 * me1) - e * 0.006783 * Math.sin(2.0 * me1 + ms);
		l = l + 0.005162 * Math.sin(md - me1) + e * 0.005 * Math.sin(ms + me1);
		l = l + 0.003862 * Math.sin(4.0 * me1) + e * 0.004049 * Math.sin(md - ms + 2.0 * me1);
		l = l + 0.003996 * Math.sin(2.0 * (md + me1)) + 0.003665 * Math.sin(2.0 * me1 - 3.0 * md);
		l = l + e * 0.002695 * Math.sin(2.0 * md - ms) + 0.002602 * Math.sin(md - 2.0 * (mf + me1));
		l = l + e * 0.002396 * Math.sin(2.0 * (me1 - md) - ms) - 0.002349 * Math.sin(md + me1);
		l = l + e2 * 0.002249 * Math.sin(2.0 * (me1 - ms)) - e * 0.002125 * Math.sin(2.0 * md + ms);
		l = l - e2 * 0.002079 * Math.sin(2.0 * ms) + e2 * 0.002059 * Math.sin(2.0 * (me1 - ms) - md);
		l = l - 0.001773 * Math.sin(md + 2.0 * (me1 - mf)) - 0.001595 * Math.sin(2.0 * (mf + me1));
		l = l + e * 0.00122 * Math.sin(4.0 * me1 - ms - md) - 0.00111 * Math.sin(2.0 * (md + mf));
		l = l + 0.000892 * Math.sin(md - 3.0 * me1) - e * 0.000811 * Math.sin(ms + md + 2.0 * me1);

		l += e * 0.000761 * Math.sin(4.0 * me1 - ms - 2.0 * md);
		l += e2 * 0.000704 * Math.sin(md - 2.0 * (ms + me1));
		l += e * 0.000693 * Math.sin(ms - 2.0 * (md - me1));
		l += e * 0.000598 * Math.sin(2.0 * (me1 - mf) - ms);
		l = l + 0.00055 * Math.sin(md + 4.0 * me1) + 0.000538 * Math.sin(4.0 * md);
		l = l + e * 0.000521 * Math.sin(4.0 * me1 - ms) + 0.000486 * Math.sin(2.0 * md - me1);
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
		g = g + 0.277693 * Math.sin(md - mf) + 0.173238 * Math.sin(2.0 * me1 - mf);
		g = g + 0.055413 * Math.sin(2.0 * me1 + mf - md) + 0.046272 * Math.sin(2.0 * me1 - mf - md);
		g = g + 0.032573 * Math.sin(2.0 * me1 + mf) + 0.017198 * Math.sin(2.0 * md + mf);
		g = g + 0.009267 * Math.sin(2.0 * me1 + md - mf) + 0.008823 * Math.sin(2.0 * md - mf);
		g = g + e * 0.008247 * Math.sin(2.0 * me1 - ms - mf) + 0.004323 * Math.sin(2.0 * (me1 - md) - mf);
		g = g + 0.0042 * Math.sin(2.0 * me1 + mf + md) + e * 0.003372 * Math.sin(mf - ms - 2.0 * me1);
		g += e * 0.002472 * Math.sin(2.0 * me1 + mf - ms - md);
		g += e * 0.002222 * Math.sin(2.0 * me1 + mf - ms);
		g += e * 0.002072 * Math.sin(2.0 * me1 - mf - ms - md);
		g = g + e * 0.001877 * Math.sin(mf - ms + md) + 0.001828 * Math.sin(4.0 * me1 - mf - md);
		g = g - e * 0.001803 * Math.sin(mf + ms) - 0.00175 * Math.sin(3.0 * mf);
		g = g + e * 0.00157 * Math.sin(md - ms - mf) - 0.001487 * Math.sin(mf + me1);
		g = g - e * 0.001481 * Math.sin(mf + ms + md) + e * 0.001417 * Math.sin(mf - ms - md);
		g = g + e * 0.00135 * Math.sin(mf - ms) + 0.00133 * Math.sin(mf - me1);
		g = g + 0.001106 * Math.sin(mf + 3.0 * md) + 0.00102 * Math.sin(4.0 * me1 - mf);
		g = g + 0.000833 * Math.sin(mf + 4.0 * me1 - md) + 0.000781 * Math.sin(md - 3.0 * mf);
		g = g + 0.00067 * Math.sin(mf + 4.0 * me1 - 2.0 * md) + 0.000606 * Math.sin(2.0 * me1 - 3.0 * mf);
		g += 0.000597 * Math.sin(2.0 * (me1 + md) - mf);
		g = g + e * 0.000492 * Math.sin(2.0 * me1 + md - ms - mf) + 0.00045 * Math.sin(2.0 * (md - me1) - mf);
		g = g + 0.000439 * Math.sin(3.0 * md - mf) + 0.000423 * Math.sin(mf + 2.0 * (me1 + md));
		g = g + 0.000422 * Math.sin(2.0 * me1 - mf - 3.0 * md) - e * 0.000367 * Math.sin(ms + mf + 2.0 * me1 - md);
		g = g - e * 0.000353 * Math.sin(ms + mf + 2.0 * me1) + 0.000331 * Math.sin(mf + 4.0 * me1);
		g += e * 0.000317 * Math.sin(2.0 * me1 + mf - ms + md);
		g = g + e2 * 0.000306 * Math.sin(2.0 * (me1 - ms) - mf) - 0.000283 * Math.sin(md + 3.0 * mf);

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

		ml = ml + 0.000233 * s1 + s3 + 0.001964 * s2;
		ms -= 0.001778 * s1;
		md = md + 0.000817 * s1 + s3 + 0.002541 * s2;
		mf = mf + s3 - 0.024691 * s2 - 0.004328 * s4;
		me1 = me1 + 0.002011 * s1 + s3 + 0.001964 * s2;

		double e = 1.0 - (0.002495 + 0.00000752 * t) * t;
		double e2 = e * e;

		ms = Math.toRadians(ms);
		me1 = Math.toRadians(me1);
		mf = Math.toRadians(mf);
		md = Math.toRadians(md);

		double pm = 0.950724 + 0.051818 * Math.cos(md) + 0.009531 * Math.cos(2.0 * me1 - md);
		pm = pm + 0.007843 * Math.cos(2.0 * me1) + 0.002824 * Math.cos(2.0 * md);
		pm = pm + 0.000857 * Math.cos(2.0 * me1 + md) + e * 0.000533 * Math.cos(2.0 * me1 - ms);
		pm += e * 0.000401 * Math.cos(2.0 * me1 - md - ms);
		pm = pm + e * 0.00032 * Math.cos(md - ms) - 0.000271 * Math.cos(me1);
		pm = pm - e * 0.000264 * Math.cos(ms + md) - 0.000198 * Math.cos(2.0 * mf - md);
		pm = pm + 0.000173 * Math.cos(3.0 * md) + 0.000167 * Math.cos(4.0 * me1 - md);
		pm = pm - e * 0.000111 * Math.cos(ms) + 0.000103 * Math.cos(4.0 * me1 - 2.0 * md);
		pm = pm - 0.000084 * Math.cos(2.0 * md - 2.0 * me1) - e * 0.000083 * Math.cos(2.0 * me1 + ms);
		pm = pm + 0.000079 * Math.cos(2.0 * me1 + 2.0 * md) + 0.000072 * Math.cos(4.0 * me1);
		pm = pm + e * 0.000064 * Math.cos(2.0 * me1 - ms + md) - e * 0.000063 * Math.cos(2.0 * me1 + ms - md);
		pm = pm + e * 0.000041 * Math.cos(ms + me1) + e * 0.000035 * Math.cos(2.0 * md - ms);
		pm = pm - 0.000033 * Math.cos(3.0 * md - 2.0 * me1) - 0.00003 * Math.cos(md + me1);
		pm = pm - 0.000029 * Math.cos(2.0 * (mf - me1)) - e * 0.000029 * Math.cos(2.0 * md + ms);
		pm = pm + e2 * 0.000026 * Math.cos(2.0 * (me1 - ms)) - 0.000023 * Math.cos(2.0 * (mf - me1) + md);
		pm += e * 0.000019 * Math.cos(4.0 * me1 - ms - md);

		return pm;
	}

	/**
	 * Calculate distance from the Earth to the Moon (km)
	 * 
	 * Original macro name: MoonDist
	 */
	public static double moonDist(double lh, double lm, double ls, int ds, int zc, double dy, int mn, int yr) {
		double hp = Math.toRadians(moonHP(lh, lm, ls, ds, zc, dy, mn, yr));
		double r = 6378.14 / Math.sin(hp);

		return r;
	}

	/**
	 * Calculate the Moon's angular diameter (degrees)
	 * 
	 * Original macro name: MoonSize
	 */
	public static double moonSize(double lh, double lm, double ls, int ds, int zc, double dy, int mn, int yr) {
		double hp = Math.toRadians(moonHP(lh, lm, ls, ds, zc, dy, mn, yr));
		double r = 6378.14 / Math.sin(hp);
		double th = 384401.0 * 0.5181 / r;

		return th;
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
	 * Calculate the Sun's mean anomaly.
	 * 
	 * Original macro name: SunMeanAnomaly
	 */
	public static double sunMeanAnomaly(double lch, double lcm, double lcs, int ds, int zc, double ld, int lm, int ly) {
		double aa = localCivilTimeGreenwichDay(lch, lcm, lcs, ds, zc, ld, lm, ly);
		int bb = localCivilTimeGreenwichMonth(lch, lcm, lcs, ds, zc, ld, lm, ly);
		int cc = localCivilTimeGreenwichYear(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double ut = localCivilTimeToUniversalTime(lch, lcm, lcs, ds, zc, ld, lm, ly);
		double dj = civilDateToJulianDate(aa, bb, cc) - 2415020;
		double t = (dj / 36525) + (ut / 876600);
		double t2 = t * t;
		double a = 100.0021359 * t;
		double b = 360 * (a - Math.floor(a));
		double m1 = 358.47583 - (0.00015 + 0.0000033 * t) * t2 + b;
		double am = unwind(Math.toRadians(m1));

		return am;
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

		L3710 result1 = sunriseLCTL3710(gd, gm, gy, sr, di, gp);

		double xx;
		if (result1.s != RiseSetStatus.OK) {
			xx = -99.0;
		} else {
			double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
			double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

			if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK) {
				xx = -99.0;
			} else {
				sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
				L3710 result2 = sunriseLCTL3710(gd, gm, gy, sr, di, gp);

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

	/** Helper function for sunriseLCT() */
	public static L3710 sunriseLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0.0, 0.0, y, 0.0, 0.0, di, gp);

		return new L3710(a, x, y, la, s);
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

		L3710 result1 = sunsetLCTL3710(gd, gm, gy, sr, di, gp);

		double xx;
		if (result1.s != RiseSetStatus.OK) {
			xx = -99.0;
		} else {
			double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
			double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

			if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK) {
				xx = -99.0;
			} else {
				sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
				L3710 result2 = sunsetLCTL3710(gd, gm, gy, sr, di, gp);

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
	public static L3710 sunsetLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0.0, 0.0, 0.0, 0.0, 0.0, gd, gm, gy);
		double y = ecDec(a, 0.0, 0.0, 0.0, 0.0, 0.0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeSet(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new L3710(a, x, y, la, s);
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

		L3710 result1 = eSunRSL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK) {
			return result1.s;
		} else {
			double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
			double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);
			sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
			L3710 result2 = eSunRSL3710(gd, gm, gy, sr, di, gp);
			if (result2.s != RiseSetStatus.OK) {
				return result2.s;
			} else {
				x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);

				if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK) {
					RiseSetStatus s = RiseSetStatus.GST_UT_CONVERSION_WARNING;

					return s;
				}

				return result2.s;
			}
		}
	}

	/** Helper function for eSunRS() */
	public static L3710 eSunRSL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new L3710(a, x, y, la, s);
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

		L3710 result1 = sunriseAZL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK) {
			return -99.0;
		}

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK) {
			return -99.0;
		}

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);
		L3710 result2 = sunriseAZL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK) {
			return -99.0;
		}

		return riseSetAzimuthRise(decimalDegreesToDegreeHours(x), 0, 0, result2.y, 0.0, 0.0, di, gp);
	}

	/** Helper function for sunriseAZ() */
	public static L3710 sunriseAZL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new L3710(a, x, y, la, s);
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

		L3710 result1 = sunsetAZL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK) {
			return -99.0;
		}

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK) {
			return -99.0;
		}

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		L3710 result2 = sunsetAZL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK) {
			return -99.0;
		}
		return riseSetAzimuthSet(decimalDegreesToDegreeHours(x), 0, 0, result2.y, 0, 0, di, gp);
	}

	/** Helper function for sunsetAZ() */
	public static L3710 sunsetAZL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeSet(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new L3710(a, x, y, la, s);
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
			returnValue = RiseSetStatus.NEVER_RISES;
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

		L3710 result1 = twilightAMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK)
			return -99.0;

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK)
			return -99.0;

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		L3710 result2 = twilightAMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK)
			return -99.0;

		x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);
		ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		double xx = universalTimeToLocalCivilTime(ut, 0, 0, ds, zc, gd, gm, gy);

		return xx;
	}

	/** Helper function for twilightAMLCT() */
	public static L3710 twilightAMLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new L3710(a, x, y, la, s);
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

		L3710 result1 = twilightPMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != RiseSetStatus.OK)
			return 0.0;

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK)
			return 0.0;

		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		L3710 result2 = twilightPMLCTL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != RiseSetStatus.OK)
			return 0.0;

		x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);
		ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);

		return universalTimeToLocalCivilTime(ut, 0, 0, ds, zc, gd, gm, gy);
	}

	/** Helper function for twilightPMLCT() */
	public static L3710 twilightPMLCTL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeSet(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		return new L3710(a, x, y, la, s);
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

		L3710Twilight result1 = eTwilightL3710(gd, gm, gy, sr, di, gp);

		if (result1.s != TwilightStatus.OK)
			return result1.s;

		double x = localSiderealTimeToGreenwichSiderealTime(result1.la, 0, 0, gl);
		double ut = greenwichSiderealTimeToUniversalTime(x, 0, 0, gd, gm, gy);
		sr = sunLong(ut, 0, 0, 0, 0, gd, gm, gy);

		L3710Twilight result2 = eTwilightL3710(gd, gm, gy, sr, di, gp);

		if (result2.s != TwilightStatus.OK)
			return result2.s;

		x = localSiderealTimeToGreenwichSiderealTime(result2.la, 0, 0, gl);

		if (eGstUt(x, 0, 0, gd, gm, gy) != WarningFlag.OK) {
			result2.s = TwilightStatus.GST_TO_UT_CONVERSION_WARNING;

			return result2.s;
		}

		return result2.s;
	}

	/** Helper function for eTwilight() */
	public static L3710Twilight eTwilightL3710(double gd, int gm, int gy, double sr, double di, double gp) {
		double a = sr + nutatLong(gd, gm, gy) - 0.005694;
		double x = ecRA(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double y = ecDec(a, 0, 0, 0, 0, 0, gd, gm, gy);
		double la = riseSetLocalSiderealTimeRise(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);
		RiseSetStatus s = eRS(decimalDegreesToDegreeHours(x), 0, 0, y, 0, 0, di, gp);

		TwilightStatus ts = TwilightStatus.OK;
		if (s == RiseSetStatus.CIRCUMPOLAR)
			ts = TwilightStatus.LASTS_ALL_NIGHT;
		if (s == RiseSetStatus.NEVER_RISES)
			ts = TwilightStatus.SUN_TOO_FAR_BELOW_HORIZON;

		return new L3710Twilight(a, x, y, la, ts);
	}

	/**
	 * Calculate the angle between two celestial objects
	 */
	public static double angle(double xx1, double xm1, double xs1, double dd1, double dm1, double ds1, double xx2,
			double xm2, double xs2, double dd2, double dm2, double ds2, AngleMeasure s) {
		double a = (s == AngleMeasure.HOURS)
				? degreeHoursToDecimalDegrees(hmsToDH(xx1, xm1, xs1))
				: degreesMinutesSecondsToDecimalDegrees(xx1, xm1, xs1);
		double b = Math.toRadians(a);
		double c = degreesMinutesSecondsToDecimalDegrees(dd1, dm1, ds1);
		double d = Math.toRadians(c);
		double e = (s == AngleMeasure.HOURS)
				? degreeHoursToDecimalDegrees(hmsToDH(xx2, xm2, xs2))
				: degreesMinutesSecondsToDecimalDegrees(xx2, xm2, xs2);
		double f = Math.toRadians(e);
		double g = degreesMinutesSecondsToDecimalDegrees(dd2, dm2, ds2);
		double h = Math.toRadians(g);
		double i = Math.acos(Math.sin(d) * Math.sin(h) + Math.cos(d) * Math.cos(h) * Math.cos(b - f));

		return wToDegrees(i);
	}

	/**
	 * Calculate several planetary properties.
	 *
	 * Original macro names: PlanetLong, PlanetLat, PlanetDist, PlanetHLong1,
	 * PlanetHLong2, PlanetHLat, PlanetRVect
	 */
	public static PlanetCoordinates planetCoordinates(double lh, double lm, double ls, int ds, int zc, double dy,
			int mn, int yr, String s) {
		double a11 = 178.179078;
		double a12 = 415.2057519;
		double a13 = 0.0003011;
		double a14 = 0.0;
		double a21 = 75.899697;
		double a22 = 1.5554889;
		double a23 = 0.0002947;
		double a24 = 0.0;
		double a31 = 0.20561421;
		double a32 = 0.00002046;
		double a33 = -0.00000003;
		double a34 = 0.0;
		double a41 = 7.002881;
		double a42 = 0.0018608;
		double a43 = -0.0000183;
		double a44 = 0.0;
		double a51 = 47.145944;
		double a52 = 1.1852083;
		double a53 = 0.0001739;
		double a54 = 0.0;
		double a61 = 0.3870986;
		double a62 = 6.74;
		double a63 = -0.42;

		double b11 = 342.767053;
		double b12 = 162.5533664;
		double b13 = 0.0003097;
		double b14 = 0.0;
		double b21 = 130.163833;
		double b22 = 1.4080361;
		double b23 = -0.0009764;
		double b24 = 0.0;
		double b31 = 0.00682069;
		double b32 = -0.00004774;
		double b33 = 0.000000091;
		double b34 = 0.0;
		double b41 = 3.393631;
		double b42 = 0.0010058;
		double b43 = -0.000001;
		double b44 = 0.0;
		double b51 = 75.779647;
		double b52 = 0.89985;
		double b53 = 0.00041;
		double b54 = 0.0;
		double b61 = 0.7233316;
		double b62 = 16.92;
		double b63 = -4.4;

		double c11 = 293.737334;
		double c12 = 53.17137642;
		double c13 = 0.0003107;
		double c14 = 0.0;
		double c21 = 334.218203;
		double c22 = 1.8407584;
		double c23 = 0.0001299;
		double c24 = -0.00000119;
		double c31 = 0.0933129;
		double c32 = 0.000092064;
		double c33 = -0.000000077;
		double c34 = 0.0;
		double c41 = 1.850333;
		double c42 = -0.000675;
		double c43 = 0.0000126;
		double c44 = 0.0;
		double c51 = 48.786442;
		double c52 = 0.7709917;
		double c53 = -0.0000014;
		double c54 = -0.00000533;
		double c61 = 1.5236883;
		double c62 = 9.36;
		double c63 = -1.52;

		double d11 = 238.049257;
		double d12 = 8.434172183;
		double d13 = 0.0003347;
		double d14 = -0.00000165;
		double d21 = 12.720972;
		double d22 = 1.6099617;
		double d23 = 0.00105627;
		double d24 = -0.00000343;
		double d31 = 0.04833475;
		double d32 = 0.00016418;
		double d33 = -0.0000004676;
		double d34 = -0.0000000017;
		double d41 = 1.308736;
		double d42 = -0.0056961;
		double d43 = 0.0000039;
		double d44 = 0.0;
		double d51 = 99.443414;
		double d52 = 1.01053;
		double d53 = 0.00035222;
		double d54 = -0.00000851;
		double d61 = 5.202561;
		double d62 = 196.74;
		double d63 = -9.4;

		double e11 = 266.564377;
		double e12 = 3.398638567;
		double e13 = 0.0003245;
		double e14 = -0.0000058;
		double e21 = 91.098214;
		double e22 = 1.9584158;
		double e23 = 0.00082636;
		double e24 = 0.00000461;
		double e31 = 0.05589232;
		double e32 = -0.0003455;
		double e33 = -0.000000728;
		double e34 = 0.00000000074;
		double e41 = 2.492519;
		double e42 = -0.0039189;
		double e43 = -0.00001549;
		double e44 = 0.00000004;
		double e51 = 112.790414;
		double e52 = 0.8731951;
		double e53 = -0.00015218;
		double e54 = -0.00000531;
		double e61 = 9.554747;
		double e62 = 165.6;
		double e63 = -8.88;

		double f11 = 244.19747;
		double f12 = 1.194065406;
		double f13 = 0.000316;
		double f14 = -0.0000006;
		double f21 = 171.548692;
		double f22 = 1.4844328;
		double f23 = 0.0002372;
		double f24 = -0.00000061;
		double f31 = 0.0463444;
		double f32a = -0.00002658;
		double f33 = 0.000000077;
		double f34 = 0.0;
		double f41 = 0.772464;
		double f42 = 0.0006253;
		double f43 = 0.0000395;
		double f44 = 0.0;
		double f51 = 73.477111;
		double f52 = 0.4986678;
		double f53 = 0.0013117;
		double f54 = 0.0;
		double f61 = 19.21814;
		double f62 = 65.8;
		double f63 = -7.19;

		double g11 = 84.457994;
		double g12 = 0.6107942056;
		double g13 = 0.0003205;
		double g14 = -0.0000006;
		double g21 = 46.727364;
		double g22 = 1.4245744;
		double g23 = 0.00039082;
		double g24 = -0.000000605;
		double g31 = 0.00899704;
		double g32 = 0.00000633;
		double g33 = -0.000000002;
		double g34 = 0.0;
		double g41 = 1.779242;
		double g42 = -0.0095436;
		double g43 = -0.0000091;
		double g44 = 0.0;
		double g51 = 130.681389;
		double g52 = 1.098935;
		double g53 = 0.00024987;
		double g54 = -0.000004718;
		double g61 = 30.10957;
		double g62 = 62.2;
		double g63 = -6.87;

		List<PlanetDataPrecise> planetData = new ArrayList<>();

		planetData.add(new PlanetDataPrecise("", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

		int ip = 0;
		double b = localCivilTimeToUniversalTime(lh, lm, ls, ds, zc, dy, mn, yr);
		double gd = localCivilTimeGreenwichDay(lh, lm, ls, ds, zc, dy, mn, yr);
		int gm = localCivilTimeGreenwichMonth(lh, lm, ls, ds, zc, dy, mn, yr);
		int gy = localCivilTimeGreenwichYear(lh, lm, ls, ds, zc, dy, mn, yr);
		double a = civilDateToJulianDate(gd, gm, gy);
		double t = ((a - 2415020.0) / 36525.0) + (b / 876600.0);

		double a0 = a11;
		double a1 = a12;
		double a2 = a13;
		double a3 = a14;
		double b0 = a21;
		double b1 = a22;
		double b2 = a23;
		double b3 = a24;
		double c0 = a31;
		double c1 = a32;
		double c2 = a33;
		double c3 = a34;
		double d0 = a41;
		double d1 = a42;
		double d2 = a43;
		double d3 = a44;
		double e0 = a51;
		double e1 = a52;
		double e2 = a53;
		double e3 = a54;
		double f = a61;
		double g = a62;
		double h = a63;
		double aa = a1 * t;
		b = 360.0 * (aa - Math.floor(aa));
		double c = a0 + b + (a3 * t + a2) * t * t;

		planetData.add(
				new PlanetDataPrecise(
						"Mercury",
						c - 360.0 * Math.floor(c / 360.0),
						(a1 * 0.009856263) + (a2 + a3) / 36525.0,
						((b3 * t + b2) * t + b1) * t + b0,
						((c3 * t + c2) * t + c1) * t + c0,
						((d3 * t + d2) * t + d1) * t + d0,
						((e3 * t + e2) * t + e1) * t + e0,
						f,
						g,
						h,
						0));

		a0 = b11;
		a1 = b12;
		a2 = b13;
		a3 = b14;
		b0 = b21;
		b1 = b22;
		b2 = b23;
		b3 = b24;
		c0 = b31;
		c1 = b32;
		c2 = b33;
		c3 = b34;
		d0 = b41;
		d1 = b42;
		d2 = b43;
		d3 = b44;
		e0 = b51;
		e1 = b52;
		e2 = b53;
		e3 = b54;
		f = b61;
		g = b62;
		h = b63;
		aa = a1 * t;
		b = 360.0 * (aa - Math.floor(aa));
		c = a0 + b + (a3 * t + a2) * t * t;

		planetData.add(
				new PlanetDataPrecise(
						"Venus",
						c - 360.0 * Math.floor(c / 360.0),
						(a1 * 0.009856263) + (a2 + a3) / 36525.0,
						((b3 * t + b2) * t + b1) * t + b0,
						((c3 * t + c2) * t + c1) * t + c0,
						((d3 * t + d2) * t + d1) * t + d0,
						((e3 * t + e2) * t + e1) * t + e0,
						f,
						g,
						h,
						0));

		a0 = c11;
		a1 = c12;
		a2 = c13;
		a3 = c14;
		b0 = c21;
		b1 = c22;
		b2 = c23;
		b3 = c24;
		c0 = c31;
		c1 = c32;
		c2 = c33;
		c3 = c34;
		d0 = c41;
		d1 = c42;
		d2 = c43;
		d3 = c44;
		e0 = c51;
		e1 = c52;
		e2 = c53;
		e3 = c54;
		f = c61;
		g = c62;
		h = c63;

		aa = a1 * t;
		b = 360.0 * (aa - Math.floor(aa));
		c = a0 + b + (a3 * t + a2) * t * t;

		planetData.add(
				new PlanetDataPrecise(
						"Mars",
						c - 360.0 * Math.floor(c / 360.0),
						(a1 * 0.009856263) + (a2 + a3) / 36525.0,
						((b3 * t + b2) * t + b1) * t + b0,
						((c3 * t + c2) * t + c1) * t + c0,
						((d3 * t + d2) * t + d1) * t + d0,
						((e3 * t + e2) * t + e1) * t + e0,
						f,
						g,
						h,
						0));

		a0 = d11;
		a1 = d12;
		a2 = d13;
		a3 = d14;
		b0 = d21;
		b1 = d22;
		b2 = d23;
		b3 = d24;
		c0 = d31;
		c1 = d32;
		c2 = d33;
		c3 = d34;
		d0 = d41;
		d1 = d42;
		d2 = d43;
		d3 = d44;
		e0 = d51;
		e1 = d52;
		e2 = d53;
		e3 = d54;
		f = d61;
		g = d62;
		h = d63;

		aa = a1 * t;
		b = 360.0 * (aa - Math.floor(aa));
		c = a0 + b + (a3 * t + a2) * t * t;

		planetData.add(
				new PlanetDataPrecise(
						"Jupiter",
						c - 360.0 * Math.floor(c / 360.0),
						(a1 * 0.009856263) + (a2 + a3) / 36525.0,
						((b3 * t + b2) * t + b1) * t + b0,
						((c3 * t + c2) * t + c1) * t + c0,
						((d3 * t + d2) * t + d1) * t + d0,
						((e3 * t + e2) * t + e1) * t + e0,
						f,
						g,
						h,
						0));

		a0 = e11;
		a1 = e12;
		a2 = e13;
		a3 = e14;
		b0 = e21;
		b1 = e22;
		b2 = e23;
		b3 = e24;
		c0 = e31;
		c1 = e32;
		c2 = e33;
		c3 = e34;
		d0 = e41;
		d1 = e42;
		d2 = e43;
		d3 = e44;
		e0 = e51;
		e1 = e52;
		e2 = e53;
		e3 = e54;
		f = e61;
		g = e62;
		h = e63;

		aa = a1 * t;
		b = 360.0 * (aa - Math.floor(aa));
		c = a0 + b + (a3 * t + a2) * t * t;

		planetData.add(
				new PlanetDataPrecise(
						"Saturn",
						c - 360.0 * Math.floor(c / 360.0),
						(a1 * 0.009856263) + (a2 + a3) / 36525.0,
						((b3 * t + b2) * t + b1) * t + b0,
						((c3 * t + c2) * t + c1) * t + c0,
						((d3 * t + d2) * t + d1) * t + d0,
						((e3 * t + e2) * t + e1) * t + e0,
						f,
						g,
						h,
						0));

		a0 = f11;
		a1 = f12;
		a2 = f13;
		a3 = f14;
		b0 = f21;
		b1 = f22;
		b2 = f23;
		b3 = f24;
		c0 = f31;
		c1 = f32a;
		c2 = f33;
		c3 = f34;
		d0 = f41;
		d1 = f42;
		d2 = f43;
		d3 = f44;
		e0 = f51;
		e1 = f52;
		e2 = f53;
		e3 = f54;
		f = f61;
		g = f62;
		h = f63;

		aa = a1 * t;
		b = 360.0 * (aa - Math.floor(aa));
		c = a0 + b + (a3 * t + a2) * t * t;

		planetData.add(
				new PlanetDataPrecise(
						"Uranus",
						c - 360.0 * Math.floor(c / 360.0),
						(a1 * 0.009856263) + (a2 + a3) / 36525.0,
						((b3 * t + b2) * t + b1) * t + b0,
						((c3 * t + c2) * t + c1) * t + c0,
						((d3 * t + d2) * t + d1) * t + d0,
						((e3 * t + e2) * t + e1) * t + e0,
						f,
						g,
						h,
						0));

		a0 = g11;
		a1 = g12;
		a2 = g13;
		a3 = g14;
		b0 = g21;
		b1 = g22;
		b2 = g23;
		b3 = g24;
		c0 = g31;
		c1 = g32;
		c2 = g33;
		c3 = g34;
		d0 = g41;
		d1 = g42;
		d2 = g43;
		d3 = g44;
		e0 = g51;
		e1 = g52;
		e2 = g53;
		e3 = g54;
		f = g61;
		g = g62;
		h = g63;

		aa = a1 * t;
		b = 360.0 * (aa - Math.floor(aa));
		c = a0 + b + (a3 * t + a2) * t * t;

		planetData.add(
				new PlanetDataPrecise(
						"Neptune",
						c - 360.0 * Math.floor(c / 360.0),
						(a1 * 0.009856263) + (a2 + a3) / 36525.0,
						((b3 * t + b2) * t + b1) * t + b0,
						((c3 * t + c2) * t + c1) * t + c0,
						((d3 * t + d2) * t + d1) * t + d0,
						((e3 * t + e2) * t + e1) * t + e0,
						f,
						g,
						h,
						0));

		PlanetDataPrecise checkPlanet = planetData.stream()
				.filter(p -> p.Name == s)
				.findFirst()
				.orElse(new PlanetDataPrecise("NotFound", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

		if (checkPlanet.Name == "NotFound")
			return (new PlanetCoordinates(wToDegrees(unwind(0)), wToDegrees(unwind(0)), wToDegrees(unwind(0)),
					wToDegrees(unwind(0)), wToDegrees(unwind(0)), wToDegrees(unwind(0)), wToDegrees(unwind(0))));

		double li = 0.0;
		double ms = sunMeanAnomaly(lh, lm, ls, ds, zc, dy, mn, yr);
		double sr = Math.toRadians(sunLong(lh, lm, ls, ds, zc, dy, mn, yr));
		double re = sunDist(lh, lm, ls, ds, zc, dy, mn, yr);
		double lg = sr + Math.PI;

		double l0 = 0.0;
		double s0 = 0.0;
		double p0 = 0.0;
		double vo = 0.0;
		double lp1 = 0.0;
		double ll = 0.0;
		double rd = 0.0;
		double pd = 0.0;
		double sp = 0.0;
		double ci = 0.0;

		for (int k = 1; k <= 3; k++) {
			for (PlanetDataPrecise planetDataPrecise : planetData) {
				planetDataPrecise.APValue = Math
						.toRadians(planetDataPrecise.Value1 - planetDataPrecise.Value3 - li * planetDataPrecise.Value2);
			}

			double qa = 0.0;
			double qb = 0.0;
			double qc = 0.0;
			double qd = 0.0;
			double qe = 0.0;
			double qf = 0.0;
			double qg = 0.0;

			if (s == "Mercury") {
				PlanetLongL4685 planetLongL4685 = planetLongL4685(planetData);

				qa = planetLongL4685.qa;
				qb = planetLongL4685.qb;
			}

			if (s == "Venus") {
				PlanetLongL4735 planetLongL4735 = planetLongL4735(planetData, ms, t);

				qa = planetLongL4735.qa;
				qb = planetLongL4735.qb;
				qc = planetLongL4735.qc;
				qe = planetLongL4735.qe;
			}

			if (s == "Mars") {
				PlanetLongL4810 returnValue = planetLongL4810(planetData, ms);

				qc = returnValue.qc;
				qe = returnValue.qe;
				qa = returnValue.qa;
				qb = returnValue.qb;
			}

			PlanetDataPrecise matchPlanet = planetData.stream()
					.filter(p -> p.Name == s)
					.findFirst()
					.orElse(new PlanetDataPrecise("NotFound", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

			Set<String> planetSet = new HashSet<>(Set.of("Jupiter", "Saturn", "Uranus", "Neptune"));
			if (planetSet.contains(s)) {
				PlanetLongL4945 returnValue = planetLongL4945(t, matchPlanet);

				qa = returnValue.qa;
				qb = returnValue.qb;
				qc = returnValue.qc;
				qd = returnValue.qd;
				qe = returnValue.qe;
				qf = returnValue.qf;
				qg = returnValue.qg;
			}

			double ec = matchPlanet.Value4 + qd;
			double am = matchPlanet.APValue + qe;
			double at = trueAnomaly(am, ec);
			double pvv = (matchPlanet.Value7 + qf) * (1.0 - ec * ec) / (1.0 + ec * Math.cos(at));
			double lp = wToDegrees(at) + matchPlanet.Value3 + wToDegrees(qc - qe);
			lp = Math.toRadians(lp);
			double om = Math.toRadians(matchPlanet.Value6);
			double lo = lp - om;
			double so = Math.sin(lo);
			double co = Math.cos(lo);
			double inn = Math.toRadians(matchPlanet.Value5);
			pvv += qb;
			sp = so * Math.sin(inn);
			double y = so * Math.cos(inn);
			double ps = Math.asin(sp) + qg;
			sp = Math.sin(ps);
			pd = Math.atan2(y, co) + om + Math.toRadians(qa);
			pd = unwind(pd);
			ci = Math.cos(ps);
			rd = pvv * ci;
			ll = pd - lg;
			double rh = re * re + pvv * pvv - 2.0 * re * pvv * ci * Math.cos(ll);
			rh = Math.sqrt(rh);
			li = rh * 0.005775518;

			if (k == 1) {
				l0 = pd;
				s0 = ps;
				p0 = pvv;
				vo = rh;
				lp1 = lp;
			}
		}

		double l1 = Math.sin(ll);
		double l2 = Math.cos(ll);

		double ep = (ip < 3)
				? Math.atan(-1.0 * rd * l1 / (re - rd * l2)) + lg + Math.PI
				: Math.atan(re * l1 / (rd - re * l2)) + pd;
		ep = unwind(ep);

		double bp = Math.atan(rd * sp * Math.sin(ep - pd) / (ci * re * l1));

		double planetLongitude = wToDegrees(unwind(ep));
		double planetLatitude = wToDegrees(unwind(bp));
		double planetDistanceAU = vo;
		double planetHLong1 = wToDegrees(lp1);
		double planetHLong2 = wToDegrees(l0);
		double planetHLat = wToDegrees(s0);
		double planetRVect = p0;

		return new PlanetCoordinates(planetLongitude, planetLatitude, planetDistanceAU, planetHLong1, planetHLong2,
				planetHLat, planetRVect);
	}

	/** Helper function for planetCoordinates() */
	public static PlanetLongL4685 planetLongL4685(List<PlanetDataPrecise> pl) {
		double qa = 0.00204 * Math.cos(5.0 * pl.get(2).APValue - 2.0 * pl.get(1).APValue + 0.21328);
		qa += 0.00103 * Math.cos(2.0 * pl.get(2).APValue - pl.get(1).APValue - 2.8046);
		qa += 0.00091 * Math.cos(2.0 * pl.get(4).APValue - pl.get(1).APValue - 0.64582);
		qa += 0.00078 * Math.cos(5.0 * pl.get(2).APValue - 3.0 * pl.get(1).APValue + 0.17692);

		double qb = 0.000007525 * Math.cos(2.0 * pl.get(4).APValue - pl.get(1).APValue + 0.925251);
		qb += 0.000006802 * Math.cos(5.0 * pl.get(2).APValue - 3.0 * pl.get(1).APValue - 4.53642);
		qb += 0.000005457 * Math.cos(2.0 * pl.get(2).APValue - 2.0 * pl.get(1).APValue - 1.24246);
		qb += 0.000003569 * Math.cos(5.0 * pl.get(2).APValue - pl.get(1).APValue - 1.35699);

		return new PlanetLongL4685(qa, qb);
	}

	/** Helper function for planetCoordinates() */
	public static PlanetLongL4735 planetLongL4735(List<PlanetDataPrecise> pl, double ms, double t) {
		double qc = 0.00077 * Math.sin(4.1406 + t * 2.6227);
		qc = Math.toRadians(qc);
		double qe = qc;

		double qa = 0.00313 * Math.cos(2.0 * ms - 2.0 * pl.get(2).APValue - 2.587);
		qa += 0.00198 * Math.cos(3.0 * ms - 3.0 * pl.get(2).APValue + 0.044768);
		qa += 0.00136 * Math.cos(ms - pl.get(2).APValue - 2.0788);
		qa += 0.00096 * Math.cos(3.0 * ms - 2.0 * pl.get(2).APValue - 2.3721);
		qa += 0.00082 * Math.cos(pl.get(4).APValue - pl.get(2).APValue - 3.6318);

		double qb = 0.000022501 * Math.cos(2.0 * ms - 2.0 * pl.get(2).APValue - 1.01592);
		qb += 0.000019045 * Math.cos(3.0 * ms - 3.0 * pl.get(2).APValue + 1.61577);
		qb += 0.000006887 * Math.cos(pl.get(4).APValue - pl.get(2).APValue - 2.06106);
		qb += 0.000005172 * Math.cos(ms - pl.get(2).APValue - 0.508065);
		qb += 0.00000362 * Math.cos(5.0 * ms - 4.0 * pl.get(2).APValue - 1.81877);
		qb += 0.000003283 * Math.cos(4.0 * ms - 4.0 * pl.get(2).APValue + 1.10851);
		qb += 0.000003074 * Math.cos(2.0 * pl.get(4).APValue - 2.0 * pl.get(2).APValue - 0.962846);

		return new PlanetLongL4735(qa, qb, qc, qe);
	}

	/** Helper function for planetCoordinates() */
	public static PlanetLongL4810 planetLongL4810(List<PlanetDataPrecise> pl, double ms) {
		double a = 3.0 * pl.get(4).APValue - 8.0 * pl.get(3).APValue + 4.0 * ms;
		double sa = Math.sin(a);
		double ca = Math.cos(a);
		double qc = -(0.01133 * sa + 0.00933 * ca);
		qc = Math.toRadians(qc);
		double qe = qc;

		double qa = 0.00705 * Math.cos(pl.get(4).APValue - pl.get(3).APValue - 0.85448);
		qa += 0.00607 * Math.cos(2.0 * pl.get(4).APValue - pl.get(3).APValue - 3.2873);
		qa += 0.00445 * Math.cos(2.0 * pl.get(4).APValue - 2.0 * pl.get(3).APValue - 3.3492);
		qa += 0.00388 * Math.cos(ms - 2.0 * pl.get(3).APValue + 0.35771);
		qa += 0.00238 * Math.cos(ms - pl.get(3).APValue + 0.61256);
		qa += 0.00204 * Math.cos(2.0 * ms - 3.0 * pl.get(3).APValue + 2.7688);
		qa += 0.00177 * Math.cos(3.0 * pl.get(3).APValue - pl.get(2).APValue - 1.0053);
		qa += 0.00136 * Math.cos(2.0 * ms - 4.0 * pl.get(3).APValue + 2.6894);
		qa += 0.00104 * Math.cos(pl.get(4).APValue + 0.30749);

		double qb = 0.000053227 * Math.cos(pl.get(4).APValue - pl.get(3).APValue + 0.717864);
		qb += 0.000050989 * Math.cos(2.0 * pl.get(4).APValue - 2.0 * pl.get(3).APValue - 1.77997);
		qb += 0.000038278 * Math.cos(2.0 * pl.get(4).APValue - pl.get(3).APValue - 1.71617);
		qb += 0.000015996 * Math.cos(ms - pl.get(3).APValue - 0.969618);
		qb += 0.000014764 * Math.cos(2.0 * ms - 3.0 * pl.get(3).APValue + 1.19768);
		qb += 0.000008966 * Math.cos(pl.get(4).APValue - 2.0 * pl.get(3).APValue + 0.761225);
		qb += 0.000007914 * Math.cos(3.0 * pl.get(4).APValue - 2.0 * pl.get(3).APValue - 2.43887);
		qb += 0.000007004 * Math.cos(2.0 * pl.get(4).APValue - 3.0 * pl.get(3).APValue - 1.79573);
		qb += 0.00000662 * Math.cos(ms - 2.0 * pl.get(3).APValue + 1.97575);
		qb += 0.00000493 * Math.cos(3.0 * pl.get(4).APValue - 3.0 * pl.get(3).APValue - 1.33069);
		qb += 0.000004693 * Math.cos(3.0 * ms - 5.0 * pl.get(3).APValue + 3.32665);
		qb += 0.000004571 * Math.cos(2.0 * ms - 4.0 * pl.get(3).APValue + 4.27086);
		qb += 0.000004409 * Math.cos(3.0 * pl.get(4).APValue - pl.get(3).APValue - 2.02158);

		return new PlanetLongL4810(a, sa, ca, qc, qe, qa, qb);
	}

	/** Helper function for planetCoordinates() */
	public static PlanetLongL4945 planetLongL4945(double t, PlanetDataPrecise planet) {
		double qa = 0.0;
		double qb = 0.0;
		double qc = 0.0;
		double qd = 0.0;
		double qe = 0.0;
		double qf = 0.0;
		double qg = 0.0;
		double vk = 0.0;
		double ja = 0.0;
		double jb = 0.0;
		double jc = 0.0;

		double j1 = t / 5.0 + 0.1;
		double j2 = unwind(4.14473 + 52.9691 * t);
		double j3 = unwind(4.641118 + 21.32991 * t);
		double j4 = unwind(4.250177 + 7.478172 * t);
		double j5 = 5.0 * j3 - 2.0 * j2;
		double j6 = 2.0 * j2 - 6.0 * j3 + 3.0 * j4;

		Set<String> planetSet1 = new HashSet<>(Set.of("Mercury", "Venus", "Mars"));
		if (planetSet1.contains(planet.Name))
			return new PlanetLongL4945(qa, qb, qc, qd, qe, qf, qg);

		Set<String> planetSet2 = new HashSet<>(Set.of("Jupiter", "Saturn"));
		if (planetSet2.contains(planet.Name)) {
			double j7 = j3 - j2;
			double u1 = Math.sin(j3);
			double u2 = Math.cos(j3);
			double u3 = Math.sin(2.0 * j3);
			double u4 = Math.cos(2.0 * j3);
			double u5 = Math.sin(j5);
			double u6 = Math.cos(j5);
			double u7 = Math.sin(2.0 * j5);
			double u8a = Math.sin(j6);
			double u9 = Math.sin(j7);
			double ua = Math.cos(j7);
			double ub = Math.sin(2.0 * j7);
			double uc = Math.cos(2.0 * j7);
			double ud = Math.sin(3.0 * j7);
			double ue = Math.cos(3.0 * j7);
			double uf = Math.sin(4.0 * j7);
			double ug = Math.cos(4.0 * j7);
			double vh = Math.cos(5.0 * j7);

			if (planet.Name == "Saturn") {
				double ui = Math.sin(3.0 * j3);
				double uj = Math.cos(3.0 * j3);
				double uk = Math.sin(4.0 * j3);
				double ul = Math.cos(4.0 * j3);
				double vi = Math.cos(2.0 * j5);
				double un = Math.sin(5.0 * j7);
				double j8 = j4 - j3;
				double uo = Math.sin(2.0 * j8);
				double up = Math.cos(2.0 * j8);
				double uq = Math.sin(3.0 * j8);
				double ur = Math.cos(3.0 * j8);

				qc = 0.007581 * u7 - 0.007986 * u8a - 0.148811 * u9;
				qc -= (0.814181 - (0.01815 - 0.016714 * j1) * j1) * u5;
				qc -= (0.010497 - (0.160906 - 0.0041 * j1) * j1) * u6;
				qc = qc - 0.015208 * ud - 0.006339 * uf - 0.006244 * u1;
				qc = qc - 0.0165 * ub * u1 - 0.040786 * ub;
				qc = qc + (0.008931 + 0.002728 * j1) * u9 * u1 - 0.005775 * ud * u1;
				qc = qc + (0.081344 + 0.003206 * j1) * ua * u1 + 0.015019 * uc * u1;
				qc = qc + (0.085581 + 0.002494 * j1) * u9 * u2 + 0.014394 * uc * u2;
				qc = qc + (0.025328 - 0.003117 * j1) * ua * u2 + 0.006319 * ue * u2;
				qc = qc + 0.006369 * u9 * u3 + 0.009156 * ub * u3 + 0.007525 * uq * u3;
				qc = qc - 0.005236 * ua * u4 - 0.007736 * uc * u4 - 0.007528 * ur * u4;
				qc = Math.toRadians(qc);

				qd = (-7927.0 + (2548.0 + 91.0 * j1) * j1) * u5;
				qd = qd + (13381.0 + (1226.0 - 253.0 * j1) * j1) * u6 + (248.0 - 121.0 * j1) * u7;
				qd = qd - (305.0 + 91.0 * j1) * vi + 412.0 * ub + 12415.0 * u1;
				qd = qd + (390.0 - 617.0 * j1) * u9 * u1 + (165.0 - 204.0 * j1) * ub * u1;
				qd = qd + 26599.0 * ua * u1 - 4687.0 * uc * u1 - 1870.0 * ue * u1 - 821.0 * ug * u1;
				qd = qd - 377.0 * vh * u1 + 497.0 * up * u1 + (163.0 - 611.0 * j1) * u2;
				qd = qd - 12696.0 * u9 * u2 - 4200.0 * ub * u2 - 1503.0 * ud * u2 - 619.0 * uf * u2;
				qd = qd - 268.0 * un * u2 - (282.0 + 1306.0 * j1) * ua * u2;
				qd = qd + (-86.0 + 230.0 * j1) * uc * u2 + 461.0 * uo * u2 - 350.0 * u3;
				qd = qd + (2211.0 - 286.0 * j1) * u9 * u3 - 2208.0 * ub * u3 - 568.0 * ud * u3;
				qd = qd - 346.0 * uf * u3 - (2780.0 + 222.0 * j1) * ua * u3;
				qd = qd + (2022.0 + 263.0 * j1) * uc * u3 + 248.0 * ue * u3 + 242.0 * uq * u3;
				qd = qd + 467.0 * ur * u3 - 490.0 * u4 - (2842.0 + 279.0 * j1) * u9 * u4;
				qd = qd + (128.0 + 226.0 * j1) * ub * u4 + 224.0 * ud * u4;
				qd = qd + (-1594.0 + 282.0 * j1) * ua * u4 + (2162.0 - 207.0 * j1) * uc * u4;
				qd = qd + 561.0 * ue * u4 + 343.0 * ug * u4 + 469.0 * uq * u4 - 242.0 * ur * u4;
				qd = qd - 205.0 * u9 * ui + 262.0 * ud * ui + 208.0 * ua * uj - 271.0 * ue * uj;
				qd = qd - 382.0 * ue * uk - 376.0 * ud * ul;
				qd *= 0.0000001;

				vk = (0.077108 + (0.007186 - 0.001533 * j1) * j1) * u5;
				vk -= 0.007075 * u9;
				vk += (0.045803 - (0.014766 + 0.000536 * j1) * j1) * u6;
				vk = vk - 0.072586 * u2 - 0.075825 * u9 * u1 - 0.024839 * ub * u1;
				vk = vk - 0.008631 * ud * u1 - 0.150383 * ua * u2;
				vk = vk + 0.026897 * uc * u2 + 0.010053 * ue * u2;
				vk = vk - (0.013597 + 0.001719 * j1) * u9 * u3 + 0.011981 * ub * u4;
				vk -= (0.007742 - 0.001517 * j1) * ua * u3;
				vk += (0.013586 - 0.001375 * j1) * uc * u3;
				vk -= (0.013667 - 0.001239 * j1) * u9 * u4;
				vk += (0.014861 + 0.001136 * j1) * ua * u4;
				vk -= (0.013064 + 0.001628 * j1) * uc * u4;
				qe = qc - (Math.toRadians(vk) / planet.Value4);

				qf = 572.0 * u5 - 1590.0 * ub * u2 + 2933.0 * u6 - 647.0 * ud * u2;
				qf = qf + 33629.0 * ua - 344.0 * uf * u2 - 3081.0 * uc + 2885.0 * ua * u2;
				qf = qf - 1423.0 * ue + (2172.0 + 102.0 * j1) * uc * u2 - 671.0 * ug;
				qf = qf + 296.0 * ue * u2 - 320.0 * vh - 267.0 * ub * u3 + 1098.0 * u1;
				qf = qf - 778.0 * ua * u3 - 2812.0 * u9 * u1 + 495.0 * uc * u3 + 688.0 * ub * u1;
				qf = qf + 250.0 * ue * u3 - 393.0 * ud * u1 - 856.0 * u9 * u4 - 228.0 * uf * u1;
				qf = qf + 441.0 * ub * u4 + 2138.0 * ua * u1 + 296.0 * uc * u4 - 999.0 * uc * u1;
				qf = qf + 211.0 * ue * u4 - 642.0 * ue * u1 - 427.0 * u9 * ui - 325.0 * ug * u1;
				qf = qf + 398.0 * ud * ui - 890.0 * u2 + 344.0 * ua * uj + 2206.0 * u9 * u2;
				qf -= 427.0 * ue * uj;
				qf *= 0.000001;

				qg = 0.000747 * ua * u1 + 0.001069 * ua * u2 + 0.002108 * ub * u3;
				qg = qg + 0.001261 * uc * u3 + 0.001236 * ub * u4 - 0.002075 * uc * u4;
				qg = Math.toRadians(qg);

				return new PlanetLongL4945(qa, qb, qc, qd, qe, qf, qg);
			}

			qc = (0.331364 - (0.010281 + 0.004692 * j1) * j1) * u5;
			qc += (0.003228 - (0.064436 - 0.002075 * j1) * j1) * u6;
			qc -= (0.003083 + (0.000275 - 0.000489 * j1) * j1) * u7;
			qc = qc + 0.002472 * u8a + 0.013619 * u9 + 0.018472 * ub;
			qc = qc + 0.006717 * ud + 0.002775 * uf + 0.006417 * ub * u1;
			qc = qc + (0.007275 - 0.001253 * j1) * u9 * u1 + 0.002439 * ud * u1;
			qc = qc - (0.035681 + 0.001208 * j1) * u9 * u2 - 0.003767 * uc * u1;
			qc = qc - (0.033839 + 0.001125 * j1) * ua * u1 - 0.004261 * ub * u2;
			qc = qc + (0.001161 * j1 - 0.006333) * ua * u2 + 0.002178 * u2;
			qc = qc - 0.006675 * uc * u2 - 0.002664 * ue * u2 - 0.002572 * u9 * u3;
			qc = qc - 0.003567 * ub * u3 + 0.002094 * ua * u4 + 0.003342 * uc * u4;
			qc = Math.toRadians(qc);

			qd = (3606.0 + (130.0 - 43.0 * j1) * j1) * u5 + (1289.0 - 580.0 * j1) * u6;
			qd = qd - 6764.0 * u9 * u1 - 1110.0 * ub * u1 - 224.0 * ud * u1 - 204.0 * u1;
			qd = qd + (1284.0 + 116.0 * j1) * ua * u1 + 188.0 * uc * u1;
			qd = qd + (1460.0 + 130.0 * j1) * u9 * u2 + 224.0 * ub * u2 - 817.0 * u2;
			qd = qd + 6074.0 * u2 * ua + 992.0 * uc * u2 + 508.0 * ue * u2 + 230.0 * ug * u2;
			qd = qd + 108.0 * vh * u2 - (956.0 + 73.0 * j1) * u9 * u3 + 448.0 * ub * u3;
			qd = qd + 137.0 * ud * u3 + (108.0 * j1 - 997.0) * ua * u3 + 480.0 * uc * u3;
			qd = qd + 148.0 * ue * u3 + (99.0 * j1 - 956.0) * u9 * u4 + 490.0 * ub * u4;
			qd = qd + 158.0 * ud * u4 + 179.0 * u4 + (1024.0 + 75.0 * j1) * ua * u4;
			qd = qd - 437.0 * uc * u4 - 132.0 * ue * u4;
			qd *= 0.0000001;

			vk = (0.007192 - 0.003147 * j1) * u5 - 0.004344 * u1;
			vk += (j1 * (0.000197 * j1 - 0.000675) - 0.020428) * u6;
			vk = vk + 0.034036 * ua * u1 + (0.007269 + 0.000672 * j1) * u9 * u1;
			vk = vk + 0.005614 * uc * u1 + 0.002964 * ue * u1 + 0.037761 * u9 * u2;
			vk = vk + 0.006158 * ub * u2 - 0.006603 * ua * u2 - 0.005356 * u9 * u3;
			vk = vk + 0.002722 * ub * u3 + 0.004483 * ua * u3;
			vk = vk - 0.002642 * uc * u3 + 0.004403 * u9 * u4;
			vk = vk - 0.002536 * ub * u4 + 0.005547 * ua * u4 - 0.002689 * uc * u4;
			qe = qc - (Math.toRadians(vk) / planet.Value4);

			qf = 205.0 * ua - 263.0 * u6 + 693.0 * uc + 312.0 * ue + 147.0 * ug + 299.0 * u9 * u1;
			qf = qf + 181.0 * uc * u1 + 204.0 * ub * u2 + 111.0 * ud * u2 - 337.0 * ua * u2;
			qf -= 111.0 * uc * u2;
			qf *= 0.000001;

			return new PlanetLongL4945(qa, qb, qc, qd, qe, qf, qg);
		}

		Set<String> planetSet3 = new HashSet<>(Set.of("Uranus", "Neptune"));
		if (planetSet3.contains(planet.Name)) {
			double j8 = unwind(1.46205 + 3.81337 * t);
			double j9 = 2.0 * j8 - j4;
			double vj = Math.sin(j9);
			double uu = Math.sin(j9);
			double uv = Math.sin(2.0 * j9);
			double uw = Math.cos(2.0 * j9);

			if (planet.Name == "Neptune") {
				ja = j8 - j2;
				jb = j8 - j3;
				jc = j8 - j4;
				qc = (0.001089 * j1 - 0.589833) * vj;
				qc = qc + (0.004658 * j1 - 0.056094) * uu - 0.024286 * uv;
				qc = Math.toRadians(qc);

				vk = 0.024039 * vj - 0.025303 * uu + 0.006206 * uv;
				vk -= 0.005992 * uw;
				qe = qc - (Math.toRadians(vk) / planet.Value4);

				qd = 4389.0 * vj + 1129.0 * uv + 4262.0 * uu + 1089.0 * uw;
				qd *= 0.0000001;

				qf = 8189.0 * uu - 817.0 * vj + 781.0 * uw;
				qf *= 0.000001;

				double vd = Math.sin(2.0 * jc);
				double ve = Math.cos(2.0 * jc);
				double vf = Math.sin(j8);
				double vg = Math.cos(j8);
				qa = -0.009556 * Math.sin(ja) - 0.005178 * Math.sin(jb);
				qa = qa + 0.002572 * vd - 0.002972 * ve * vf - 0.002833 * vd * vg;

				qg = 0.000336 * ve * vf + 0.000364 * vd * vg;
				qg = Math.toRadians(qg);

				qb = -40596.0 + 4992.0 * Math.cos(ja) + 2744.0 * Math.cos(jb);
				qb = qb + 2044.0 * Math.cos(jc) + 1051.0 * ve;
				qb *= 0.000001;

				return new PlanetLongL4945(qa, qb, qc, qd, qe, qf, qg);
			}

			ja = j4 - j2;
			jb = j4 - j3;
			jc = j8 - j4;
			qc = (0.864319 - 0.001583 * j1) * vj;
			qc = qc + (0.082222 - 0.006833 * j1) * uu + 0.036017 * uv;
			qc = qc - 0.003019 * uw + 0.008122 * Math.sin(j6);
			qc = Math.toRadians(qc);

			vk = 0.120303 * vj + 0.006197 * uv;
			vk += (0.019472 - 0.000947 * j1) * uu;
			qe = qc - (Math.toRadians(vk) / planet.Value4);

			qd = (163.0 * j1 - 3349.0) * vj + 20981.0 * uu + 1311.0 * uw;
			qd *= 0.0000001;

			qf = -0.003825 * uu;

			qa = (-0.038581 + (0.002031 - 0.00191 * j1) * j1) * Math.cos(j4 + jb);
			qa += (0.010122 - 0.000988 * j1) * Math.sin(j4 + jb);
			double a = (0.034964 - (0.001038 - 0.000868 * j1) * j1) * Math.cos(2.0 * j4 + jb);
			qa = a + qa + 0.005594 * Math.sin(j4 + 3.0 * jc) - 0.014808 * Math.sin(ja);
			qa = qa - 0.005794 * Math.sin(jb) + 0.002347 * Math.cos(jb);
			qa = qa + 0.009872 * Math.sin(jc) + 0.008803 * Math.sin(2.0 * jc);
			qa -= 0.004308 * Math.sin(3.0 * jc);

			double ux = Math.sin(jb);
			double uy = Math.cos(jb);
			double uz = Math.sin(j4);
			double va = Math.cos(j4);
			double vb = Math.sin(2.0 * j4);
			double vc = Math.cos(2.0 * j4);
			qg = (0.000458 * ux - 0.000642 * uy - 0.000517 * Math.cos(4.0 * jc)) * uz;
			qg -= (0.000347 * ux + 0.000853 * uy + 0.000517 * Math.sin(4.0 * jb)) * va;
			qg += 0.000403 * (Math.cos(2.0 * jc) * vb + Math.sin(2.0 * jc) * vc);
			qg = Math.toRadians(qg);

			qb = -25948.0 + 4985.0 * Math.cos(ja) - 1230.0 * va + 3354.0 * uy;
			qb = qb + 904.0 * Math.cos(2.0 * jc) + 894.0 * (Math.cos(jc) - Math.cos(3.0 * jc));
			qb += (5795.0 * va - 1165.0 * uz + 1388.0 * vc) * ux;
			qb += (1351.0 * va + 5702.0 * uz + 1388.0 * vb) * uy;
			qb *= 0.000001;

			return new PlanetLongL4945(qa, qb, qc, qd, qe, qf, qg);
		}

		return new PlanetLongL4945(qa, qb, qc, qd, qe, qf, qg);
	}

	/**
	 * For W, in radians, return S, also in radians.
	 * 
	 * Original macro name: SolveCubic
	 */
	public static double solveCubic(double w) {
		double s = w / 3.0;

		while (true) {
			double s2 = s * s;
			double d = (s2 + 3.0) * s - w;

			if (Math.abs(d) < 0.000001) {
				return s;
			}

			s = ((2.0 * s * s2) + w) / (3.0 * (s2 + 1.0));
		}
	}

	/**
	 * Calculate longitude, latitude, and distance of parabolic-orbit comet.
	 *
	 * Original macro names: PcometLong, PcometLat, PcometDist
	 */
	public static PCometLongLatDist pCometLongLatDist(double lh, double lm, double ls, int ds, int zc, double dy,
			int mn, int yr, double td, int tm, int ty, double q, double i, double p, double n) {
		double gd = localCivilTimeGreenwichDay(lh, lm, ls, ds, zc, dy, mn, yr);
		int gm = localCivilTimeGreenwichMonth(lh, lm, ls, ds, zc, dy, mn, yr);
		int gy = localCivilTimeGreenwichYear(lh, lm, ls, ds, zc, dy, mn, yr);
		double ut = localCivilTimeToUniversalTime(lh, lm, ls, ds, zc, dy, mn, yr);
		double tpe = (ut / 365.242191) + civilDateToJulianDate(gd, gm, gy) - civilDateToJulianDate(td, tm, ty);
		double lg = Math.toRadians(sunLong(lh, lm, ls, ds, zc, dy, mn, yr) + 180.0);
		double re = sunDist(lh, lm, ls, ds, zc, dy, mn, yr);

		double rh2 = 0.0;
		double rd = 0.0;
		double s3 = 0.0;
		double c3 = 0.0;
		double lc = 0.0;
		double s2 = 0.0;
		double c2 = 0.0;

		for (int k = 1; k < 3; k++) {
			double s = solveCubic(0.0364911624 * tpe / (q * Math.sqrt(q)));
			double nu = 2.0 * Math.atan(s);
			double r = q * (1.0 + s * s);
			double l = nu + Math.toRadians(p);
			double s1 = Math.sin(l);
			double c1 = Math.cos(l);
			double i1 = Math.toRadians(i);
			s2 = s1 * Math.sin(i1);
			double ps = Math.asin(s2);
			double y = s1 * Math.cos(i1);
			lc = Math.atan2(y, c1) + Math.toRadians(n);
			c2 = Math.cos(ps);
			rd = r * c2;
			double ll = lc - lg;
			c3 = Math.cos(ll);
			s3 = Math.sin(ll);
			@SuppressWarnings("unused")
			double rh = Math.sqrt((re * re) + (r * r) - (2.0 * re * rd * c3 * Math.cos(ps)));
			if (k == 1) {
				rh2 = Math.sqrt(
						(re * re) + (r * r) - (2.0 * re * r * Math.cos(ps) * Math.cos(l + Math.toRadians(n) - lg)));
			}
		}

		double ep;

		ep = (rd < re)
				? Math.atan(-rd * s3 / (re - (rd * c3))) + lg + 3.141592654
				: Math.atan(re * s3 / (rd - (re * c3))) + lc;
		ep = unwind(ep);

		double tb = rd * s2 * Math.sin(ep - lc) / (c2 * re * s3);
		double bp = Math.atan(tb);

		double cometLongDeg = wToDegrees(ep);
		double cometLatDeg = wToDegrees(bp);
		double cometDistAU = rh2;

		return new PCometLongLatDist(cometLongDeg, cometLatDeg, cometDistAU);
	}

	/**
	 * Calculate longitude, latitude, and horizontal parallax of the Moon.
	 * 
	 * Original macro names: MoonLong, MoonLat, MoonHP
	 */
	public static MoonLongLatHP moonLongLatHP(double lh, double lm, double ls, int ds, int zc, double dy, int mn,
			int yr) {
		double ut = localCivilTimeToUniversalTime(lh, lm, ls, ds, zc, dy, mn, yr);
		double gd = localCivilTimeGreenwichDay(lh, lm, ls, ds, zc, dy, mn, yr);
		int gm = localCivilTimeGreenwichMonth(lh, lm, ls, ds, zc, dy, mn, yr);
		int gy = localCivilTimeGreenwichYear(lh, lm, ls, ds, zc, dy, mn, yr);
		double t = ((civilDateToJulianDate(gd, gm, gy) - 2415020.0) / 36525.0) + (ut / 876600.0);
		double t2 = t * t;

		double m1 = 27.32158213;
		double m2 = 365.2596407;
		double m3 = 27.55455094;
		double m4 = 29.53058868;
		double m5 = 27.21222039;
		double m6 = 6798.363307;
		double q = civilDateToJulianDate(gd, gm, gy) - 2415020.0 + (ut / 24.0);
		m1 = q / m1;
		m2 = q / m2;
		m3 = q / m3;
		m4 = q / m4;
		m5 = q / m5;
		m6 = q / m6;
		m1 = 360.0 * (m1 - Math.floor(m1));
		m2 = 360.0 * (m2 - Math.floor(m2));
		m3 = 360.0 * (m3 - Math.floor(m3));
		m4 = 360.0 * (m4 - Math.floor(m4));
		m5 = 360.0 * (m5 - Math.floor(m5));
		m6 = 360.0 * (m6 - Math.floor(m6));

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
		na = Math.toRadians(na);
		me1 = Math.toRadians(me1);
		mf = Math.toRadians(mf);
		md = Math.toRadians(md);

		// Longitude-specific
		double l = 6.28875 * Math.sin(md) + 1.274018 * Math.sin(2.0 * me1 - md);
		l = l + 0.658309 * Math.sin(2.0 * me1) + 0.213616 * Math.sin(2.0 * md);
		l = l - e * 0.185596 * Math.sin(ms) - 0.114336 * Math.sin(2.0 * mf);
		l += 0.058793 * Math.sin(2.0 * (me1 - md));
		l = l + 0.057212 * e * Math.sin(2.0 * me1 - ms - md) + 0.05332 * Math.sin(2.0 * me1 + md);
		l = l + 0.045874 * e * Math.sin(2.0 * me1 - ms) + 0.041024 * e * Math.sin(md - ms);
		l = l - 0.034718 * Math.sin(me1) - e * 0.030465 * Math.sin(ms + md);
		l = l + 0.015326 * Math.sin(2.0 * (me1 - mf)) - 0.012528 * Math.sin(2.0 * mf + md);
		l = l - 0.01098 * Math.sin(2.0 * mf - md) + 0.010674 * Math.sin(4.0 * me1 - md);
		l = l + 0.010034 * Math.sin(3.0 * md) + 0.008548 * Math.sin(4.0 * me1 - 2.0 * md);
		l = l - e * 0.00791 * Math.sin(ms - md + 2.0 * me1) - e * 0.006783 * Math.sin(2.0 * me1 + ms);
		l = l + 0.005162 * Math.sin(md - me1) + e * 0.005 * Math.sin(ms + me1);
		l = l + 0.003862 * Math.sin(4.0 * me1) + e * 0.004049 * Math.sin(md - ms + 2.0 * me1);
		l = l + 0.003996 * Math.sin(2.0 * (md + me1)) + 0.003665 * Math.sin(2.0 * me1 - 3.0 * md);
		l = l + e * 0.002695 * Math.sin(2.0 * md - ms) + 0.002602 * Math.sin(md - 2.0 * (mf + me1));
		l = l + e * 0.002396 * Math.sin(2.0 * (me1 - md) - ms) - 0.002349 * Math.sin(md + me1);
		l = l + e2 * 0.002249 * Math.sin(2.0 * (me1 - ms)) - e * 0.002125 * Math.sin(2.0 * md + ms);
		l = l - e2 * 0.002079 * Math.sin(2.0 * ms) + e2 * 0.002059 * Math.sin(2.0 * (me1 - ms) - md);
		l = l - 0.001773 * Math.sin(md + 2.0 * (me1 - mf)) - 0.001595 * Math.sin(2.0 * (mf + me1));
		l = l + e * 0.00122 * Math.sin(4.0 * me1 - ms - md) - 0.00111 * Math.sin(2.0 * (md + mf));
		l = l + 0.000892 * Math.sin(md - 3.0 * me1) - e * 0.000811 * Math.sin(ms + md + 2.0 * me1);
		l += e * 0.000761 * Math.sin(4.0 * me1 - ms - 2.0 * md);
		l += e2 * 0.000704 * Math.sin(md - 2.0 * (ms + me1));
		l += e * 0.000693 * Math.sin(ms - 2.0 * (md - me1));
		l += e * 0.000598 * Math.sin(2.0 * (me1 - mf) - ms);
		l = l + 0.00055 * Math.sin(md + 4.0 * me1) + 0.000538 * Math.sin(4.0 * md);
		l = l + e * 0.000521 * Math.sin(4.0 * me1 - ms) + 0.000486 * Math.sin(2.0 * md - me1);
		l += e2 * 0.000717 * Math.sin(md - 2.0 * ms);
		double mm = unwind(ml + Math.toRadians(l));

		// Latitude-specific
		double g = 5.128189 * Math.sin(mf) + 0.280606 * Math.sin(md + mf);
		g = g + 0.277693 * Math.sin(md - mf) + 0.173238 * Math.sin(2.0 * me1 - mf);
		g = g + 0.055413 * Math.sin(2.0 * me1 + mf - md) + 0.046272 * Math.sin(2.0 * me1 - mf - md);
		g = g + 0.032573 * Math.sin(2.0 * me1 + mf) + 0.017198 * Math.sin(2.0 * md + mf);
		g = g + 0.009267 * Math.sin(2.0 * me1 + md - mf) + 0.008823 * Math.sin(2.0 * md - mf);
		g = g + e * 0.008247 * Math.sin(2.0 * me1 - ms - mf) + 0.004323 * Math.sin(2.0 * (me1 - md) - mf);
		g = g + 0.0042 * Math.sin(2.0 * me1 + mf + md) + e * 0.003372 * Math.sin(mf - ms - 2.0 * me1);
		g += e * 0.002472 * Math.sin(2.0 * me1 + mf - ms - md);
		g += e * 0.002222 * Math.sin(2.0 * me1 + mf - ms);
		g += e * 0.002072 * Math.sin(2.0 * me1 - mf - ms - md);
		g = g + e * 0.001877 * Math.sin(mf - ms + md) + 0.001828 * Math.sin(4.0 * me1 - mf - md);
		g = g - e * 0.001803 * Math.sin(mf + ms) - 0.00175 * Math.sin(3.0 * mf);
		g = g + e * 0.00157 * Math.sin(md - ms - mf) - 0.001487 * Math.sin(mf + me1);
		g = g - e * 0.001481 * Math.sin(mf + ms + md) + e * 0.001417 * Math.sin(mf - ms - md);
		g = g + e * 0.00135 * Math.sin(mf - ms) + 0.00133 * Math.sin(mf - me1);
		g = g + 0.001106 * Math.sin(mf + 3.0 * md) + 0.00102 * Math.sin(4.0 * me1 - mf);
		g = g + 0.000833 * Math.sin(mf + 4.0 * me1 - md) + 0.000781 * Math.sin(md - 3.0 * mf);
		g = g + 0.00067 * Math.sin(mf + 4.0 * me1 - 2.0 * md) + 0.000606 * Math.sin(2.0 * me1 - 3.0 * mf);
		g += 0.000597 * Math.sin(2.0 * (me1 + md) - mf);
		g = g + e * 0.000492 * Math.sin(2.0 * me1 + md - ms - mf) + 0.00045 * Math.sin(2.0 * (md - me1) - mf);
		g = g + 0.000439 * Math.sin(3.0 * md - mf) + 0.000423 * Math.sin(mf + 2.0 * (me1 + md));
		g = g + 0.000422 * Math.sin(2.0 * me1 - mf - 3.0 * md) - e * 0.000367 * Math.sin(ms + mf + 2.0 * me1 - md);
		g = g - e * 0.000353 * Math.sin(ms + mf + 2.0 * me1) + 0.000331 * Math.sin(mf + 4.0 * me1);
		g += e * 0.000317 * Math.sin(2.0 * me1 + mf - ms + md);
		g = g + e2 * 0.000306 * Math.sin(2.0 * (me1 - ms) - mf) - 0.000283 * Math.sin(md + 3.0 * mf);
		double w1 = 0.0004664 * Math.cos(na);
		double w2 = 0.0000754 * Math.cos(c);
		double bm = Math.toRadians(g) * (1.0 - w1 - w2);

		// Horizontal parallax-specific
		double pm = 0.950724 + 0.051818 * Math.cos(md) + 0.009531 * Math.cos(2.0 * me1 - md);
		pm = pm + 0.007843 * Math.cos(2.0 * me1) + 0.002824 * Math.cos(2.0 * md);
		pm = pm + 0.000857 * Math.cos(2.0 * me1 + md) + e * 0.000533 * Math.cos(2.0 * me1 - ms);
		pm += e * 0.000401 * Math.cos(2.0 * me1 - md - ms);
		pm = pm + e * 0.00032 * Math.cos(md - ms) - 0.000271 * Math.cos(me1);
		pm = pm - e * 0.000264 * Math.cos(ms + md) - 0.000198 * Math.cos(2.0 * mf - md);
		pm = pm + 0.000173 * Math.cos(3.0 * md) + 0.000167 * Math.cos(4.0 * me1 - md);
		pm = pm - e * 0.000111 * Math.cos(ms) + 0.000103 * Math.cos(4.0 * me1 - 2.0 * md);
		pm = pm - 0.000084 * Math.cos(2.0 * md - 2.0 * me1) - e * 0.000083 * Math.cos(2.0 * me1 + ms);
		pm = pm + 0.000079 * Math.cos(2.0 * me1 + 2.0 * md) + 0.000072 * Math.cos(4.0 * me1);
		pm = pm + e * 0.000064 * Math.cos(2.0 * me1 - ms + md) - e * 0.000063 * Math.cos(2.0 * me1 + ms - md);
		pm = pm + e * 0.000041 * Math.cos(ms + me1) + e * 0.000035 * Math.cos(2.0 * md - ms);
		pm = pm - 0.000033 * Math.cos(3.0 * md - 2.0 * me1) - 0.00003 * Math.cos(md + me1);
		pm = pm - 0.000029 * Math.cos(2.0 * (mf - me1)) - e * 0.000029 * Math.cos(2.0 * md + ms);
		pm = pm + e2 * 0.000026 * Math.cos(2.0 * (me1 - ms)) - 0.000023 * Math.cos(2.0 * (mf - me1) + md);
		pm += e * 0.000019 * Math.cos(4.0 * me1 - ms - md);

		double moonLongDeg = wToDegrees(mm);
		double moonLatDeg = wToDegrees(bm);
		double moonHorPara = pm;

		return new MoonLongLatHP(moonLongDeg, moonLatDeg, moonHorPara);
	}

	/**
	 * Calculate current phase of Moon.
	 * 
	 * Original macro name: MoonPhase
	 */
	public static double moonPhase(double lh, double lm, double ls, int ds, int zc, double dy, int mn, int yr) {
		MoonLongLatHP moonResult = moonLongLatHP(lh, lm, ls, ds, zc, dy, mn, yr);

		double cd = Math.cos(Math.toRadians((moonResult.moonLongDeg - sunLong(lh, lm, ls, ds, zc, dy, mn, yr))))
				* Math.cos(Math.toRadians(moonResult.moonLatDeg));

		double d = Math.acos(cd);
		double sd = Math.sin(d);
		double i = 0.1468 * sd * (1.0 - 0.0549 * Math.sin(moonMeanAnomaly(lh, lm, ls, ds, zc, dy, mn, yr)));
		i /= (1.0 - 0.0167 * Math.sin(sunMeanAnomaly(lh, lm, ls, ds, zc, dy, mn, yr)));
		i = 3.141592654 - d - Math.toRadians(i);
		double k = (1.0 + Math.cos(i)) / 2.0;

		return PAUtil.round(k, 2);
	}

	/**
	 * Calculate the Moon's mean anomaly.
	 * 
	 * Original macro name: MoonMeanAnomaly
	 */
	public static double moonMeanAnomaly(double lh, double lm, double ls, int ds, int zc, double dy, int mn, int yr) {
		double ut = localCivilTimeToUniversalTime(lh, lm, ls, ds, zc, dy, mn, yr);
		double gd = localCivilTimeGreenwichDay(lh, lm, ls, ds, zc, dy, mn, yr);
		int gm = localCivilTimeGreenwichMonth(lh, lm, ls, ds, zc, dy, mn, yr);
		int gy = localCivilTimeGreenwichYear(lh, lm, ls, ds, zc, dy, mn, yr);
		double t = ((civilDateToJulianDate(gd, gm, gy) - 2415020.0) / 36525.0) + (ut / 876600.0);
		double t2 = t * t;

		double m1 = 27.32158213;
		double m2 = 365.2596407;
		double m3 = 27.55455094;
		double m4 = 29.53058868;
		double m5 = 27.21222039;
		double m6 = 6798.363307;
		double q = civilDateToJulianDate(gd, gm, gy) - 2415020.0 + (ut / 24.0);
		m1 = q / m1;
		m2 = q / m2;
		m3 = q / m3;
		m4 = q / m4;
		m5 = q / m5;
		m6 = q / m6;
		m1 = 360.0 * (m1 - Math.floor(m1));
		m2 = 360.0 * (m2 - Math.floor(m2));
		m3 = 360.0 * (m3 - Math.floor(m3));
		m4 = 360.0 * (m4 - Math.floor(m4));
		m5 = 360.0 * (m5 - Math.floor(m5));
		m6 = 360.0 * (m6 - Math.floor(m6));

		@SuppressWarnings("unused")
		double ml = 270.434164 + m1 - (0.001133 - 0.0000019 * t) * t2;
		@SuppressWarnings("unused")
		double ms = 358.475833 + m2 - (0.00015 + 0.0000033 * t) * t2;
		double md = 296.104608 + m3 + (0.009192 + 0.0000144 * t) * t2;
		double na = 259.183275 - m6 + (0.002078 + 0.0000022 * t) * t2;
		double a = Math.toRadians(51.2 + 20.2 * t);
		double s1 = Math.sin(a);
		double s2 = Math.sin(Math.toRadians(na));
		double b = 346.56 + (132.87 - 0.0091731 * t) * t;
		double s3 = 0.003964 * Math.sin(Math.toRadians(b));
		@SuppressWarnings("unused")
		double c = Math.toRadians(na + 275.05 - 2.3 * t);
		md = md + 0.000817 * s1 + s3 + 0.002541 * s2;

		return Math.toRadians(md);
	}

	/**
	 * Calculate Julian date of New Moon.
	 * 
	 * Original macro name: NewMoon
	 */
	public static double newMoon(int ds, int zc, double dy, int mn, int yr) {
		double d0 = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int m0 = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int y0 = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);

		double j0 = civilDateToJulianDate(0.0, 1, y0) - 2415020.0;
		double dj = civilDateToJulianDate(d0, m0, y0) - 2415020.0;
		double k = lint(((y0 - 1900.0 + ((dj - j0) / 365.0)) * 12.3685) + 0.5);
		double tn = k / 1236.85;
		double tf = (k + 0.5) / 1236.85;
		double t = tn;
		NewMoonFullMoonL6855 nmfmResult1 = newMoonFullMoonL6855(k, t);
		double ni = nmfmResult1.a;
		double nf = nmfmResult1.b;
		t = tf;
		k += 0.5;
		@SuppressWarnings("unused")
		NewMoonFullMoonL6855 nmfmResult2 = newMoonFullMoonL6855(k, t);

		return ni + 2415020.0 + nf;
	}

	/**
	 * Calculate Julian date of Full Moon.
	 * 
	 * Original macro name: FullMoon
	 */
	public static double fullMoon(int ds, int zc, double dy, int mn, int yr) {
		double d0 = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int m0 = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int y0 = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);

		double j0 = civilDateToJulianDate(0.0, 1, y0) - 2415020.0;
		double dj = civilDateToJulianDate(d0, m0, y0) - 2415020.0;
		double k = lint(((y0 - 1900.0 + ((dj - j0) / 365.0)) * 12.3685) + 0.5);
		double tn = k / 1236.85;
		double tf = (k + 0.5) / 1236.85;
		double t = tn;
		@SuppressWarnings("unused")
		NewMoonFullMoonL6855 nmfnResult1 = newMoonFullMoonL6855(k, t);
		t = tf;
		k += 0.5;
		NewMoonFullMoonL6855 nmfnResult2 = newMoonFullMoonL6855(k, t);
		double fi = nmfnResult2.a;
		double ff = nmfnResult2.b;

		return fi + 2415020.0 + ff;
	}

	/** Helper function for newMoon() and fullMoon() */
	public static NewMoonFullMoonL6855 newMoonFullMoonL6855(double k, double t) {
		double t2 = t * t;
		double e = 29.53 * k;
		double c = 166.56 + (132.87 - 0.009173 * t) * t;
		c = Math.toRadians(c);
		double b = 0.00058868 * k + (0.0001178 - 0.000000155 * t) * t2;
		b = b + 0.00033 * Math.sin(c) + 0.75933;
		double a = k / 12.36886;
		double a1 = 359.2242 + 360.0 * fract(a) - (0.0000333 + 0.00000347 * t) * t2;
		double a2 = 306.0253 + 360.0 * fract(k / 0.9330851);
		a2 += (0.0107306 + 0.00001236 * t) * t2;
		a = k / 0.9214926;
		double f = 21.2964 + 360.0 * fract(a) - (0.0016528 + 0.00000239 * t) * t2;
		a1 = unwindDeg(a1);
		a2 = unwindDeg(a2);
		f = unwindDeg(f);
		a1 = Math.toRadians(a1);
		a2 = Math.toRadians(a2);
		f = Math.toRadians(f);

		double dd = (0.1734 - 0.000393 * t) * Math.sin(a1) + 0.0021 * Math.sin(2.0 * a1);
		dd = dd - 0.4068 * Math.sin(a2) + 0.0161 * Math.sin(2.0 * a2) - 0.0004 * Math.sin(3.0 * a2);
		dd = dd + 0.0104 * Math.sin(2.0 * f) - 0.0051 * Math.sin(a1 + a2);
		dd = dd - 0.0074 * Math.sin(a1 - a2) + 0.0004 * Math.sin(2.0 * f + a1);
		dd = dd - 0.0004 * Math.sin(2.0 * f - a1) - 0.0006 * Math.sin(2.0 * f + a2) + 0.001 * Math.sin(2.0 * f - a2);
		dd += 0.0005 * Math.sin(a1 + 2.0 * a2);
		double e1 = Math.floor(e);
		b = b + dd + (e - e1);
		double b1 = Math.floor(b);
		a = e1 + b1;
		b -= b1;

		return new NewMoonFullMoonL6855(a, b, f);
	}

	/** Original macro name: FRACT */
	public static double fract(double w) {
		return w - lint(w);
	}

	/** Original macro name: LINT */
	public static double lint(double w) {
		return iInt(w) + iInt(((1.0 * sign(w)) - 1.0) / 2.0);
	}

	/** Original macro name: IINT */
	public static double iInt(double w) {
		return sign(w) * Math.floor(Math.abs(w));
	}

	/** Calculate sign of number. */
	public static double sign(double numberToCheck) {
		double signValue = 0.0;

		if (numberToCheck < 0.0)
			signValue = -1.0;

		if (numberToCheck > 0.0)
			signValue = 1.0;

		return signValue;
	}

	/** Original macro name: UTDayAdjust */
	public static double utDayAdjust(double ut, double g1) {
		double returnValue = ut;

		if ((ut - g1) < -6.0)
			returnValue = ut + 24.0;

		if ((ut - g1) > 6.0)
			returnValue = ut - 24.0;

		return returnValue;
	}

	/** Original macro name: Fpart */
	public static double fPart(double w) {
		return w - lint(w);
	}

	/**
	 * Local time of moonrise.
	 * 
	 * Original macro name: MoonRiseLCT
	 */
	public static double moonRiseLCT(double dy, int mn, int yr, int ds, int zc, double gLong, double gLat) {
		double gdy = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gmn = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gyr = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		double lct = 12.0;
		double dy1 = dy;
		int mn1 = mn;
		int yr1 = yr;

		MoonL6700 lct6700result1 = moonRiseLCTL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
		double lu = lct6700result1.lu;
		lct = lct6700result1.lct;

		if (lct == -99.0)
			return lct;

		double la = lu;

		double x;
		double ut;
		double g1 = 0.0;
		double gu = 0.0;

		for (int k = 1; k < 9; k++) {
			x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
			ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

			g1 = (k == 1) ? ut : gu;

			gu = ut;
			ut = gu;

			MoonL6680 lct6680result = moonRiseLCTL6680(x, ds, zc, gdy, gmn, gyr, g1, ut);
			lct = lct6680result.lct;
			dy1 = lct6680result.dy1;
			mn1 = lct6680result.mn1;
			yr1 = lct6680result.yr1;
			gdy = lct6680result.gdy;
			gmn = lct6680result.gmn;
			gyr = lct6680result.gyr;

			MoonL6700 lct6700result2 = moonRiseLCTL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
			lu = lct6700result2.lu;
			lct = lct6700result2.lct;

			if (lct == -99.0)
				return lct;

			la = lu;
		}

		x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
		ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);

		return lct;
	}

	/** Helper function for moonRiseLCT */
	public static MoonL6680 moonRiseLCTL6680(double x, int ds, int zc, double gdy, int gmn, int gyr, double g1,
			double ut) {
		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		double lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		double dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		gdy = localCivilTimeGreenwichDay(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gmn = localCivilTimeGreenwichMonth(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gyr = localCivilTimeGreenwichYear(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		ut -= 24.0 * Math.floor(ut / 24.0);

		return new MoonL6680(ut, lct, dy1, mn1, yr1, gdy, gmn, gyr);
	}

	/** Helper function for moonRiseLCT */
	public static MoonL6700 moonRiseLCTL6700(double lct, int ds, int zc, double dy1, int mn1, int yr1,
			double gdy, int gmn, int gyr, double gLat) {
		double mm = moonLong(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double bm = moonLat(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double pm = Math.toRadians(moonHP(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1));
		double dp = nutatLong(gdy, gmn, gyr);
		double th = 0.27249 * Math.sin(pm);
		double di = th + 0.0098902 - pm;
		double p = decimalDegreesToDegreeHours(ecRA(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr));
		double q = ecDec(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr);
		double lu = riseSetLocalSiderealTimeRise(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);

		if (eRS(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat) != RiseSetStatus.OK)
			lct = -99.0;

		return new MoonL6700(mm, bm, pm, dp, th, di, p, q, lu, lct);
	}

	/**
	 * Local date of moonrise.
	 * 
	 * Original macro names: MoonRiseLcDay, MoonRiseLcMonth, MoonRiseLcYear
	 */
	public static MoonLcDMY moonRiseLcDMY(double dy, int mn, int yr, int ds, int zc, double gLong, double gLat) {
		double gdy = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gmn = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gyr = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		double lct = 12.0;
		double dy1 = dy;
		int mn1 = mn;
		int yr1 = yr;

		MoonL6700 lct6700result1 = moonRiseLcDMYL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
		double lu = lct6700result1.lu;
		lct = lct6700result1.lct;

		if (lct == -99.0)
			return new MoonLcDMY(lct, (int) lct, (int) lct);

		double la = lu;

		double x;
		double ut;
		double g1 = 0.0;
		double gu = 0.0;
		for (int k = 1; k < 9; k++) {
			x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
			ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

			g1 = (k == 1) ? ut : gu;

			gu = ut;
			ut = gu;

			MoonL6680 lct6680result1 = moonRiseLcDMYL6680(x, ds, zc, gdy, gmn, gyr, g1, ut);
			lct = lct6680result1.lct;
			dy1 = lct6680result1.dy1;
			mn1 = lct6680result1.mn1;
			yr1 = lct6680result1.yr1;
			gdy = lct6680result1.gdy;
			gmn = lct6680result1.gmn;
			gyr = lct6680result1.gyr;

			MoonL6700 lct6700result2 = moonRiseLcDMYL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);

			lu = lct6700result2.lu;
			lct = lct6700result2.lct;

			if (lct == -99.0)
				return new MoonLcDMY(lct, (int) lct, (int) lct);

			la = lu;
		}

		x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
		ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);

		return new MoonLcDMY(dy1, mn1, yr1);
	}

	/** Helper function for moonRiseLcDMY */
	public static MoonL6680 moonRiseLcDMYL6680(double x, int ds, int zc, double gdy, int gmn, int gyr, double g1,
			double ut) {
		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		double lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		double dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		gdy = localCivilTimeGreenwichDay(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gmn = localCivilTimeGreenwichMonth(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gyr = localCivilTimeGreenwichYear(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		ut -= 24.0 * Math.floor(ut / 24.0);

		return new MoonL6680(ut, lct, dy1, mn1, yr1, gdy, gmn, gyr);
	}

	/** Helper function for moonRiseLcDMY */
	public static MoonL6700 moonRiseLcDMYL6700(double lct, int ds, int zc, double dy1, int mn1, int yr1,
			double gdy, int gmn, int gyr, double gLat) {
		double mm = moonLong(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double bm = moonLat(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double pm = Math.toRadians(moonHP(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1));
		double dp = nutatLong(gdy, gmn, gyr);
		double th = 0.27249 * Math.sin(pm);
		double di = th + 0.0098902 - pm;
		double p = decimalDegreesToDegreeHours(ecRA(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr));
		double q = ecDec(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr);
		double lu = riseSetLocalSiderealTimeRise(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);

		return new MoonL6700(mm, bm, pm, dp, th, di, p, q, lu, lct);
	}

	/**
	 * Local azimuth of moonrise.
	 * 
	 * Original macro name: MoonRiseAz
	 */
	public static double moonRiseAz(double dy, int mn, int yr, int ds, int zc, double gLong, double gLat) {
		double gdy = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gmn = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gyr = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		double lct = 12.0;
		double dy1 = dy;
		int mn1 = mn;
		int yr1 = yr;

		MoonAzL6700 az6700result1 = moonRiseAzL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
		double lu = az6700result1.lu;
		lct = az6700result1.lct;
		double au;

		if (lct == -99.0)
			return lct;

		double la = lu;

		double x;
		double ut;
		double g1;
		double gu = 0.0;
		double aa = 0.0;
		for (int k = 1; k < 9; k++) {
			x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
			ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

			g1 = (k == 1) ? ut : gu;

			gu = ut;
			ut = gu;

			MoonL6680 az6680result1 = moonRiseAzL6680(x, ds, zc, gdy, gmn, gyr, g1, ut);
			lct = az6680result1.lct;
			dy1 = az6680result1.dy1;
			mn1 = az6680result1.mn1;
			yr1 = az6680result1.yr1;
			gdy = az6680result1.gdy;
			gmn = az6680result1.gmn;
			gyr = az6680result1.gyr;

			MoonAzL6700 az6700result2 = moonRiseAzL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
			lu = az6700result2.lu;
			lct = az6700result2.lct;
			au = az6700result2.au;

			if (lct == -99.0)
				return lct;

			la = lu;
			aa = au;
		}

		au = aa;

		return au;
	}

	/** Helper function for moonRiseAz */
	public static MoonL6680 moonRiseAzL6680(double x, int ds, int zc, double gdy, int gmn, int gyr, double g1,
			double ut) {
		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		double lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		double dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		gdy = localCivilTimeGreenwichDay(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gmn = localCivilTimeGreenwichMonth(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gyr = localCivilTimeGreenwichYear(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		ut -= 24.0 * Math.floor(ut / 24.0);

		return new MoonL6680(ut, lct, dy1, mn1, yr1, gdy, gmn, gyr);
	}

	/** Helper function for moonRiseAz */
	public static MoonAzL6700 moonRiseAzL6700(double lct, int ds, int zc, double dy1, int mn1, int yr1, double gdy,
			int gmn, int gyr, double gLat) {
		double mm = moonLong(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double bm = moonLat(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double pm = Math.toRadians(moonHP(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1));
		double dp = nutatLong(gdy, gmn, gyr);
		double th = 0.27249 * Math.sin(pm);
		double di = th + 0.0098902 - pm;
		double p = decimalDegreesToDegreeHours(ecRA(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr));
		double q = ecDec(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr);
		double lu = riseSetLocalSiderealTimeRise(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);
		double au = riseSetAzimuthRise(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);

		return new MoonAzL6700(mm, bm, pm, dp, th, di, p, q, lu, lct, au);
	}

	/**
	 * Local time of moonset.
	 * 
	 * Original macro name: MoonSetLCT
	 */
	public static double moonSetLCT(double dy, int mn, int yr, int ds, int zc, double gLong, double gLat) {
		double gdy = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gmn = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gyr = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		double lct = 12.0;
		double dy1 = dy;
		int mn1 = mn;
		int yr1 = yr;

		MoonL6700 lct6700result1 = moonSetLCTL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
		double lu = lct6700result1.lu;
		lct = lct6700result1.lct;

		if (lct == -99.0)
			return lct;

		double la = lu;

		double x;
		double ut;
		double g1 = 0.0;
		double gu = 0.0;
		for (int k = 1; k < 9; k++) {
			x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
			ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

			g1 = (k == 1) ? ut : gu;

			gu = ut;
			ut = gu;

			MoonL6680 lct6680result1 = moonSetLCTL6680(x, ds, zc, gdy, gmn, gyr, g1, ut);
			lct = lct6680result1.lct;
			dy1 = lct6680result1.dy1;
			mn1 = lct6680result1.mn1;
			yr1 = lct6680result1.yr1;
			gdy = lct6680result1.gdy;
			gmn = lct6680result1.gmn;
			gyr = lct6680result1.gyr;

			MoonL6700 lct6700result2 = moonSetLCTL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
			lu = lct6700result2.lu;
			lct = lct6700result2.lct;

			if (lct == -99.0)
				return lct;

			la = lu;
		}

		x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
		ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);

		return lct;
	}

	/** Helper function for moonSetLCT */
	public static MoonL6680 moonSetLCTL6680(double x, int ds, int zc, double gdy, int gmn, int gyr, double g1,
			double ut) {
		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		double lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		double dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		gdy = localCivilTimeGreenwichDay(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gmn = localCivilTimeGreenwichMonth(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gyr = localCivilTimeGreenwichYear(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		ut -= 24.0 * Math.floor(ut / 24.0);

		return new MoonL6680(ut, lct, dy1, mn1, yr1, gdy, gmn, gyr);
	}

	/** Helper function for moonSetLCT */
	public static MoonL6700 moonSetLCTL6700(double lct, int ds, int zc, double dy1, int mn1, int yr1, double gdy,
			int gmn, int gyr, double gLat) {
		double mm = moonLong(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double bm = moonLat(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double pm = Math.toRadians(moonHP(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1));
		double dp = nutatLong(gdy, gmn, gyr);
		double th = 0.27249 * Math.sin(pm);
		double di = th + 0.0098902 - pm;
		double p = decimalDegreesToDegreeHours(ecRA(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr));
		double q = ecDec(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr);
		double lu = riseSetLocalSiderealTimeSet(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);

		if (eRS(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat) != RiseSetStatus.OK)
			lct = -99.0;

		return new MoonL6700(mm, bm, pm, dp, th, di, p, q, lu, lct);
	}

	/**
	 * Local date of moonset.
	 * 
	 * Original macro names: MoonSetLcDay, MoonSetLcMonth, MoonSetLcYear
	 */
	public static MoonLcDMY moonSetLcDMY(double dy, int mn, int yr, int ds, int zc, double gLong, double gLat) {
		double gdy = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gmn = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gyr = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		double lct = 12.0;
		double dy1 = dy;
		int mn1 = mn;
		int yr1 = yr;

		MoonL6700 dmy6700result1 = moonSetLcDMYL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
		double lu = dmy6700result1.lu;
		lct = dmy6700result1.lct;

		if (lct == -99.0)
			return new MoonLcDMY(lct, (int) lct, (int) lct);

		double la = lu;

		double x;
		double ut;
		double g1 = 0.0;
		double gu = 0.0;
		for (int k = 1; k < 9; k++) {
			x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
			ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

			g1 = (k == 1) ? ut : gu;

			gu = ut;
			ut = gu;

			MoonL6680 dmy6680result1 = moonSetLcDMYL6680(x, ds, zc, gdy, gmn, gyr, g1, ut);
			lct = dmy6680result1.lct;
			dy1 = dmy6680result1.dy1;
			mn1 = dmy6680result1.mn1;
			yr1 = dmy6680result1.yr1;
			gdy = dmy6680result1.gdy;
			gmn = dmy6680result1.gmn;
			gyr = dmy6680result1.gyr;

			MoonL6700 dmy6700result2 = moonSetLcDMYL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
			lu = dmy6700result2.lu;
			lct = dmy6700result2.lct;

			if (lct == -99.0)
				return new MoonLcDMY(lct, (int) lct, (int) lct);

			la = lu;
		}

		x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
		ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);

		return new MoonLcDMY(dy1, mn1, yr1);
	}

	/** Helper function for moonSetLcDMY */
	public static MoonL6680 moonSetLcDMYL6680(double x, int ds, int zc, double gdy, int gmn, int gyr, double g1,
			double ut) {
		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		double lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		double dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		gdy = localCivilTimeGreenwichDay(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gmn = localCivilTimeGreenwichMonth(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gyr = localCivilTimeGreenwichYear(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		ut -= 24.0 * Math.floor(ut / 24.0);

		return new MoonL6680(ut, lct, dy1, mn1, yr1, gdy, gmn, gyr);
	}

	/** Helper function for moonSetLcDMY */
	public static MoonL6700 moonSetLcDMYL6700(double lct, int ds, int zc, double dy1, int mn1, int yr1, double gdy,
			int gmn, int gyr, double gLat) {
		double mm = moonLong(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double bm = moonLat(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double pm = Math.toRadians(moonHP(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1));
		double dp = nutatLong(gdy, gmn, gyr);
		double th = 0.27249 * Math.sin(pm);
		double di = th + 0.0098902 - pm;
		double p = decimalDegreesToDegreeHours(ecRA(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr));
		double q = ecDec(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr);
		double lu = riseSetLocalSiderealTimeSet(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);

		return new MoonL6700(mm, bm, pm, dp, th, di, p, q, lu, lct);
	}

	/**
	 * Local azimuth of moonset.
	 * 
	 * Original macro name: MoonSetAz
	 */
	public static double moonSetAz(double dy, int mn, int yr, int ds, int zc, double gLong, double gLat) {
		double gdy = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gmn = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int gyr = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		double lct = 12.0;
		double dy1 = dy;
		int mn1 = mn;
		int yr1 = yr;

		MoonAzL6700 az6700result1 = moonSetAzL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
		double lu = az6700result1.lu;
		lct = az6700result1.lct;

		double au;

		if (lct == -99.0)
			return lct;

		double la = lu;

		double x;
		double ut;
		double g1;
		double gu = 0.0;
		double aa = 0.0;
		for (int k = 1; k < 9; k++) {
			x = localSiderealTimeToGreenwichSiderealTime(la, 0.0, 0.0, gLong);
			ut = greenwichSiderealTimeToUniversalTime(x, 0.0, 0.0, gdy, gmn, gyr);

			g1 = (k == 1) ? ut : gu;

			gu = ut;
			ut = gu;

			MoonL6680 az6680result1 = moonSetAzL6680(x, ds, zc, gdy, gmn, gyr, g1, ut);
			lct = az6680result1.lct;
			dy1 = az6680result1.dy1;
			mn1 = az6680result1.mn1;
			yr1 = az6680result1.yr1;
			gdy = az6680result1.gdy;
			gmn = az6680result1.gmn;
			gyr = az6680result1.gyr;

			MoonAzL6700 az6700result2 = moonSetAzL6700(lct, ds, zc, dy1, mn1, yr1, gdy, gmn, gyr, gLat);
			lu = az6700result2.lu;
			lct = az6700result2.lct;
			au = az6700result2.au;

			if (lct == -99.0)
				return lct;

			la = lu;
			aa = au;
		}

		au = aa;

		return au;
	}

	/** Helper function for moonSetAz */
	public static MoonL6680 moonSetAzL6680(double x, int ds, int zc, double gdy, int gmn, int gyr, double g1,
			double ut) {
		if (eGstUt(x, 0.0, 0.0, gdy, gmn, gyr) != WarningFlag.OK)
			if (Math.abs(g1 - ut) > 0.5)
				ut += 23.93447;

		ut = utDayAdjust(ut, g1);
		double lct = universalTimeToLocalCivilTime(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		double dy1 = universalTimeLocalCivilDay(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int mn1 = universalTimeLocalCivilMonth(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		int yr1 = universalTimeLocalCivilYear(ut, 0.0, 0.0, ds, zc, gdy, gmn, gyr);
		gdy = localCivilTimeGreenwichDay(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gmn = localCivilTimeGreenwichMonth(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		gyr = localCivilTimeGreenwichYear(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		ut -= 24.0 * Math.floor(ut / 24.0);

		return new MoonL6680(ut, lct, dy1, mn1, yr1, gdy, gmn, gyr);
	}

	/** Helper function for moonSetAz */
	public static MoonAzL6700 moonSetAzL6700(double lct, int ds, int zc, double dy1, int mn1, int yr1, double gdy,
			int gmn, int gyr, double gLat) {
		double mm = moonLong(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double bm = moonLat(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1);
		double pm = Math.toRadians(moonHP(lct, 0.0, 0.0, ds, zc, dy1, mn1, yr1));
		double dp = nutatLong(gdy, gmn, gyr);
		double th = 0.27249 * Math.sin(pm);
		double di = th + 0.0098902 - pm;
		double p = decimalDegreesToDegreeHours(ecRA(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr));
		double q = ecDec(mm + dp, 0.0, 0.0, bm, 0.0, 0.0, gdy, gmn, gyr);
		double lu = riseSetLocalSiderealTimeSet(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);
		double au = riseSetAzimuthSet(p, 0.0, 0.0, q, 0.0, 0.0, wToDegrees(di), gLat);

		return new MoonAzL6700(mm, bm, pm, dp, th, di, p, q, lu, lct, au);
	}

	/**
	 * Determine if a lunar eclipse is likely to occur.
	 * 
	 * Original macro name: LEOccurrence
	 */
	public static LunarEclipseOccurrence lunarEclipseOccurrence(int ds, int zc, double dy, int mn, int yr) {
		double d0 = localCivilTimeGreenwichDay(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int m0 = localCivilTimeGreenwichMonth(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);
		int y0 = localCivilTimeGreenwichYear(12.0, 0.0, 0.0, ds, zc, dy, mn, yr);

		double j0 = civilDateToJulianDate(0.0, 1, y0);
		double dj = civilDateToJulianDate(d0, m0, y0);
		double k = (y0 - 1900.0 + ((dj - j0) * 1.0 / 365.0)) * 12.3685;
		k = lint(k + 0.5);
		double tn = k / 1236.85;
		double tf = (k + 0.5) / 1236.85;
		double t = tn;
		@SuppressWarnings("unused")
		LunarEclipseOccurrenceL6855 l6855result1 = lunarEclipseOccurrenceL6855(t, k);
		t = tf;
		k += 0.5;
		LunarEclipseOccurrenceL6855 l6855result2 = lunarEclipseOccurrenceL6855(t, k);
		double fb = l6855result2.f;

		double df = Math.abs(fb - 3.141592654 * lint(fb / 3.141592654));

		if (df > 0.37)
			df = 3.141592654 - df;

		LunarEclipseOccurrence s = LunarEclipseOccurrence.LUNAR_ECLIPSE_CERTAIN;
		if (df >= 0.242600766) {
			s = LunarEclipseOccurrence.LUNAR_ECLIPSE_POSSIBLE;

			if (df > 0.37)
				s = LunarEclipseOccurrence.NO_LUNAR_ECLIPSE;
		}

		return s;
	}

	/** Helper function for lunarEclipseOccurrence */
	public static LunarEclipseOccurrenceL6855 lunarEclipseOccurrenceL6855(double t, double k) {
		double t2 = t * t;
		double e = 29.53 * k;
		double c = 166.56 + (132.87 - 0.009173 * t) * t;
		c = Math.toRadians(c);
		double b = 0.00058868 * k + (0.0001178 - 0.000000155 * t) * t2;
		b = b + 0.00033 * Math.sin(c) + 0.75933;
		double a = k / 12.36886;
		double a1 = 359.2242 + 360.0 * fPart(a) - (0.0000333 + 0.00000347 * t) * t2;
		double a2 = 306.0253 + 360.0 * fPart(k / 0.9330851);
		a2 += (0.0107306 + 0.00001236 * t) * t2;
		a = k / 0.9214926;
		double f = 21.2964 + 360.0 * fPart(a) - (0.0016528 + 0.00000239 * t) * t2;
		a1 = unwindDeg(a1);
		a2 = unwindDeg(a2);
		f = unwindDeg(f);
		a1 = Math.toRadians(a1);
		a2 = Math.toRadians(a2);
		f = Math.toRadians(f);

		double dd = (0.1734 - 0.000393 * t) * Math.sin(a1) + 0.0021 * Math.sin(2.0 * a1);
		dd = dd - 0.4068 * Math.sin(a2) + 0.0161 * Math.sin(2.0 * a2) - 0.0004 * Math.sin(3.0 * a2);
		dd = dd + 0.0104 * Math.sin(2.0 * f) - 0.0051 * Math.sin(a1 + a2);
		dd = dd - 0.0074 * Math.sin(a1 - a2) + 0.0004 * Math.sin(2.0 * f + a1);
		dd = dd - 0.0004 * Math.sin(2.0 * f - a1) - 0.0006 * (2.0 * f + a2) + 0.001 * Math.sin(2.0 * f - a2);
		dd += 0.0005 * Math.sin(a1 + 2.0 * a2);
		double e1 = Math.floor(e);
		b = b + dd + (e - e1);
		double b1 = Math.floor(b);
		a = e1 + b1;
		b -= b1;

		return new LunarEclipseOccurrenceL6855(f, dd, e1, b1, a, b);
	}

	/**
	 * Calculate time of maximum shadow for lunar eclipse (UT)
	 * 
	 * Original macro name: UTMaxLunarEclipse
	 */
	public static double utMaxLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		@SuppressWarnings("unused")
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double rp = (hd + rn + ps) * 1.02;
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		return z1;
	}

	/**
	 * Calculate time of first shadow contact for lunar eclipse (UT)
	 * 
	 * Original macro name: UTFirstContactLunarEclipse
	 */
	public static double utFirstContactLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double rp = (hd + rn + ps) * 1.02;
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		double zd = Math.sqrt(dd);
		double z6 = z1 - zd;

		if (z6 < 0.0)
			z6 += 24.0;

		return z6;
	}

	/**
	 * Calculate time of last shadow contact for lunar eclipse (UT)
	 * 
	 * Original macro name: UTLastContactLunarEclipse
	 */
	public static double utLastContactLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double rp = (hd + rn + ps) * 1.02;
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		double zd = Math.sqrt(dd);
		double z7 = z1 + zd - lint((z1 + zd) / 24.0) * 24.0;

		return z7;
	}

	/**
	 * Calculate start time of umbra phase of lunar eclipse (UT)
	 * 
	 * Original macro name: UTStartUmbraLunarEclipse
	 */
	public static double utStartUmbraLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double ru = (hd - rn + ps) * 1.02;
		double rp = (hd + rn + ps) * 1.02;
		@SuppressWarnings("unused")
		double pj = Math.abs(sh * zh / Math.sqrt(s2 + z2));
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		double zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z6 = z1 - zd;

		r = rm + ru;
		dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		zd = Math.sqrt(dd);
		double z8 = z1 - zd;

		if (z8 < 0.0)
			z8 += 24.0;

		return z8;
	}

	/**
	 * Calculate end time of umbra phase of lunar eclipse (UT)
	 * 
	 * Original macro name: UTEndUmbraLunarEclipse
	 */
	public static double utEndUmbraLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double ru = (hd - rn + ps) * 1.02;
		double rp = (hd + rn + ps) * 1.02;
		@SuppressWarnings("unused")
		double pj = Math.abs(sh * zh / Math.sqrt(s2 + z2));
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		double zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z6 = z1 - zd;

		r = rm + ru;
		dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		zd = Math.sqrt(dd);
		double z9 = z1 + zd - lint((z1 + zd) / 24.0) * 24.0;

		return z9;
	}

	/**
	 * Calculate start time of total phase of lunar eclipse (UT)
	 * 
	 * Original macro name: UTStartTotalLunarEclipse
	 */
	public static double utStartTotalLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double ru = (hd - rn + ps) * 1.02;
		double rp = (hd + rn + ps) * 1.02;
		@SuppressWarnings("unused")
		double pj = Math.abs(sh * zh / Math.sqrt(s2 + z2));
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		double zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z6 = z1 - zd;

		r = rm + ru;
		dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z8 = z1 - zd;

		r = ru - rm;
		dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		zd = Math.sqrt(dd);
		double zcc = z1 - zd;

		if (zcc < 0.0)
			zcc = zc + 24.0;

		return zcc;
	}

	/**
	 * Calculate end time of total phase of lunar eclipse (UT)
	 * 
	 * Original macro name: UTEndTotalLunarEclipse
	 */
	public static double utEndTotalLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double ru = (hd - rn + ps) * 1.02;
		double rp = (hd + rn + ps) * 1.02;
		@SuppressWarnings("unused")
		double pj = Math.abs(sh * zh / Math.sqrt(s2 + z2));
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		double zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z6 = z1 - zd;

		r = rm + ru;
		dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z8 = z1 - zd;

		r = ru - rm;
		dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		zd = Math.sqrt(dd);
		double zb = z1 + zd - lint((z1 + zd) / 24.0) * 24.0;

		return zb;
	}

	/**
	 * Calculate magnitude of lunar eclipse.
	 * 
	 * Original macro name: MagLunarEclipse
	 */
	public static double magLunarEclipse(double dy, int mn, int yr, int ds, int zc) {
		double tp = 2.0 * Math.PI;

		if (lunarEclipseOccurrence(ds, zc, dy, mn, yr) == LunarEclipseOccurrence.NO_LUNAR_ECLIPSE)
			return -99.0;

		double dj = fullMoon(ds, zc, dy, mn, yr);
		double gday = julianDateDay(dj);
		int gmonth = julianDateMonth(dj);
		int gyear = julianDateYear(dj);
		double igday = Math.floor(gday);
		double xi = gday - igday;
		double utfm = xi * 24.0;
		double ut = utfm - 1.0;
		double ly = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double my = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double by = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hy = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		ut = utfm + 1.0;
		double sb = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear)) - ly;
		double mz = Math.toRadians(moonLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double bz = Math.toRadians(moonLat(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		double hz = Math.toRadians(moonHP(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));

		if (sb < 0.0)
			sb += tp;

		double xh = utfm;
		double x0 = xh + 1.0 - (2.0 * bz / (bz - by));
		double dm = mz - my;

		if (dm < 0.0)
			dm += tp;

		double lj = (dm - sb) / 2.0;
		double q = 0.0;
		double mr = my + (dm * (x0 - xh + 1.0) / 2.0);
		ut = x0 - 0.13851852;
		double rr = sunDist(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear);
		double sr = Math.toRadians(sunLong(ut, 0.0, 0.0, 0, 0, igday, gmonth, gyear));
		sr += Math.toRadians(nutatLong(igday, gmonth, gyear) - 0.00569);
		sr = sr + Math.PI - lint((sr + Math.PI) / tp) * tp;
		by -= q;
		bz -= q;
		double p3 = 0.00004263;
		double zh = (sr - mr) / lj;
		double tc = x0 + zh;
		double sh = (((bz - by) * (tc - xh - 1.0) / 2.0) + bz) / lj;
		double s2 = sh * sh;
		double z2 = zh * zh;
		double ps = p3 / (rr * lj);
		double z1 = (zh * z2 / (z2 + s2)) + x0;
		double h0 = (hy + hz) / (2.0 * lj);
		double rm = 0.272446 * h0;
		double rn = 0.00465242 / (lj * rr);
		double hd = h0 * 0.99834;
		double ru = (hd - rn + ps) * 1.02;
		double rp = (hd + rn + ps) * 1.02;
		double pj = Math.abs(sh * zh / Math.sqrt(s2 + z2));
		double r = rm + rp;
		double dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);

		if (dd < 0.0)
			return -99.0;

		double zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z6 = z1 - zd;

		r = rm + ru;
		dd = z1 - x0;
		dd = dd * dd - ((z2 - (r * r)) * dd / zh);
		double mg = (rm + rp - pj) / (2.0 * rm);

		if (dd < 0.0)
			return mg;

		zd = Math.sqrt(dd);
		@SuppressWarnings("unused")
		double z8 = z1 - zd;

		r = ru - rm;
		dd = z1 - x0;
		mg = (rm + ru - pj) / (2.0 * rm);

		return mg;
	}
}
