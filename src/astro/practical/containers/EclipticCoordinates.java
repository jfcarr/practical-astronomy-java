package astro.practical.containers;

import astro.practical.types.CoordinatesLongLat;

public class EclipticCoordinates extends CoordinatesLongLat {
	public EclipticCoordinates(double longitudeDegrees, double longitudeMinutes, double longitudeSeconds,
			double latitudeDegrees, double latitudeMinutes, double latitudeSeconds) {
		super(longitudeDegrees, longitudeMinutes, longitudeSeconds, latitudeDegrees, latitudeMinutes, latitudeSeconds);
	}
}
