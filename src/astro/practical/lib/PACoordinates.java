package astro.practical.lib;

import astro.practical.containers.Aberration;
import astro.practical.containers.Angle;
import astro.practical.containers.EclipticCoordinates;
import astro.practical.containers.EquatorialCoordinatesHA;
import astro.practical.containers.EquatorialCoordinatesRA;
import astro.practical.containers.GalacticCoordinates;
import astro.practical.containers.HorizonCoordinates;
import astro.practical.containers.HourAngle;
import astro.practical.containers.Nutation;
import astro.practical.containers.RightAscension;
import astro.practical.containers.RiseSet;
import astro.practical.types.CoordinateType;
import astro.practical.types.PAAngleMeasure;
import astro.practical.types.RightAscensionDeclination;
import astro.practical.types.RiseSetStatus;

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

	/**
	 * Calculate rising and setting times for an object.
	 */
	public RiseSet risingAndSetting(double raHours, double raMinutes, double raSeconds, double decDeg, double decMin,
			double decSec, double gwDateDay, int gwDateMonth, int gwDateYear, double geogLongDeg, double geogLatDeg,
			double vertShiftDeg) {
		double raHours1 = PAMacros.hmsToDH(raHours, raMinutes, raSeconds);
		double decRad = Math.toRadians(PAMacros.degreesMinutesSecondsToDecimalDegrees(decDeg, decMin, decSec));
		double verticalDisplRadians = Math.toRadians(vertShiftDeg);
		double geoLatRadians = Math.toRadians(geogLatDeg);
		double cosH = -(Math.sin(verticalDisplRadians) + Math.sin(geoLatRadians) * Math.sin(decRad))
				/ (Math.cos(geoLatRadians) * Math.cos(decRad));
		double hHours = PAMacros.decimalDegreesToDegreeHours(PAMacros.wToDegrees(Math.acos(cosH)));
		double lstRiseHours = (raHours1 - hHours) - 24 * Math.floor((raHours1 - hHours) / 24);
		double lstSetHours = (raHours1 + hHours) - 24 * Math.floor((raHours1 + hHours) / 24);
		double aDeg = PAMacros
				.wToDegrees(Math.acos((Math.sin(decRad) + Math.sin(verticalDisplRadians) * Math.sin(geoLatRadians))
						/ (Math.cos(verticalDisplRadians) * Math.cos(geoLatRadians))));
		double azRiseDeg = aDeg - 360 * Math.floor(aDeg / 360);
		double azSetDeg = (360 - aDeg) - 360 * Math.floor((360 - aDeg) / 360);
		double utRiseHours1 = PAMacros.greenwichSiderealTimeToUniversalTime(
				PAMacros.localSiderealTimeToGreenwichSiderealTime(lstRiseHours, 0, 0, geogLongDeg), 0, 0, gwDateDay,
				gwDateMonth, gwDateYear);
		double utSetHours1 = PAMacros.greenwichSiderealTimeToUniversalTime(
				PAMacros.localSiderealTimeToGreenwichSiderealTime(lstSetHours, 0, 0, geogLongDeg), 0, 0, gwDateDay,
				gwDateMonth, gwDateYear);
		double utRiseAdjustedHours = utRiseHours1 + 0.008333;
		double utSetAdjustedHours = utSetHours1 + 0.008333;

		RiseSetStatus riseSetStatus = RiseSetStatus.OK;
		if (cosH > 1)
			riseSetStatus = RiseSetStatus.NEVERRISES;
		if (cosH < -1)
			riseSetStatus = RiseSetStatus.CIRCUMPOLAR;

		var utRiseHour = (riseSetStatus == RiseSetStatus.OK) ? PAMacros.decimalHoursHour(utRiseAdjustedHours) : 0;
		var utRiseMin = (riseSetStatus == RiseSetStatus.OK) ? PAMacros.decimalHoursMinute(utRiseAdjustedHours) : 0;
		var utSetHour = (riseSetStatus == RiseSetStatus.OK) ? PAMacros.decimalHoursHour(utSetAdjustedHours) : 0;
		var utSetMin = (riseSetStatus == RiseSetStatus.OK) ? PAMacros.decimalHoursMinute(utSetAdjustedHours) : 0;
		var azRise = (riseSetStatus == RiseSetStatus.OK) ? PAUtil.round(azRiseDeg, 2) : 0;
		var azSet = (riseSetStatus == RiseSetStatus.OK) ? PAUtil.round(azSetDeg, 2) : 0;

		return new RiseSet(riseSetStatus, utRiseHour, utRiseMin, utSetHour, utSetMin, azRise, azSet);
	}

	/**
	 * Calculate precession (corrected coordinates between two epochs)
	 */
	public RightAscensionDeclination correctForPrecession(double raHour, double raMinutes, double raSeconds,
			double decDeg, double decMinutes, double decSeconds, double epoch1Day, int epoch1Month, int epoch1Year,
			double epoch2Day, int epoch2Month, int epoch2Year) {
		double ra1Rad = Math
				.toRadians(PAMacros.degreeHoursToDecimalDegrees(PAMacros.hmsToDH(raHour, raMinutes, raSeconds)));
		double dec1Rad = Math.toRadians(PAMacros.degreesMinutesSecondsToDecimalDegrees(decDeg, decMinutes, decSeconds));
		double tCenturies = (PAMacros.civilDateToJulianDate(epoch1Day, epoch1Month, epoch1Year) - 2415020) / 36525;
		double mSec = 3.07234 + (0.00186 * tCenturies);
		double nArcsec = 20.0468 - (0.0085 * tCenturies);
		double nYears = (PAMacros.civilDateToJulianDate(epoch2Day, epoch2Month, epoch2Year)
				- PAMacros.civilDateToJulianDate(epoch1Day, epoch1Month, epoch1Year)) / 365.25;
		double s1Hours = ((mSec + (nArcsec * Math.sin(ra1Rad) * Math.tan(dec1Rad) / 15)) * nYears) / 3600;
		double ra2Hours = PAMacros.hmsToDH(raHour, raMinutes, raSeconds) + s1Hours;
		double s2Deg = (nArcsec * Math.cos(ra1Rad) * nYears) / 3600;
		double dec2Deg = PAMacros.degreesMinutesSecondsToDecimalDegrees(decDeg, decMinutes, decSeconds) + s2Deg;

		int correctedRAHour = PAMacros.decimalHoursHour(ra2Hours);
		int correctedRAMinutes = PAMacros.decimalHoursMinute(ra2Hours);
		double correctedRASeconds = PAMacros.decimalHoursSecond(ra2Hours);
		double correctedDecDeg = PAMacros.decimalDegreesDegrees(dec2Deg);
		double correctedDecMinutes = PAMacros.decimalDegreesMinutes(dec2Deg);
		double correctedDecSeconds = PAMacros.decimalDegreesSeconds(dec2Deg);

		return new RightAscensionDeclination(correctedRAHour, correctedRAMinutes, correctedRASeconds, correctedDecDeg,
				correctedDecMinutes, correctedDecSeconds);
	}

	/**
	 * Calculate nutation for two values: ecliptic longitude and obliquity, for a
	 * Greenwich date.
	 */
	public Nutation nutationInEclipticLongitudeAndObliquity(double greenwichDay, int greenwichMonth,
			int greenwichYear) {
		double jdDays = PAMacros.civilDateToJulianDate(greenwichDay, greenwichMonth, greenwichYear);
		double tCenturies = (jdDays - 2415020) / 36525;
		double aDeg = 100.0021358 * tCenturies;
		double l1Deg = 279.6967 + (0.000303 * tCenturies * tCenturies);
		double lDeg1 = l1Deg + 360 * (aDeg - Math.floor(aDeg));
		double lDeg2 = lDeg1 - 360 * Math.floor(lDeg1 / 360);
		double lRad = Math.toRadians(lDeg2);
		double bDeg = 5.372617 * tCenturies;
		double nDeg1 = 259.1833 - 360 * (bDeg - Math.floor(bDeg));
		double nDeg2 = nDeg1 - 360 * (Math.floor(nDeg1 / 360));
		double nRad = Math.toRadians(nDeg2);
		double nutInLongArcsec = -17.2 * Math.sin(nRad) - 1.3 * Math.sin(2 * lRad);
		double nutInOblArcsec = 9.2 * Math.cos(nRad) + 0.5 * Math.cos(2 * lRad);

		double nutInLongDeg = nutInLongArcsec / 3600;
		double nutInOblDeg = nutInOblArcsec / 3600;

		return new Nutation(nutInLongDeg, nutInOblDeg);
	}

	/**
	 * Correct ecliptic coordinates for the effects of aberration.
	 */
	public Aberration correctForAberration(double utHour, double utMinutes, double utSeconds, double gwDay, int gwMonth,
			int gwYear, double trueEclLongDeg, double trueEclLongMin, double trueEclLongSec, double trueEclLatDeg,
			double trueEclLatMin, double trueEclLatSec) {
		double trueLongDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(trueEclLongDeg, trueEclLongMin,
				trueEclLongSec);
		double trueLatDeg = PAMacros.degreesMinutesSecondsToDecimalDegrees(trueEclLatDeg, trueEclLatMin, trueEclLatSec);
		double sunTrueLongDeg = PAMacros.sunLong(utHour, utMinutes, utSeconds, 0, 0, gwDay, gwMonth, gwYear);
		double dlongArcsec = -20.5 * Math.cos(Math.toRadians(sunTrueLongDeg - trueLongDeg))
				/ Math.cos(Math.toRadians(trueLatDeg));
		double dlatArcsec = -20.5 * Math.sin(Math.toRadians(sunTrueLongDeg - trueLongDeg))
				* Math.sin(Math.toRadians(trueLatDeg));
		double apparentLongDeg = trueLongDeg + (dlongArcsec / 3600);
		double apparentLatDeg = trueLatDeg + (dlatArcsec / 3600);

		double apparentEclLongDeg = PAMacros.decimalDegreesDegrees(apparentLongDeg);
		double apparentEclLongMin = PAMacros.decimalDegreesMinutes(apparentLongDeg);
		double apparentEclLongSec = PAMacros.decimalDegreesSeconds(apparentLongDeg);
		double apparentEclLatDeg = PAMacros.decimalDegreesDegrees(apparentLatDeg);
		double apparentEclLatMin = PAMacros.decimalDegreesMinutes(apparentLatDeg);
		double apparentEclLatSec = PAMacros.decimalDegreesSeconds(apparentLatDeg);

		return new Aberration(apparentEclLongDeg, apparentEclLongMin, apparentEclLongSec, apparentEclLatDeg,
				apparentEclLatMin, apparentEclLatSec);
	}

	/**
	 * Calculate corrected RA/Dec, accounting for atmospheric refraction.
	 * 
	 * NOTE: Valid values for coordinate_type are "TRUE" and "APPARENT".
	 */
	public RightAscensionDeclination atmosphericRefraction(double trueRAHour, double trueRAMin, double trueRASec,
			double trueDecDeg, double trueDecMin, double trueDecSec, CoordinateType coordinateType, double geogLongDeg,
			double geogLatDeg, int daylightSavingHours, int timezoneHours, double lcdDay, int lcdMonth, int lcdYear,
			double lctHour, double lctMin, double lctSec, double atmosphericPressureMbar,
			double atmosphericTemperatureCelsius) {
		double haHour = PAMacros.rightAscensionToHourAngle(trueRAHour, trueRAMin, trueRASec, lctHour, lctMin, lctSec,
				daylightSavingHours, timezoneHours, lcdDay, lcdMonth, lcdYear, geogLongDeg);
		double azimuthDeg = PAMacros.equatorialCoordinatesToAzimuth(haHour, 0, 0, trueDecDeg, trueDecMin, trueDecSec,
				geogLatDeg);
		double altitudeDeg = PAMacros.equatorialCoordinatesToAltitude(haHour, 0, 0, trueDecDeg, trueDecMin, trueDecSec,
				geogLatDeg);
		double correctedAltitudeDeg = PAMacros.refract(altitudeDeg, coordinateType, atmosphericPressureMbar,
				atmosphericTemperatureCelsius);

		double correctedHAHour = PAMacros.horizonCoordinatesToHourAngle(azimuthDeg, 0, 0, correctedAltitudeDeg, 0, 0,
				geogLatDeg);
		double correctedRAHour1 = PAMacros.hourAngleToRightAscension(correctedHAHour, 0, 0, lctHour, lctMin, lctSec,
				daylightSavingHours, timezoneHours, lcdDay, lcdMonth, lcdYear, geogLongDeg);
		double correctedDecDeg1 = PAMacros.horizonCoordinatesToDeclination(azimuthDeg, 0, 0, correctedAltitudeDeg, 0, 0,
				geogLatDeg);

		int correctedRAHour = PAMacros.decimalHoursHour(correctedRAHour1);
		int correctedRAMin = PAMacros.decimalHoursMinute(correctedRAHour1);
		double correctedRASec = PAMacros.decimalHoursSecond(correctedRAHour1);
		double correctedDecDeg = PAMacros.decimalDegreesDegrees(correctedDecDeg1);
		double correctedDecMin = PAMacros.decimalDegreesMinutes(correctedDecDeg1);
		double correctedDecSec = PAMacros.decimalDegreesSeconds(correctedDecDeg1);

		return new RightAscensionDeclination(correctedRAHour, correctedRAMin, correctedRASec, correctedDecDeg,
				correctedDecMin, correctedDecSec);
	}

	/**
	 * Calculate corrected RA/Dec, accounting for geocentric parallax.
	 */
	public RightAscensionDeclination correctionsForGeocentricParallax(double raHour, double raMin, double raSec,
			double decDeg, double decMin, double decSec, CoordinateType coordinateType, double equatorialHorParallaxDeg,
			double geogLongDeg, double geogLatDeg, double heightM, int daylightSaving, int timezoneHours, double lcdDay,
			int lcdMonth, int lcdYear, double lctHour, double lctMin, double lctSec) {
		double haHours = PAMacros.rightAscensionToHourAngle(raHour, raMin, raSec, lctHour, lctMin, lctSec,
				daylightSaving, timezoneHours, lcdDay, lcdMonth, lcdYear, geogLongDeg);

		double correctedHAHours = PAMacros.parallaxHA(haHours, 0, 0, decDeg, decMin, decSec, coordinateType, geogLatDeg,
				heightM, equatorialHorParallaxDeg);

		double correctedRAHours = PAMacros.hourAngleToRightAscension(correctedHAHours, 0, 0, lctHour, lctMin, lctSec,
				daylightSaving, timezoneHours, lcdDay, lcdMonth, lcdYear, geogLongDeg);

		double correctedDecDeg1 = PAMacros.parallaxDec(haHours, 0, 0, decDeg, decMin, decSec, coordinateType,
				geogLatDeg, heightM, equatorialHorParallaxDeg);

		int correctedRAHour = PAMacros.decimalHoursHour(correctedRAHours);
		int correctedRAMin = PAMacros.decimalHoursMinute(correctedRAHours);
		double correctedRASec = PAMacros.decimalHoursSecond(correctedRAHours);
		double correctedDecDeg = PAMacros.decimalDegreesDegrees(correctedDecDeg1);
		double correctedDecMin = PAMacros.decimalDegreesMinutes(correctedDecDeg1);
		double correctedDecSec = PAMacros.decimalDegreesSeconds(correctedDecDeg1);

		return new RightAscensionDeclination(correctedRAHour, correctedRAMin, correctedRASec, correctedDecDeg,
				correctedDecMin, correctedDecSec);
	}
}
