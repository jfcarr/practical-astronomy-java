package astro.practical.types.complex;

public class PositionOfSun {
    public double sunRAHour;
    public double sunRAMin;
    public double sunRASec;
    public double sunDecDeg;
    public double sunDecMin;
    public double sunDecSec;

    public PositionOfSun(double sunRAHour, double sunRAMin, double sunRASec, double sunDecDeg,
            double sunDecMin, double sunDecSec) {
        this.sunRAHour = sunRAHour;
        this.sunRAMin = sunRAMin;
        this.sunRASec = sunRASec;
        this.sunDecDeg = sunDecDeg;
        this.sunDecMin = sunDecMin;
        this.sunDecSec = sunDecSec;
    }
}
