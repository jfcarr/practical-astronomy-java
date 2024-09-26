package astro.practical.types.complex;

public class PlanetPosition {
    public double planetRAHour;
    public double planetRAMin;
    public double planetRASec;
    public double planetDecDeg;
    public double planetDecMin;
    public double planetDecSec;

    public PlanetPosition(double planetRAHour, double planetRAMin, double planetRASec, double planetDecDeg,
            double planetDecMin, double planetDecSec) {
        this.planetRAHour = planetRAHour;
        this.planetRAMin = planetRAMin;
        this.planetRASec = planetRASec;
        this.planetDecDeg = planetDecDeg;
        this.planetDecMin = planetDecMin;
        this.planetDecSec = planetDecSec;
    }
}
