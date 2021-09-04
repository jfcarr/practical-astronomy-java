package astro.practical.types;

public class RightAscensionDeclination {
	public double rightAscensionHours;
	public double rightAscensionMinutes;
	public double rightAscensionSeconds;
	public double declinationDegrees;
	public double declinationMinutes;
	public double declinationSeconds;

	public RightAscensionDeclination(double rightAscensionHours, double rightAscensionMinutes,
			double rightAscensionSeconds, double declinationDegrees, double declinationMinutes,
			double declinationSeconds) {
		this.rightAscensionHours = rightAscensionHours;
		this.rightAscensionMinutes = rightAscensionMinutes;
		this.rightAscensionSeconds = rightAscensionSeconds;
		this.declinationDegrees = declinationDegrees;
		this.declinationMinutes = declinationMinutes;
		this.declinationSeconds = declinationSeconds;
	}
}
