package astro.practical.types;

public class CoordinatesLongLat {
	public double longitudeDegrees;
	public double longitudeMinutes;
	public double longitudeSeconds;
	public double latitudeDegrees;
	public double latitudeMinutes;
	public double latitudeSeconds;

	public CoordinatesLongLat(double longitudeDegrees, double longitudeMinutes, double longitudeSeconds,
			double latitudeDegrees, double latitudeMinutes, double latitudeSeconds) {
		this.longitudeDegrees = longitudeDegrees;
		this.longitudeMinutes = longitudeMinutes;
		this.longitudeSeconds = longitudeSeconds;
		this.latitudeDegrees = latitudeDegrees;
		this.latitudeMinutes = latitudeMinutes;
		this.latitudeSeconds = latitudeSeconds;
	}
}
