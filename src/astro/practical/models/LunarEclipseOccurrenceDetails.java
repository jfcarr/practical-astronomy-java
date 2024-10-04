package astro.practical.models;

import astro.practical.types.LunarEclipseOccurrence;

public class LunarEclipseOccurrenceDetails {
    public LunarEclipseOccurrence status;
    public double eventDateDay;
    public int eventDateMonth;
    public int eventDateYear;

    public LunarEclipseOccurrenceDetails(LunarEclipseOccurrence status, double eventDateDay, int eventDateMonth,
            int eventDateYear) {
        this.status = status;
        this.eventDateDay = eventDateDay;
        this.eventDateMonth = eventDateMonth;
        this.eventDateYear = eventDateYear;
    }
}
