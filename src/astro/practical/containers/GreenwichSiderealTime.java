package astro.practical.containers;

import astro.practical.types.Time;

/**
 * Representation of Greenwich Sidereal Time (hours, minutes, seconds)
 */
public class GreenwichSiderealTime extends Time {
	public GreenwichSiderealTime(double hours, double minutes, double seconds) {
		super(hours, minutes, seconds);
	}
}
