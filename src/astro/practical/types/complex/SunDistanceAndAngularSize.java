package astro.practical.types.complex;

public class SunDistanceAndAngularSize {
    public double sunDistKm;
    public double sunAngSizeDeg;
    public double sunAngSizeMin;
    public double sunAngSizeSec;

    public SunDistanceAndAngularSize(double sunDistKm, double sunAngSizeDeg, double sunAngSizeMin,
            double sunAngSizeSec) {
        this.sunDistKm = sunDistKm;
        this.sunAngSizeDeg = sunAngSizeDeg;
        this.sunAngSizeMin = sunAngSizeMin;
        this.sunAngSizeSec = sunAngSizeSec;
    }
}
