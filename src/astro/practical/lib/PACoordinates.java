package astro.practical.lib;

import astro.practical.containers.Angle;
import astro.practical.containers.HourAngle;
import astro.practical.containers.RightAscension;

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
}
