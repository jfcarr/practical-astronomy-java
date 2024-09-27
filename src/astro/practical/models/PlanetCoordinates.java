package astro.practical.models;

public class PlanetCoordinates {
    public double planetLongitude;
    public double planetLatitude;
    public double planetDistanceAU;
    public double planetHLong1;
    public double planetHLong2;
    public double planetHLat;
    public double planetRVect;

    public PlanetCoordinates(double planetLongitude, double planetLatitude, double planetDistanceAU,
            double planetHLong1, double planetHLong2, double planetHLat, double planetRVect) {
        this.planetLongitude = planetLongitude;
        this.planetLatitude = planetLatitude;
        this.planetDistanceAU = planetDistanceAU;
        this.planetHLong1 = planetHLong1;
        this.planetHLong2 = planetHLong2;
        this.planetHLat = planetHLat;
        this.planetRVect = planetRVect;
    }
}
