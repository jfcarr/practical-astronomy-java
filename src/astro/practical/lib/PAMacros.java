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
}
