package astro.practical.lib;

import astro.practical.containers.Angle;
import astro.practical.containers.EclipticCoordinates;
import astro.practical.containers.EquatorialCoordinatesHA;
import astro.practical.containers.EquatorialCoordinatesRA;
import astro.practical.containers.GalacticCoordinates;
import astro.practical.containers.HorizonCoordinates;
import astro.practical.containers.HourAngle;
import astro.practical.containers.RightAscension;
import astro.practical.types.PAAngleMeasure;

public class PACoordinates {
	/**
	 * Convert an Angle (degrees, minutes, and seconds) to Decimal Degrees
	 */
	public double angleToDecimalDegrees(double degrees, double minutes, double seconds) {
		var a = Math.abs(seconds) / 60;
		var b = (Math.abs(minutes) + a) / 60;
		var c = Math.abs(degrees) + b;
		var d = (degrees < 0 || minutes < 0 || seconds < 0) ? -c : c;

		return d;
	}

	/**
	 * Convert Decimal Degrees to an Angle (degrees, minutes, and seconds)
	 */
	public Angle decimalDegreesToAngle(double decimalDegrees) {
		double unsignedDecimal = Math.abs(decimalDegrees);
		double totalSeconds = unsignedDecimal * 3600;
		double seconds2DP = PAUtil.round(totalSeconds % 60, 2);
		double correctedSeconds = (seconds2DP == 60) ? 0 : seconds2DP;
		double correctedRemainder = (seconds2DP == 60) ? totalSeconds + 60 : totalSeconds;
		double minutes = Math.floor(correctedRemainder / 60) % 60;
		double unsignedDegrees = Math.floor(correctedRemainder / 3600);
		double signedDegrees = (decimalDegrees < 0) ? -1 * unsignedDegrees : unsignedDegrees;

		return new Angle(signedDegrees, minutes, Math.floor(correctedSeconds));
	}

	/**
	 * Convert Right Ascension to Hour Angle
	 * 
	 * @return tuple <double hourAngleHours, double hourAngleMinutes, double
	 *         hourAngleSeconds>
	 */
	public HourAngle rightAscensionToHourAngle(double raHours, double raMinutes, double raSeconds, double lctHours,
			double lctMinutes, double lctSeconds, boolean isDaylightSavings, int zoneCorrection, double localDay,
			int localMonth, int localYear, double geographicalLongitude) {
		int daylightSaving = (isDaylightSavings) ? 1 : 0;

		double hourAngle = PAMacros.rightAscensionToHourAngle(raHours, raMinutes, raSeconds, lctHours, lctMinutes,
				lctSeconds, daylightSaving, zoneCorrection, localDay, localMonth, localYear, geographicalLongitude);

		int hourAngleHours = PAMacros.decimalHoursHour(hourAngle);
		int hourAngleMinutes = PAMacros.decimalHoursMinute(hourAngle);
		double hourAngleSeconds = PAMacros.decimalHoursSecond(hourAngle);

		return new HourAngle(hourAngleHours, hourAngleMinutes, hourAngleSeconds);
	}

	/**
	 * Convert Hour Angle to Right Ascension
	 * 
	 * @return RightAscension
	 */
	public RightAscension hourAngleToRightAscension(double hourAngleHours, double hourAngleMinutes,
			double hourAngleSeconds, double lctHours, double lctMinutes, double lctSeconds, boolean isDaylightSaving,
			int zoneCorrection, double localDay, int localMonth, int localYear, double geographicalLongitude) {
		int daylightSaving = (isDaylightSaving) ? 1 : 0;

		double rightAscension = PAMacros.hourAngleToRightAscension(hourAngleHours, hourAngleMinutes, hourAngleSeconds,
				lctHours, lctMinutes, lctSeconds, daylightSaving, zoneCorrection, localDay, localMonth, localYear,
				geographicalLongitude);

		int rightAscensionHours = PAMacros.decimalHoursHour(rightAscension);
		int rightAscensionMinutes = PAMacros.decimalHoursMinute(rightAscension);
		double rightAscensionSeconds = PAMacros.decimalHoursSecond(rightAscension);

		return new RightAscension(rightAscensionHours, rightAscensionMinutes, rightAscensionSeconds);
	}

