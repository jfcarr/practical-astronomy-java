package astro.practical.containers;

public class EclipticCoordinates {
	public double eclipticLongitudeDegrees;
	public double eclipticLongitudeMinutes;
	public double eclipticLongitudeSeconds;
	public double eclipticLatitudeDegrees;
	public double eclipticLatitudeMinutes;
	public double eclipticLatitudeSeconds;

	public EclipticCoordinates(double eclipticLongitudeDegrees, double eclipticLongitudeMinutes,
			double eclipticLongitudeSeconds, double eclipticLatitudeDegrees, double eclipticLatitudeMinutes,
			double eclipticLatitudeSeconds) {
		this.eclipticLongitudeDegrees = eclipticLongitudeDegrees;
		this.eclipticLongitudeMinutes = eclipticLongitudeMinutes;
		this.eclipticLongitudeSeconds = eclipticLongitudeSeconds;
		this.eclipticLatitudeDegrees = eclipticLatitudeDegrees;
		this.eclipticLatitudeMinutes = eclipticLatitudeMinutes;
		this.eclipticLatitudeSeconds = eclipticLatitudeSeconds;
	}
}
