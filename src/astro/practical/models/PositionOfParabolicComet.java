package astro.practical.models;

public class PositionOfParabolicComet {
    public double cometRAHour;
    public double cometRAMin;
    public double cometRASec;
    public double cometDecDeg;
    public double cometDecMin;
    public double cometDecSec;
    public double cometDistEarth;

    public PositionOfParabolicComet(double cometRAHour, double cometRAMin, double cometRASec, double cometDecDeg,
            double cometDecMin, double cometDecSec, double cometDistEarth) {
        this.cometRAHour = cometRAHour;
        this.cometRAMin = cometRAMin;
        this.cometRASec = cometRASec;
        this.cometDecDeg = cometDecDeg;
        this.cometDecMin = cometDecMin;
        this.cometDecSec = cometDecSec;
        this.cometDistEarth = cometDistEarth;
    }
}
