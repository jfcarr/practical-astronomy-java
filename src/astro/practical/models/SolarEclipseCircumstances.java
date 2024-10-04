package astro.practical.models;

public class SolarEclipseCircumstances {
    public double solarEclipseCertainDateDay;
    public int solarEclipseCertainDateMonth;
    public int solarEclipseCertainDateYear;
    public double utFirstContactHour;
    public double utFirstContactMinutes;
    public double utMidEclipseHour;
    public double utMidEclipseMinutes;
    public double utLastContactHour;
    public double utLastContactMinutes;
    public double eclipseMagnitude;

    public SolarEclipseCircumstances(double solarEclipseCertainDateDay, int solarEclipseCertainDateMonth,
            int solarEclipseCertainDateYear, double utFirstContactHour, double utFirstContactMinutes,
            double utMidEclipseHour, double utMidEclipseMinutes, double utLastContactHour, double utLastContactMinutes,
            double eclipseMagnitude) {
        this.solarEclipseCertainDateDay = solarEclipseCertainDateDay;
        this.solarEclipseCertainDateMonth = solarEclipseCertainDateMonth;
        this.solarEclipseCertainDateYear = solarEclipseCertainDateYear;
        this.utFirstContactHour = utFirstContactHour;
        this.utFirstContactMinutes = utFirstContactMinutes;
        this.utMidEclipseHour = utMidEclipseHour;
        this.utMidEclipseMinutes = utMidEclipseMinutes;
        this.utLastContactHour = utLastContactHour;
        this.utLastContactMinutes = utLastContactMinutes;
        this.eclipseMagnitude = eclipseMagnitude;
    }
}
