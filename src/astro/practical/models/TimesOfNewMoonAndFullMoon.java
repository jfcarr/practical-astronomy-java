package astro.practical.models;

public class TimesOfNewMoonAndFullMoon {
    public double nmLocalTimeHour;
    public double nmLocalTimeMin;
    public double nmLocalDateDay;
    public int nmLocalDateMonth;
    public int nmLocalDateYear;
    public double fmLocalTimeHour;
    public double fmLocalTimeMin;
    public double fmLocalDateDay;
    public int fmLocalDateMonth;
    public int fmLocalDateYear;

    public TimesOfNewMoonAndFullMoon(double nmLocalTimeHour, double nmLocalTimeMin, double nmLocalDateDay,
            int nmLocalDateMonth, int nmLocalDateYear, double fmLocalTimeHour, double fmLocalTimeMin,
            double fmLocalDateDay, int fmLocalDateMonth, int fmLocalDateYear) {
        this.nmLocalTimeHour = nmLocalTimeHour;
        this.nmLocalTimeMin = nmLocalTimeMin;
        this.nmLocalDateDay = nmLocalDateDay;
        this.nmLocalDateMonth = nmLocalDateMonth;
        this.nmLocalDateYear = nmLocalDateYear;
        this.fmLocalTimeHour = fmLocalTimeHour;
        this.fmLocalTimeMin = fmLocalTimeMin;
        this.fmLocalDateDay = fmLocalDateDay;
        this.fmLocalDateMonth = fmLocalDateMonth;
        this.fmLocalDateYear = fmLocalDateYear;
    }
}
