package astro.practical.models;

public class VisualAspectsOfAPlanet {
    public double distanceAU;
    public double angDiaArcsec;
    public double phase;
    public double lightTimeHour;
    public double lightTimeMinutes;
    public double lightTimeSeconds;
    public double posAngleBrightLimbDeg;
    public double approximateMagnitude;

    public VisualAspectsOfAPlanet(double distanceAU, double angDiaArcsec, double phase, double lightTimeHour,
            double lightTimeMinutes, double lightTimeSeconds, double posAngleBrightLimbDeg,
            double approximateMagnitude) {
        this.distanceAU = distanceAU;
        this.angDiaArcsec = angDiaArcsec;
        this.phase = phase;
        this.lightTimeHour = lightTimeHour;
        this.lightTimeMinutes = lightTimeMinutes;
        this.lightTimeSeconds = lightTimeSeconds;
        this.posAngleBrightLimbDeg = posAngleBrightLimbDeg;
        this.approximateMagnitude = approximateMagnitude;
    }
}
