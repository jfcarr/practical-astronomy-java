package astro.practical.lib;

import astro.practical.containers.Angle;

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
}
