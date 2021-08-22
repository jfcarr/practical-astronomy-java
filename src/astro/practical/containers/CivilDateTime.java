package astro.practical.containers;

import astro.practical.types.DateTime;

/**
 * Representation of full Civil date and time.
 */
public class CivilDateTime extends DateTime {
	public CivilDateTime(int hours, int minutes, int seconds, int day, int month, int year) {
		super(hours, minutes, seconds, day, month, year);
	}
}
