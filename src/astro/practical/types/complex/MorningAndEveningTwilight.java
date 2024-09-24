package astro.practical.types.complex;

import astro.practical.types.TwilightStatus;

public class MorningAndEveningTwilight {
    public double amTwilightBeginsHour;
    public double amTwilightBeginsMin;
    public double pmTwilightEndsHour;
    public double pmTwilightEndsMin;
    public TwilightStatus status;

    public MorningAndEveningTwilight(double amTwilightBeginsHour, double amTwilightBeginsMin, double pmTwilightEndsHour,
            double pmTwilightEndsMin, TwilightStatus status) {
        this.amTwilightBeginsHour = amTwilightBeginsHour;
        this.amTwilightBeginsMin = amTwilightBeginsMin;
        this.pmTwilightEndsHour = pmTwilightEndsHour;
        this.pmTwilightEndsMin = pmTwilightEndsMin;
        this.status = status;
    }
}
