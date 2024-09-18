package astro.practical.types.complex;

/**
 * Simple Time type (hours, minutes, seconds)
 */
public class Time {
	public double hours;
	public double minutes;
	public double seconds;

	public Time(double hours, double minutes, double seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}
}