	/**
	 * Convert Equatorial Coordinates to Horizon Coordinates
	 */
	public HorizonCoordinates equatorialCoordinatesToHorizonCoordinates(double hourAngleHours, double hourAngleMinutes,
			double hourAngleSeconds, double declinationDegrees, double declinationMinutes, double declinationSeconds,
			double geographicalLatitude) {
		double azimuthInDecimalDegrees = PAMacros.equatorialCoordinatesToAzimuth(hourAngleHours, hourAngleMinutes,
				hourAngleSeconds, declinationDegrees, declinationMinutes, declinationSeconds, geographicalLatitude);

		double altitudeInDecimalDegrees = PAMacros.equatorialCoordinatesToAltitude(hourAngleHours, hourAngleMinutes,
				hourAngleSeconds, declinationDegrees, declinationMinutes, declinationSeconds, geographicalLatitude);

		double azimuthDegrees = PAMacros.decimalDegreesDegrees(azimuthInDecimalDegrees);
		double azimuthMinutes = PAMacros.decimalDegreesMinutes(azimuthInDecimalDegrees);
		double azimuthSeconds = PAMacros.decimalDegreesSeconds(azimuthInDecimalDegrees);

		double altitudeDegrees = PAMacros.decimalDegreesDegrees(altitudeInDecimalDegrees);
		double altitudeMinutes = PAMacros.decimalDegreesMinutes(altitudeInDecimalDegrees);
		double altitudeSeconds = PAMacros.decimalDegreesSeconds(altitudeInDecimalDegrees);

		return new HorizonCoordinates(azimuthDegrees, azimuthMinutes, azimuthSeconds, altitudeDegrees, altitudeMinutes,
				altitudeSeconds);
	}

	/**
	 * Convert Horizon Coordinates to Equatorial Coordinates
	 */
	public EquatorialCoordinatesHA horizonCoordinatesToEquatorialCoordinates(double azimuthDegrees,
			double azimuthMinutes, double azimuthSeconds, double altitudeDegrees, double altitudeMinutes,
			double altitudeSeconds, double geographicalLatitude) {
		double hourAngleInDecimalDegrees = PAMacros.horizonCoordinatesToHourAngle(azimuthDegrees, azimuthMinutes,
				azimuthSeconds, altitudeDegrees, altitudeMinutes, altitudeSeconds, geographicalLatitude);

		double declinationInDecimalDegrees = PAMacros.horizonCoordinatesToDeclination(azimuthDegrees, azimuthMinutes,
				azimuthSeconds, altitudeDegrees, altitudeMinutes, altitudeSeconds, geographicalLatitude);

		int hourAngleHours = PAMacros.decimalHoursHour(hourAngleInDecimalDegrees);
		int hourAngleMinutes = PAMacros.decimalHoursMinute(hourAngleInDecimalDegrees);
		double hourAngleSeconds = PAMacros.decimalHoursSecond(hourAngleInDecimalDegrees);

		double declinationDegrees = PAMacros.decimalDegreesDegrees(declinationInDecimalDegrees);
		double declinationMinutes = PAMacros.decimalDegreesMinutes(declinationInDecimalDegrees);
		double declinationSeconds = PAMacros.decimalDegreesSeconds(declinationInDecimalDegrees);

		return new EquatorialCoordinatesHA(hourAngleHours, hourAngleMinutes, hourAngleSeconds, declinationDegrees,
				declinationMinutes, declinationSeconds);
	}

	/**
	 * Calculate Mean Obliquity of the Ecliptic for a Greenwich Date
	 */
	public double meanObliquityOfTheEcliptic(double greenwichDay, int greenwichMonth, int greenwichYear) {
		double jd = PAMacros.civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear);
		double mjd = jd - 2451545;
		double t = mjd / 36525;
		double de1 = t * (46.815 + t * (0.0006 - (t * 0.00181)));
		double de2 = de1 / 3600;

