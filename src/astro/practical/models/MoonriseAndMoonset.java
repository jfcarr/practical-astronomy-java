package astro.practical.models;

public class MoonriseAndMoonset {
    public double mrLTHour;
    public double mrLTMin;
    public double mrLocalDateDay;
    public int mrLocalDateMonth;
    public int mrLocalDateYear;
    public double mrAzimuthDeg;
    public double msLTHour;
    public double msLTMin;
    public double msLocalDateDay;
    public int msLocalDateMonth;
    public int msLocalDateYear;
    public double msAzimuthDeg;

    public MoonriseAndMoonset(double mrLTHour, double mrLTMin, double mrLocalDateDay, int mrLocalDateMonth,
            int mrLocalDateYear, double mrAzimuthDeg, double msLTHour, double msLTMin, double msLocalDateDay,
            int msLocalDateMonth, int msLocalDateYear, double msAzimuthDeg) {
        this.mrLTHour = mrLTHour;
        this.mrLTMin = mrLTMin;
        this.mrLocalDateDay = mrLocalDateDay;
        this.mrLocalDateMonth = mrLocalDateMonth;
        this.mrLocalDateYear = mrLocalDateYear;
        this.mrAzimuthDeg = mrAzimuthDeg;
        this.msLTHour = msLTHour;
        this.msLTMin = msLTMin;
        this.msLocalDateDay = msLocalDateDay;
        this.msLocalDateMonth = msLocalDateMonth;
        this.msLocalDateYear = msLocalDateYear;
        this.msAzimuthDeg = msAzimuthDeg;
    }
}
