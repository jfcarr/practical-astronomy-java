package astro.practical.types.complex;

import astro.practical.types.RiseSetStatus;

public class SunriseAndSunset {
    public double localSunriseHour;
    public double localSunriseMinute;
    public double localSunsetHour;
    public double localSunsetMinute;
    public double azimuthOfSunriseDeg;
    public double azimuthOfSunsetDeg;
    public RiseSetStatus status;

    public SunriseAndSunset(double localSunriseHour, double localSunriseMinute, double localSunsetHour,
            double localSunsetMinute, double azimuthOfSunriseDeg, double azimuthOfSunsetDeg, RiseSetStatus status) {
        this.localSunriseHour = localSunriseHour;
        this.localSunriseMinute = localSunriseMinute;
        this.localSunsetHour = localSunsetHour;
        this.localSunsetMinute = localSunsetMinute;
        this.azimuthOfSunriseDeg = azimuthOfSunriseDeg;
        this.azimuthOfSunsetDeg = azimuthOfSunsetDeg;
        this.status = status;
    }
}
