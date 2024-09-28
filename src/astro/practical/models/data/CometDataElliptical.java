package astro.practical.models.data;

public class CometDataElliptical {
    /** Name of comet */
    public String Name;

    /** Epoch of the perihelion */
    public double epoch_EpochOfPerihelion;

    /** Longitude of the perihelion */
    public double peri_LongitudeOfPerihelion;

    /** Longitude of the ascending node */
    public double node_LongitudeOfAscendingNode;

    /** Period of the orbit */
    public double period_PeriodOfOrbit;

    /** Semi-major axis of the orbit */
    public double axis_SemiMajorAxisOfOrbit;

    /** Eccentricity of the orbit */
    public double ecc_EccentricityOfOrbit;

    /** Inclination of the orbit */
    public double incl_InclinationOfOrbit;

    public CometDataElliptical(String Name, double epoch_EpochOfPerihelion, double peri_LongitudeOfPerihelion,
            double node_LongitudeOfAscendingNode, double period_PeriodOfOrbit, double axis_SemiMajorAxisOfOrbit,
            double ecc_EccentricityOfOrbit, double incl_InclinationOfOrbit) {
        this.Name = Name;
        this.epoch_EpochOfPerihelion = epoch_EpochOfPerihelion;
        this.peri_LongitudeOfPerihelion = peri_LongitudeOfPerihelion;
        this.node_LongitudeOfAscendingNode = node_LongitudeOfAscendingNode;
        this.period_PeriodOfOrbit = period_PeriodOfOrbit;
        this.axis_SemiMajorAxisOfOrbit = axis_SemiMajorAxisOfOrbit;
        this.ecc_EccentricityOfOrbit = ecc_EccentricityOfOrbit;
        this.incl_InclinationOfOrbit = incl_InclinationOfOrbit;
    }
}