		return 23.439292 - de2;
	}

	/**
	 * Convert Ecliptic Coordinates to Equatorial Coordinates
	 */
	public EquatorialCoordinatesRA eclipticCoordinateToEquatorialCoordinate(double eclipticLongitudeDegrees,
			double eclipticLongitudeMinutes, double eclipticLongitudeSeconds, double eclipticLatitudeDegrees,
			double eclipticLatitudeMinutes, double eclipticLatitudeSeconds, double greenwichDay, int greenwichMonth,
			int greenwichYear) {
		double eclonDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(eclipticLongitudeDegrees,
				eclipticLongitudeMinutes, eclipticLongitudeSeconds);
		double eclatDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(eclipticLatitudeDegrees,
				eclipticLatitudeMinutes, eclipticLatitudeSeconds);
		double eclonRad = Math.toRadians(eclonDeg);
		double eclatRad = Math.toRadians(eclatDeg);
		double obliqDeg = PAMacros.obliq(greenwichDay, greenwichMonth, greenwichYear);
		double obliqRad = Math.toRadians(obliqDeg);
		double sinDec = Math.sin(eclatRad) * Math.cos(obliqRad)
				+ Math.cos(eclatRad) * Math.sin(obliqRad) * Math.sin(eclonRad);
		double decRad = Math.asin(sinDec);
		double decDeg = PAMacros.wToDegrees(decRad);
		double y = Math.sin(eclonRad) * Math.cos(obliqRad) - Math.tan(eclatRad) * Math.sin(obliqRad);
		double x = Math.cos(eclonRad);
		double raRad = Math.atan2(y, x);
		double raDeg1 = PAMacros.wToDegrees(raRad);
		double raDeg2 = raDeg1 - 360 * Math.floor(raDeg1 / 360);
		double raHours = PAMacros.decimalDegreesToDegreeHours(raDeg2);

		int outRAHours = PAMacros.decimalHoursHour(raHours);
		int outRAMinutes = PAMacros.decimalHoursMinute(raHours);
		double outRASeconds = PAMacros.decimalHoursSecond(raHours);
		double outDecDegrees = PAMacros.decimalDegreesDegrees(decDeg);
		double outDecMinutes = PAMacros.decimalDegreesMinutes(decDeg);
		double outDecSeconds = PAMacros.decimalDegreesSeconds(decDeg);

		return new EquatorialCoordinatesRA(outRAHours, outRAMinutes, outRASeconds, outDecDegrees, outDecMinutes,
				outDecSeconds);
	}

	/**
	 * Convert Equatorial Coordinates to Ecliptic Coordinates
	 */
	public EclipticCoordinates equatorialCoordinateToEclipticCoordinate(double raHours, double raMinutes,
			double raSeconds, double decDegrees, double decMinutes, double decSeconds, double gwDay, int gwMonth,
			int gwYear) {
		double raDeg = PAMacros.degreeHoursToDecimalDegrees(PAMacros.hmsToDH(raHours, raMinutes, raSeconds));
		double decDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(decDegrees, decMinutes, decSeconds);
		double raRad = Math.toRadians(raDeg);
		double decRad = Math.toRadians(decDeg);
		double obliqDeg = PAMacros.obliq(gwDay, gwMonth, gwYear);
		double obliqRad = Math.toRadians(obliqDeg);
		double sinEclLat = Math.sin(decRad) * Math.cos(obliqRad)
				- Math.cos(decRad) * Math.sin(obliqRad) * Math.sin(raRad);
		double eclLatRad = Math.asin(sinEclLat);
		double eclLatDeg = PAMacros.wToDegrees(eclLatRad);
		double y = Math.sin(raRad) * Math.cos(obliqRad) + Math.tan(decRad) * Math.sin(obliqRad);
		double x = Math.cos(raRad);
		double eclLongRad = Math.atan2(y, x);
		double eclLongDeg1 = PAMacros.wToDegrees(eclLongRad);
		double eclLongDeg2 = eclLongDeg1 - 360 * Math.floor(eclLongDeg1 / 360);

		double outEclLongDeg = PAMacros.decimalDegreesDegrees(eclLongDeg2);
		double outEclLongMin = PAMacros.decimalDegreesMinutes(eclLongDeg2);
		double outEclLongSec = PAMacros.decimalDegreesSeconds(eclLongDeg2);
		double outEclLatDeg = PAMacros.decimalDegreesDegrees(eclLatDeg);
		double outEclLatMin = PAMacros.decimalDegreesMinutes(eclLatDeg);
		double outEclLatSec = PAMacros.decimalDegreesSeconds(eclLatDeg);

		return new EclipticCoordinates(outEclLongDeg, outEclLongMin, outEclLongSec, outEclLatDeg, outEclLatMin,
				outEclLatSec);
	}

	/**
	 * Convert Equatorial Coordinates to Galactic Coordinates
	 */
	public GalacticCoordinates equatorialCoordinateToGalacticCoordinate(double raHours, double raMinutes,
			double raSeconds, double decDegrees, double decMinutes, double decSeconds) {
		double raDeg = PAMacros.degreeHoursToDecimalDegrees(PAMacros.hmsToDH(raHours, raMinutes, raSeconds));
		double decDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(decDegrees, decMinutes, decSeconds);
		double raRad = Math.toRadians(raDeg);
		double decRad = Math.toRadians(decDeg);
		double sinB = Math.cos(decRad) * Math.cos(Math.toRadians(27.4)) * Math.cos(raRad - Math.toRadians(192.25))
				+ Math.sin(decRad) * Math.sin(Math.toRadians(27.4));
		double bRadians = Math.asin(sinB);
		double bDeg = PAMacros.wToDegrees(bRadians);
		double y = Math.sin(decRad) - sinB * Math.sin(Math.toRadians(27.4));
		double x = Math.cos(decRad) * Math.sin(raRad - Math.toRadians(192.25)) * Math.cos(Math.toRadians(27.4));
		double longDeg1 = PAMacros.wToDegrees(Math.atan2(y, x)) + 33;
		double longDeg2 = longDeg1 - 360 * Math.floor(longDeg1 / 360);

		double galLongDeg = PAMacros.decimalDegreesDegrees(longDeg2);
		double galLongMin = PAMacros.decimalDegreesMinutes(longDeg2);
		double galLongSec = PAMacros.decimalDegreesSeconds(longDeg2);
		double galLatDeg = PAMacros.decimalDegreesDegrees(bDeg);
		double galLatMin = PAMacros.decimalDegreesMinutes(bDeg);
		double galLatSec = PAMacros.decimalDegreesSeconds(bDeg);

		return new GalacticCoordinates(galLongDeg, galLongMin, galLongSec, galLatDeg, galLatMin, galLatSec);
	}

	/**
	 * Convert Galactic Coordinates to Equatorial Coordinates
	 */
	public EquatorialCoordinatesRA galacticCoordinateToEquatorialCoordinate(double galLongDeg, double galLongMin,
			double galLongSec, double galLatDeg, double galLatMin, double galLatSec) {
		double glongDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(galLongDeg, galLongMin, galLongSec);
		double glatDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(galLatDeg, galLatMin, galLatSec);
		double glongRad = Math.toRadians(glongDeg);
		double glatRad = Math.toRadians(glatDeg);
		double sinDec = Math.cos(glatRad) * Math.cos(Math.toRadians(27.4)) * Math.sin(glongRad - Math.toRadians(33.0))
				+ Math.sin(glatRad) * Math.sin(Math.toRadians(27.4));
		double decRadians = Math.asin(sinDec);
		double decDeg = PAMacros.wToDegrees(decRadians);
		double y = Math.cos(glatRad) * Math.cos(glongRad - Math.toRadians(33.0));
		double x = Math.sin(glatRad) * Math.cos(Math.toRadians(27.4))
				- Math.cos(glatRad) * Math.sin(Math.toRadians(27.4)) * Math.sin(glongRad - Math.toRadians(33.0));

		double raDeg1 = PAMacros.wToDegrees(Math.atan2(y, x)) + 192.25;
		double raDeg2 = raDeg1 - 360 * Math.floor(raDeg1 / 360);
		double raHours1 = PAMacros.decimalDegreesToDegreeHours(raDeg2);

		double raHours = PAMacros.decimalHoursHour(raHours1);
		double raMinutes = PAMacros.decimalHoursMinute(raHours1);
		double raSeconds = PAMacros.decimalHoursSecond(raHours1);
		double decDegrees = PAMacros.decimalDegreesDegrees(decDeg);
		double decMinutes = PAMacros.decimalDegreesMinutes(decDeg);
		double decSeconds = PAMacros.decimalDegreesSeconds(decDeg);

		return new EquatorialCoordinatesRA(raHours, raMinutes, raSeconds, decDegrees, decMinutes, decSeconds);
	}

	/**
	 * Calculate the angle between two celestial objects
	 */
	public Angle angleBetweenTwoObjects(double raLong1HourDeg, double raLong1Min, double raLong1Sec, double decLat1Deg,
			double decLat1Min, double decLat1Sec, double raLong2HourDeg, double raLong2Min, double raLong2Sec,
			double decLat2Deg, double decLat2Min, double decLat2Sec, PAAngleMeasure hourOrDegree) {
		double raLong1Decimal = (hourOrDegree == PAAngleMeasure.Hours)
				? PAMacros.hmsToDH(raLong1HourDeg, raLong1Min, raLong1Sec)
				: PAMacros.degreesMinutesSecondsToDecimalDegrees(raLong1HourDeg, raLong1Min, raLong1Sec);
		double raLong1Deg = (hourOrDegree == PAAngleMeasure.Hours)
				? PAMacros.degreeHoursToDecimalDegrees(raLong1Decimal)
				: raLong1Decimal;

		double raLong1Rad = Math.toRadians(raLong1Deg);
		double decLat1Deg1 = PAMacros.degreesMinutesSecondsToDecimalDegrees(decLat1Deg, decLat1Min, decLat1Sec);
		double decLat1Rad = Math.toRadians(decLat1Deg1);

		double raLong2Decimal = (hourOrDegree == PAAngleMeasure.Hours)
				? PAMacros.hmsToDH(raLong2HourDeg, raLong2Min, raLong2Sec)
				: PAMacros.degreesMinutesSecondsToDecimalDegrees(raLong2HourDeg, raLong2Min, raLong2Sec);
		double raLong2Deg = (hourOrDegree == PAAngleMeasure.Hours)
				? PAMacros.degreeHoursToDecimalDegrees(raLong2Decimal)
				: raLong2Decimal;
		double raLong2Rad = Math.toRadians(raLong2Deg);
		double decLat2Deg1 = PAMacros.degreesMinutesSecondsToDecimalDegrees(decLat2Deg, decLat2Min, decLat2Sec);
		double decLat2Rad = Math.toRadians(decLat2Deg1);

		double cosD = Math.sin(decLat1Rad) * Math.sin(decLat2Rad)
				+ Math.cos(decLat1Rad) * Math.cos(decLat2Rad) * Math.cos(raLong1Rad - raLong2Rad);
		double dRad = Math.acos(cosD);
		double dDeg = PAMacros.wToDegrees(dRad);

		double angleDeg = PAMacros.decimalDegreesDegrees(dDeg);
		double angleMin = PAMacros.decimalDegreesMinutes(dDeg);
		double angleSec = PAMacros.decimalDegreesSeconds(dDeg);

		return new Angle(angleDeg, angleMin, angleSec);
	}
}
