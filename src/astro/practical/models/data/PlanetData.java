package astro.practical.models.data;

public class PlanetData {
    public PlanetData(String name, double tp_PeriodOrbit, double long_LongitudeEpoch, double peri_LongitudePerihelion,
            double ecc_EccentricityOrbit, double axis_AxisOrbit, double incl_OrbitalInclination,
            double node_LongitudeAscendingNode, double theta0_AngularDiameter, double v0_VisualMagnitude) {
        this.Name = name;
        this.tp_PeriodOrbit = tp_PeriodOrbit;
        this.long_LongitudeEpoch = long_LongitudeEpoch;
        this.peri_LongitudePerihelion = peri_LongitudePerihelion;
        this.ecc_EccentricityOrbit = ecc_EccentricityOrbit;
        this.axis_AxisOrbit = axis_AxisOrbit;
        this.incl_OrbitalInclination = incl_OrbitalInclination;
        this.node_LongitudeAscendingNode = node_LongitudeAscendingNode;
        this.theta0_AngularDiameter = theta0_AngularDiameter;
        this.v0_VisualMagnitude = v0_VisualMagnitude;
    }

    /**
     * Name of planet.
     */
    public String Name;

    /**
     * Period of orbit.
     * 
     * Original element name: tp
     */
    public double tp_PeriodOrbit;

    /**
     * Longitude at the epoch.
     * 
     * Original element name: long
     */
    public double long_LongitudeEpoch;

    /**
     * Longitude of the perihelion.
     * 
     * Original element name: peri
     */
    public double peri_LongitudePerihelion;

    /**
     * Eccentricity of the orbit.
     * 
     * Original element name: ecc
     */
    public double ecc_EccentricityOrbit;

    /**
     * Semi-major axis of the orbit.
     * 
     * Original element name: axis
     */
    public double axis_AxisOrbit;

    /**
     * Orbital inclination.
     * 
     * Original element name: incl
     */
    public double incl_OrbitalInclination;

    /**
     * Longitude of the ascending node.
     * 
     * Original element name: node
     */
    public double node_LongitudeAscendingNode;

    /**
     * Angular diameter at 1 AU.
     * 
     * Original element name: theta0
     */
    public double theta0_AngularDiameter;

    /**
     * Visual magnitude at 1 AU.
     * 
     * Original element name: v0
     */
    public double v0_VisualMagnitude;
}
