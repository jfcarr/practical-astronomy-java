package astro.practical.lib;

import astro.practical.types.RiseSetStatus;
import astro.practical.types.complex.PositionOfSun;
import astro.practical.types.complex.SunDistanceAndAngularSize;
import astro.practical.types.complex.SunriseAndSunset;

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

        /**
         * Calculate distance to the Sun (in km), and angular size.
         */
        public SunDistanceAndAngularSize sunDistanceAndAngularSize(double lctHours, double lctMinutes,
                        double lctSeconds, double localDay, int localMonth, int localYear, boolean isDaylightSaving,
                        int zoneCorrection) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double gDay = PAMacros.localCivilTimeGreenwichDay(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                int gMonth = PAMacros.localCivilTimeGreenwichMonth(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                int gYear = PAMacros.localCivilTimeGreenwichYear(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                double trueAnomalyDeg = PAMacros.sunTrueAnomaly(lctHours, lctMinutes, lctSeconds, daylightSaving,
                                zoneCorrection, localDay, localMonth, localYear);
                double trueAnomalyRad = Math.toRadians(trueAnomalyDeg);
                double eccentricity = PAMacros.sunEcc(gDay, gMonth, gYear);
                double f = (1 + eccentricity * Math.cos(trueAnomalyRad)) / (1 - eccentricity * eccentricity);
                double rKm = 149598500 / f;
                double thetaDeg = f * 0.533128;

                double sunDistKm = PAUtil.round(rKm, 0);
                double sunAngSizeDeg = PAMacros.decimalDegreesDegrees(thetaDeg);
                double sunAngSizeMin = PAMacros.decimalDegreesMinutes(thetaDeg);
                double sunAngSizeSec = PAMacros.decimalDegreesSeconds(thetaDeg);

                return new SunDistanceAndAngularSize(sunDistKm, sunAngSizeDeg, sunAngSizeMin, sunAngSizeSec);
        }

        /**
         * Calculate local sunrise and sunset.
         */
        public SunriseAndSunset sunriseAndSunset(double localDay, int localMonth, int localYear,
                        boolean isDaylightSaving, int zoneCorrection, double geographicalLongDeg,
                        double geographicalLatDeg) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double localSunriseHours = PAMacros.sunriseLCT(localDay, localMonth, localYear, daylightSaving,
                                zoneCorrection, geographicalLongDeg, geographicalLatDeg);
                double localSunsetHours = PAMacros.sunsetLCT(localDay, localMonth, localYear, daylightSaving,
                                zoneCorrection, geographicalLongDeg, geographicalLatDeg);

                RiseSetStatus sunRiseSetStatus = PAMacros.eSunRS(localDay, localMonth, localYear, daylightSaving,
                                zoneCorrection, geographicalLongDeg, geographicalLatDeg);

                double adjustedSunriseHours = localSunriseHours + 0.008333;
                double adjustedSunsetHours = localSunsetHours + 0.008333;

                double azimuthOfSunriseDeg1 = PAMacros.sunriseAZ(localDay, localMonth, localYear, daylightSaving,
                                zoneCorrection, geographicalLongDeg, geographicalLatDeg);
                double azimuthOfSunsetDeg1 = PAMacros.sunsetAZ(localDay, localMonth, localYear, daylightSaving,
                                zoneCorrection, geographicalLongDeg, geographicalLatDeg);

                int localSunriseHour = sunRiseSetStatus == RiseSetStatus.OK
                                ? PAMacros.decimalHoursHour(adjustedSunriseHours)
                                : 0;
                int localSunriseMinute = sunRiseSetStatus == RiseSetStatus.OK
                                ? PAMacros.decimalHoursMinute(adjustedSunriseHours)
                                : 0;

                int localSunsetHour = sunRiseSetStatus == RiseSetStatus.OK
                                ? PAMacros.decimalHoursHour(adjustedSunsetHours)
                                : 0;
                int localSunsetMinute = sunRiseSetStatus == RiseSetStatus.OK
                                ? PAMacros.decimalHoursMinute(adjustedSunsetHours)
                                : 0;

                double azimuthOfSunriseDeg = sunRiseSetStatus == RiseSetStatus.OK
                                ? PAUtil.round(azimuthOfSunriseDeg1, 2)
                                : 0;
                double azimuthOfSunsetDeg = sunRiseSetStatus == RiseSetStatus.OK
                                ? PAUtil.round(azimuthOfSunsetDeg1, 2)
                                : 0;

                RiseSetStatus status = sunRiseSetStatus;

                return new SunriseAndSunset(localSunriseHour, localSunriseMinute, localSunsetHour, localSunsetMinute,
                                azimuthOfSunriseDeg, azimuthOfSunsetDeg, status);
        }
}