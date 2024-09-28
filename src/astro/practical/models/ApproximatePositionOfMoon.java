package astro.practical.models;

public class ApproximatePositionOfMoon {
    public double moonRAHour;
    public double moonRAMin;
    public double moonRASec;
    public double moonDecDeg;
    public double moonDecMin;
    public double moonDecSec;

    public ApproximatePositionOfMoon(double moonRAHour, double moonRAMin, double moonRASec, double moonDecDeg,
            double moonDecMin, double moonDecSec) {
        this.moonRAHour = moonRAHour;
        this.moonRAMin = moonRAMin;
        this.moonRASec = moonRASec;
        this.moonDecDeg = moonDecDeg;
        this.moonDecMin = moonDecMin;
        this.moonDecSec = moonDecSec;
    }
}
