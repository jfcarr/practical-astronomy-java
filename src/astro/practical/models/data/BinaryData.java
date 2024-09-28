package astro.practical.models.data;

public class BinaryData {
    /** Name of binary system. */
    public String Name;

    /** Period of the orbit. */
    public double Period;

    /** Epoch of the perihelion. */
    public double EpochPeri;

    /** Longitude of the perihelion. */
    public double LongPeri;

    /** Eccentricity of the orbit. */
    public double Ecc;

    /** Semi-major axis of the orbit. */
    public double Axis;

    /** Orbital inclination. */
    public double Incl;

    /** Position angle of the ascending node. */
    public double PANode;

    public BinaryData(String name, double period, double epochPeri, double longPeri, double ecc, double axis,
            double incl, double paNode) {
        this.Name = name;
        this.Period = period;
        this.EpochPeri = epochPeri;
        this.LongPeri = longPeri;
        this.Ecc = ecc;
        this.Axis = axis;
        this.Incl = incl;
        this.PANode = paNode;
    }
}
