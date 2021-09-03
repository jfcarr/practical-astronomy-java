package astro.practical.containers;

public class HorizonCoordinates {
	public double azimuthDegrees;
	public double azimuthMinutes;
	public double azimuthSeconds;
	public double altitudeDegrees;
	public double altitudeMinutes;
	public double altitudeSeconds;

	public HorizonCoordinates(double azimuthDegrees, double azimuthMinutes, double azimuthSeconds,
			double altitudeDegrees, double altitudeMinutes, double altitudeSeconds) {
		this.azimuthDegrees = azimuthDegrees;
		this.azimuthMinutes = azimuthMinutes;
		this.azimuthSeconds = azimuthSeconds;
		this.altitudeDegrees = altitudeDegrees;
		this.altitudeMinutes = altitudeMinutes;
		this.altitudeSeconds = altitudeSeconds;
	}
}
