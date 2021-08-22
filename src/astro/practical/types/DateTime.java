package astro.practical.types;

/**
 * Base class for date/time representations.
 */
public class DateTime {
	public int hours;
	public int minutes;
	public int seconds;
	public int day;
	public int month;
	public int year;

	public DateTime(int hours, int minutes, int seconds, int day, int month, int year) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.day = day;
		this.month = month;
		this.year = year;
	}
}
