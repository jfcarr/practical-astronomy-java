package astro.practical.models.data;

public class CometDataParabolic {
    /** Name of comet */
    public String Name;

    /** Epoch perihelion day */
    public double EpochPeriDay;

    /** Epoch perihelion month */
    public int EpochPeriMonth;

    /** Epoch perihelion year */
    public int EpochPeriYear;

    /** Arg perihelion */
    public double ArgPeri;

    /** Comet's node */
    public double Node;

    /** Distance at the perihelion */
    public double PeriDist;

    /** Inclination */
    public double Incl;

    public CometDataParabolic(String name, double epochPeriDay, int epochPeriMonth, int epochPeriYear, double argPeri,
            double node, double periDist, double incl) {
        this.Name = name;
        this.EpochPeriDay = epochPeriDay;
        this.EpochPeriMonth = epochPeriMonth;
        this.EpochPeriYear = epochPeriYear;
        this.ArgPeri = argPeri;
        this.Node = node;
        this.PeriDist = periDist;
        this.Incl = incl;
    }
}
