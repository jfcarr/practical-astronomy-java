package astro.practical.lib;

import astro.practical.models.ApproximatePositionOfMoon;
import astro.practical.models.MoonLongLatHP;
import astro.practical.models.PrecisePositionOfMoon;

public class PAMoon {
        /**
         * Calculate approximate position of the Moon.
         */
        public ApproximatePositionOfMoon approximatePositionOfMoon(double lctHour, double lctMin, double lctSec,
                        boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
                        int localDateYear) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double l0 = 91.9293359879052;
                double p0 = 130.143076320618;
                double n0 = 291.682546643194;
                double i = 5.145396;

                double gdateDay = PAMacros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                int gdateMonth = PAMacros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                int gdateYear = PAMacros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

                double utHours = PAMacros.localCivilTimeToUniversalTime(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                double dDays = PAMacros.civilDateToJulianDate(gdateDay, gdateMonth, gdateYear)
                                - PAMacros.civilDateToJulianDate(0.0, 1, 2010) + utHours / 24;
                double sunLongDeg = PAMacros.sunLong(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                                localDateDay,
                                localDateMonth, localDateYear);
                double sunMeanAnomalyRad = PAMacros.sunMeanAnomaly(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours,
                                localDateDay, localDateMonth, localDateYear);
                double lmDeg = PAMacros.unwindDeg(13.1763966 * dDays + l0);
                double mmDeg = PAMacros.unwindDeg(lmDeg - 0.1114041 * dDays - p0);
                double nDeg = PAMacros.unwindDeg(n0 - (0.0529539 * dDays));
                double evDeg = 1.2739 * Math.sin(Math.toRadians(2.0 * (lmDeg - sunLongDeg) - mmDeg));
                double aeDeg = 0.1858 * Math.sin(sunMeanAnomalyRad);
                double a3Deg = 0.37 * Math.sin(sunMeanAnomalyRad);
                double mmdDeg = mmDeg + evDeg - aeDeg - a3Deg;
                double ecDeg = 6.2886 * Math.sin(Math.toRadians(mmdDeg));
                double a4Deg = 0.214 * Math.sin(2.0 * Math.toRadians(mmdDeg));
                double ldDeg = lmDeg + evDeg + ecDeg - aeDeg + a4Deg;
                double vDeg = 0.6583 * Math.sin(2.0 * Math.toRadians(ldDeg - sunLongDeg));
                double lddDeg = ldDeg + vDeg;
                double ndDeg = nDeg - 0.16 * Math.sin(sunMeanAnomalyRad);
                double y = Math.sin(Math.toRadians(lddDeg - ndDeg)) * Math.cos(Math.toRadians(i));
                double x = Math.cos(Math.toRadians(lddDeg - ndDeg));

                double moonLongDeg = PAMacros.unwindDeg(PAMacros.wToDegrees(Math.atan2(y, x)) + ndDeg);
                double moonLatDeg = PAMacros
                                .wToDegrees(Math.asin(Math.sin(Math.toRadians(lddDeg - ndDeg))
                                                * Math.sin(Math.toRadians(i))));
                double moonRAHours1 = PAMacros.decimalDegreesToDegreeHours(
                                PAMacros.ecRA(moonLongDeg, 0, 0, moonLatDeg, 0, 0, gdateDay, gdateMonth, gdateYear));
                double moonDecDeg1 = PAMacros.ecDec(moonLongDeg, 0, 0, moonLatDeg, 0, 0, gdateDay, gdateMonth,
                                gdateYear);

                int moonRAHour = PAMacros.decimalHoursHour(moonRAHours1);
                int moonRAMin = PAMacros.decimalHoursMinute(moonRAHours1);
                double moonRASec = PAMacros.decimalHoursSecond(moonRAHours1);
                double moonDecDeg = PAMacros.decimalDegreesDegrees(moonDecDeg1);
                double moonDecMin = PAMacros.decimalDegreesMinutes(moonDecDeg1);
                double moonDecSec = PAMacros.decimalDegreesSeconds(moonDecDeg1);

                return new ApproximatePositionOfMoon(moonRAHour, moonRAMin, moonRASec, moonDecDeg, moonDecMin,
                                moonDecSec);
        }

        /**
         * Calculate precise position of the Moon.
         */
        public PrecisePositionOfMoon precisePositionOfMoon(double lctHour, double lctMin, double lctSec,
                        boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
                        int localDateYear) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double gdateDay = PAMacros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                int gdateMonth = PAMacros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                int gdateYear = PAMacros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

                MoonLongLatHP moonResult = PAMacros.moonLongLatHP(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

                double nutationInLongitudeDeg = PAMacros.nutatLong(gdateDay, gdateMonth, gdateYear);
                double correctedLongDeg = moonResult.moonLongDeg + nutationInLongitudeDeg;
                double earthMoonDistanceKM = 6378.14 / Math.sin(Math.toRadians(moonResult.moonHorPara));
                double moonRAHours1 = PAMacros.decimalDegreesToDegreeHours(PAMacros.ecRA(correctedLongDeg, 0, 0,
                                moonResult.moonLatDeg, 0, 0, gdateDay, gdateMonth, gdateYear));
                double moonDecDeg1 = PAMacros.ecDec(correctedLongDeg, 0, 0, moonResult.moonLatDeg, 0, 0, gdateDay,
                                gdateMonth, gdateYear);

                int moonRAHour = PAMacros.decimalHoursHour(moonRAHours1);
                int moonRAMin = PAMacros.decimalHoursMinute(moonRAHours1);
                double moonRASec = PAMacros.decimalHoursSecond(moonRAHours1);
                double moonDecDeg = PAMacros.decimalDegreesDegrees(moonDecDeg1);
                double moonDecMin = PAMacros.decimalDegreesMinutes(moonDecDeg1);
                double moonDecSec = PAMacros.decimalDegreesSeconds(moonDecDeg1);
                double earthMoonDistKM = PAUtil.round(earthMoonDistanceKM, 0);
                double moonHorParallaxDeg = PAUtil.round(moonResult.moonHorPara, 6);

                return new PrecisePositionOfMoon(moonRAHour, moonRAMin, moonRASec, moonDecDeg, moonDecMin, moonDecSec,
                                earthMoonDistKM, moonHorParallaxDeg);
        }
}
