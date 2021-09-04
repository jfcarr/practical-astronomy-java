package astro.practical.containers;

public class EquatorialCoordinatesHA {
	public double hourAngleHours;
	public double hourAngleMinutes;
	public double hourAngleSeconds;
	public double declinationDegrees;
	public double declinationMinutes;
	public double declinationSeconds;

	public EquatorialCoordinatesHA(double hourAngleHours, double hourAngleMinutes, double hourAngleSeconds,
			double declinationDegrees, double declinationMinutes, double declinationSeconds) {
		this.hourAngleHours = hourAngleHours;
		this.hourAngleMinutes = hourAngleMinutes;
		this.hourAngleSeconds = hourAngleSeconds;
		this.declinationDegrees = declinationDegrees;
		this.declinationMinutes = declinationMinutes;
		this.declinationSeconds = declinationSeconds;
	}
}
