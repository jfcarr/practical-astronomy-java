package astro.practical.models;

public class PrecisePositionOfMoon {
    public double moonRAHour;
    public double moonRAMin;
    public double moonRASec;
    public double moonDecDeg;
    public double moonDecMin;
    public double moonDecSec;
    public double earthMoonDistKM;
    public double moonHorParallaxDeg;

    public PrecisePositionOfMoon(double moonRAHour, double moonRAMin, double moonRASec, double moonDecDeg,
            double moonDecMin, double moonDecSec, double earthMoonDistKM, double moonHorParallaxDeg) {
        this.moonRAHour = moonRAHour;
        this.moonRAMin = moonRAMin;
        this.moonRASec = moonRASec;
        this.moonDecDeg = moonDecDeg;
        this.moonDecMin = moonDecMin;
        this.moonDecSec = moonDecSec;
        this.earthMoonDistKM = earthMoonDistKM;
        this.moonHorParallaxDeg = moonHorParallaxDeg;
    }
}
