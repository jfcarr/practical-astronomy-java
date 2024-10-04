package astro.practical.models;

public class LunarEclipseCircumstances {
    public double lunarEclipseCertainDateDay;
    public double lunarEclipseCertainDateMonth;
    public double lunarEclipseCertainDateYear;
    public double utStartPenPhaseHour;
    public double utStartPenPhaseMinutes;
    public double utStartUmbralPhaseHour;
    public double utStartUmbralPhaseMinutes;
    public double utStartTotalPhaseHour;
    public double utStartTotalPhaseMinutes;
    public double utMidEclipseHour;
    public double utMidEclipseMinutes;
    public double utEndTotalPhaseHour;
    public double utEndTotalPhaseMinutes;
    public double utEndUmbralPhaseHour;
    public double utEndUmbralPhaseMinutes;
    public double utEndPenPhaseHour;
    public double utEndPenPhaseMinutes;
    public double eclipseMagnitude;

    public LunarEclipseCircumstances(double lunarEclipseCertainDateDay, double lunarEclipseCertainDateMonth,
            double lunarEclipseCertainDateYear, double utStartPenPhaseHour, double utStartPenPhaseMinutes,
            double utStartUmbralPhaseHour, double utStartUmbralPhaseMinutes, double utStartTotalPhaseHour,
            double utStartTotalPhaseMinutes, double utMidEclipseHour, double utMidEclipseMinutes,
            double utEndTotalPhaseHour, double utEndTotalPhaseMinutes, double utEndUmbralPhaseHour,
            double utEndUmbralPhaseMinutes, double utEndPenPhaseHour, double utEndPenPhaseMinutes,
            double eclipseMagnitude) {
        this.lunarEclipseCertainDateDay = lunarEclipseCertainDateDay;
        this.lunarEclipseCertainDateMonth = lunarEclipseCertainDateMonth;
        this.lunarEclipseCertainDateYear = lunarEclipseCertainDateYear;
        this.utStartPenPhaseHour = utStartPenPhaseHour;
        this.utStartPenPhaseMinutes = utStartPenPhaseMinutes;
        this.utStartUmbralPhaseHour = utStartUmbralPhaseHour;
        this.utStartUmbralPhaseMinutes = utStartUmbralPhaseMinutes;
        this.utStartTotalPhaseHour = utStartTotalPhaseHour;
        this.utStartTotalPhaseMinutes = utStartTotalPhaseMinutes;
        this.utMidEclipseHour = utMidEclipseHour;
        this.utMidEclipseMinutes = utMidEclipseMinutes;
        this.utEndTotalPhaseHour = utEndTotalPhaseHour;
        this.utEndTotalPhaseMinutes = utEndTotalPhaseMinutes;
        this.utEndUmbralPhaseHour = utEndUmbralPhaseHour;
        this.utEndUmbralPhaseMinutes = utEndUmbralPhaseMinutes;
        this.utEndPenPhaseHour = utEndPenPhaseHour;
        this.utEndPenPhaseMinutes = utEndPenPhaseMinutes;
        this.eclipseMagnitude = eclipseMagnitude;
    }
}
