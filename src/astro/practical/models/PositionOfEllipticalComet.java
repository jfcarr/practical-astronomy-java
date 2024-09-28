package astro.practical.models;

public class PositionOfEllipticalComet {
    public double cometRAHour;
    public double cometRAMin;
    public double cometDecDeg;
    public double cometDecMin;
    public double cometDistEarth;

    public PositionOfEllipticalComet(double cometRAHour, double cometRAMin, double cometDecDeg, double cometDecMin,
            double cometDistEarth) {
        this.cometRAHour = cometRAHour;
        this.cometRAMin = cometRAMin;
        this.cometDecDeg = cometDecDeg;
        this.cometDecMin = cometDecMin;
        this.cometDistEarth = cometDistEarth;
    }
}
