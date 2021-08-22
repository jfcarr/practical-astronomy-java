package astro.practical.containers;

import astro.practical.types.DateTime;

/**
 * Representation of full Universal date and time.
 */
public class UniversalDateTime extends DateTime {
	public UniversalDateTime(int hours, int minutes, int seconds, int day, int month, int year) {
		super(hours, minutes, seconds, day, month, year);
	}
}
