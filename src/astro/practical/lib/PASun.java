package astro.practical.lib;

import astro.practical.types.complex.PositionOfSun;

public class PASun {
        /**
         * Calculate approximate position of the sun for a local date and time.
         */
        public PositionOfSun approximatePositionOfSun(double lctHours, double lctMinutes, double lctSeconds,
                        double localDay, int localMonth, int localYear, boolean isDaylightSaving, int zoneCorrection) {
                int daylightSaving = (isDaylightSaving == true) ? 1 : 0;

                double greenwichDateDay = PAMacros.localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds,
                                daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                int greenwichDateMonth = PAMacros.localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds,
                                daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                int greenwichDateYear = PAMacros.localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds,
                                daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                double utHours = PAMacros.localCivilTimeToUniversalTime(lctHours, lctMinutes, lctSeconds,
                                daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                double utDays = utHours / 24;
                double jdDays = PAMacros.civilDateToJulianDate(greenwichDateDay, greenwichDateMonth, greenwichDateYear)
                                + utDays;
                double dDays = jdDays - PAMacros.civilDateToJulianDate(0, 1, 2010);
                double nDeg = 360 * dDays / 365.242191;
                double mDeg1 = nDeg + PAMacros.sunELong(0, 1, 2010) - PAMacros.sunPeri(0, 1, 2010);
                double mDeg2 = mDeg1 - 360 * Math.floor(mDeg1 / 360);
                double eCDeg = 360 * PAMacros.sunEcc(0, 1, 2010) * Math.sin(Math.toRadians(mDeg2)) / Math.PI;
                double lSDeg1 = nDeg + eCDeg + PAMacros.sunELong(0, 1, 2010);
                double lSDeg2 = lSDeg1 - 360 * Math.floor(lSDeg1 / 360);
                double raDeg = PAMacros.ecRA(lSDeg2, 0, 0, 0, 0, 0, greenwichDateDay, greenwichDateMonth,
                                greenwichDateYear);
                double raHours = PAMacros.decimalDegreesToDegreeHours(raDeg);
                double decDeg = PAMacros.ecDec(lSDeg2, 0, 0, 0, 0, 0, greenwichDateDay, greenwichDateMonth,
                                greenwichDateYear);

                int sunRAHour = PAMacros.decimalHoursHour(raHours);
                int sunRAMin = PAMacros.decimalHoursMinute(raHours);
                double sunRASec = PAMacros.decimalHoursSecond(raHours);
                double sunDecDeg = PAMacros.decimalDegreesDegrees(decDeg);
                double sunDecMin = PAMacros.decimalDegreesMinutes(decDeg);
                double sunDecSec = PAMacros.decimalDegreesSeconds(decDeg);

                return new PositionOfSun(sunRAHour, sunRAMin, sunRASec, sunDecDeg, sunDecMin, sunDecSec);
        }

        /// <summary>
        /// Calculate precise position of the sun for a local date and time.
        /// </summary>
        public PositionOfSun precisePositionOfSun(double lctHours, double lctMinutes, double lctSeconds,
                        double localDay, int localMonth, int localYear, boolean isDaylightSaving, int zoneCorrection) {
                int daylightSaving = (isDaylightSaving == true) ? 1 : 0;

                double gDay = PAMacros.localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                int gMonth = PAMacros.localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                int gYear = PAMacros.localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                double sunEclipticLongitudeDeg = PAMacros.sunLong(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                double raDeg = PAMacros.ecRA(sunEclipticLongitudeDeg, 0, 0, 0, 0, 0, gDay, gMonth, gYear);
                double raHours = PAMacros.decimalDegreesToDegreeHours(raDeg);
                double decDeg = PAMacros.ecDec(sunEclipticLongitudeDeg, 0, 0, 0, 0, 0, gDay, gMonth, gYear);

                int sunRAHour = PAMacros.decimalHoursHour(raHours);
                int sunRAMin = PAMacros.decimalHoursMinute(raHours);
                double sunRASec = PAMacros.decimalHoursSecond(raHours);
                double sunDecDeg = PAMacros.decimalDegreesDegrees(decDeg);
                double sunDecMin = PAMacros.decimalDegreesMinutes(decDeg);
                double sunDecSec = PAMacros.decimalDegreesSeconds(decDeg);

                return new PositionOfSun(sunRAHour, sunRAMin, sunRASec, sunDecDeg, sunDecMin, sunDecSec);
        }

}
