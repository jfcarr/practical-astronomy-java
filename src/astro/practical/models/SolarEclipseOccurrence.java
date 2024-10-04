package astro.practical.models;

import astro.practical.types.EclipseOccurrence;

public class SolarEclipseOccurrence {
    public EclipseOccurrence status;
    public double eventDateDay;
    public int eventDateMonth;
    public int eventDateYear;

    public SolarEclipseOccurrence(EclipseOccurrence status, double eventDateDay, int eventDateMonth,
            int eventDateYear) {
        this.status = status;
        this.eventDateDay = eventDateDay;
        this.eventDateMonth = eventDateMonth;
        this.eventDateYear = eventDateYear;
    }
}
