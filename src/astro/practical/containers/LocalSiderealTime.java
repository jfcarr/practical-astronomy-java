package astro.practical.containers;

import astro.practical.types.Time;

/**
 * Representation of Local Sidereal Time (hours, minutes, seconds)
 */
public class LocalSiderealTime extends Time {
	public LocalSiderealTime(double hours, double minutes, double seconds) {
		super(hours, minutes, seconds);
	}
}
