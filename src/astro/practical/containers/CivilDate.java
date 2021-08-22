package astro.practical.containers;

/**
 * Representation of Civil Date (month, day, and year)
 */
public class CivilDate {
	public int month;
	public double day;
	public int year;

	/**
	 * Initialize CivilDate instance.
	 */
	public CivilDate(int month, double day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}
}
