package astro.practical.lib;

import astro.practical.models.ApproximatePositionOfMoon;
import astro.practical.models.MoonDistAngDiamHorParallax;
import astro.practical.models.MoonLongLatHP;
import astro.practical.models.MoonPhase;
import astro.practical.models.PrecisePositionOfMoon;
import astro.practical.models.TimesOfNewMoonAndFullMoon;
import astro.practical.types.AccuracyLevel;

public class PAMoon {
        /** Calculate approximate position of the Moon. */
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

        /** Calculate precise position of the Moon. */
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

        /** Calculate Moon phase and position angle of bright limb. */
        public MoonPhase moonPhase(double lctHour, double lctMin, double lctSec, boolean isDaylightSaving,
                        int zoneCorrectionHours, double localDateDay, int localDateMonth, int localDateYear,
                        AccuracyLevel accuracyLevel) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double gdateDay = PAMacros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                int gdateMonth = PAMacros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                int gdateYear = PAMacros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

                double sunLongDeg = PAMacros.sunLong(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                                localDateDay, localDateMonth, localDateYear);
                MoonLongLatHP moonResult = PAMacros.moonLongLatHP(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                double dRad = Math.toRadians(moonResult.moonLongDeg - sunLongDeg);

                double moonPhase1 = (accuracyLevel == AccuracyLevel.PRECISE)
                                ? PAMacros.moonPhase(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                                                localDateDay, localDateMonth, localDateYear)
                                : (1.0 - Math.cos(dRad)) / 2.0;

                double sunRARad = Math
                                .toRadians(PAMacros.ecRA(sunLongDeg, 0, 0, 0, 0, 0, gdateDay, gdateMonth, gdateYear));
                double moonRARad = Math.toRadians(PAMacros.ecRA(moonResult.moonLongDeg, 0, 0, moonResult.moonLatDeg, 0,
                                0, gdateDay, gdateMonth, gdateYear));
                double sunDecRad = Math
                                .toRadians(PAMacros.ecDec(sunLongDeg, 0, 0, 0, 0, 0, gdateDay, gdateMonth, gdateYear));
                double moonDecRad = Math.toRadians(
                                PAMacros.ecDec(moonResult.moonLongDeg, 0, 0, moonResult.moonLatDeg, 0, 0, gdateDay,
                                                gdateMonth, gdateYear));

                double y = Math.cos(sunDecRad) * Math.sin(sunRARad - moonRARad);
                double x = Math.cos(moonDecRad) * Math.sin(sunDecRad)
                                - Math.sin(moonDecRad) * Math.cos(sunDecRad) * Math.cos(sunRARad - moonRARad);

                double chiDeg = PAMacros.wToDegrees(Math.atan2(y, x));

                double moonPhase = PAUtil.round(moonPhase1, 2);
                double paBrightLimbDeg = PAUtil.round(chiDeg, 2);

                return new MoonPhase(moonPhase, paBrightLimbDeg);
        }

        /** Calculate new moon and full moon instances. */
        public TimesOfNewMoonAndFullMoon timesOfNewMoonAndFullMoon(boolean isDaylightSaving, int zoneCorrectionHours,
                        double localDateDay, int localDateMonth, int localDateYear) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double jdOfNewMoonDays = PAMacros.newMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                                localDateMonth, localDateYear);
                double jdOfFullMoonDays = PAMacros.fullMoon(3, zoneCorrectionHours, localDateDay, localDateMonth,
                                localDateYear);

                double gDateOfNewMoonDay = PAMacros.julianDateDay(jdOfNewMoonDays);
                double integerDay1 = Math.floor(gDateOfNewMoonDay);
                int gDateOfNewMoonMonth = PAMacros.julianDateMonth(jdOfNewMoonDays);
                int gDateOfNewMoonYear = PAMacros.julianDateYear(jdOfNewMoonDays);

                double gDateOfFullMoonDay = PAMacros.julianDateDay(jdOfFullMoonDays);
                double integerDay2 = Math.floor(gDateOfFullMoonDay);
                int gDateOfFullMoonMonth = PAMacros.julianDateMonth(jdOfFullMoonDays);
                int gDateOfFullMoonYear = PAMacros.julianDateYear(jdOfFullMoonDays);

                double utOfNewMoonHours = 24.0 * (gDateOfNewMoonDay - integerDay1);
                double utOfFullMoonHours = 24.0 * (gDateOfFullMoonDay - integerDay2);
                double lctOfNewMoonHours = PAMacros.universalTimeToLocalCivilTime(utOfNewMoonHours + 0.008333, 0, 0,
                                daylightSaving, zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth,
                                gDateOfNewMoonYear);
                double lctOfFullMoonHours = PAMacros.universalTimeToLocalCivilTime(utOfFullMoonHours + 0.008333, 0, 0,
                                daylightSaving, zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth,
                                gDateOfFullMoonYear);

                int nmLocalTimeHour = PAMacros.decimalHoursHour(lctOfNewMoonHours);
                int nmLocalTimeMin = PAMacros.decimalHoursMinute(lctOfNewMoonHours);
                double nmLocalDateDay = PAMacros.universalTimeLocalCivilDay(utOfNewMoonHours, 0, 0, daylightSaving,
                                zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth, gDateOfNewMoonYear);
                int nmLocalDateMonth = PAMacros.universalTimeLocalCivilMonth(utOfNewMoonHours, 0, 0, daylightSaving,
                                zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth, gDateOfNewMoonYear);
                int nmLocalDateYear = PAMacros.universalTimeLocalCivilYear(utOfNewMoonHours, 0, 0, daylightSaving,
                                zoneCorrectionHours, integerDay1, gDateOfNewMoonMonth, gDateOfNewMoonYear);
                int fmLocalTimeHour = PAMacros.decimalHoursHour(lctOfFullMoonHours);
                int fmLocalTimeMin = PAMacros.decimalHoursMinute(lctOfFullMoonHours);
                double fmLocalDateDay = PAMacros.universalTimeLocalCivilDay(utOfFullMoonHours, 0, 0, daylightSaving,
                                zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth, gDateOfFullMoonYear);
                int fmLocalDateMonth = PAMacros.universalTimeLocalCivilMonth(utOfFullMoonHours, 0, 0, daylightSaving,
                                zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth, gDateOfFullMoonYear);
                int fmLocalDateYear = PAMacros.universalTimeLocalCivilYear(utOfFullMoonHours, 0, 0, daylightSaving,
                                zoneCorrectionHours, integerDay2, gDateOfFullMoonMonth, gDateOfFullMoonYear);

                return new TimesOfNewMoonAndFullMoon(nmLocalTimeHour, nmLocalTimeMin, nmLocalDateDay, nmLocalDateMonth,
                                nmLocalDateYear, fmLocalTimeHour, fmLocalTimeMin, fmLocalDateDay, fmLocalDateMonth,
                                fmLocalDateYear);
        }

        /** Calculate Moon's distance, angular diameter, and horizontal parallax. */
        public MoonDistAngDiamHorParallax moonDistAngDiamHorParallax(double lctHour, double lctMin, double lctSec,
                        boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
                        int localDateYear) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double moonDistance = PAMacros.moonDist(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                                localDateDay, localDateMonth, localDateYear);
                double moonAngularDiameter = PAMacros.moonSize(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                double moonHorizontalParallax = PAMacros.moonHP(lctHour, lctMin, lctSec, daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

                double earthMoonDist = PAUtil.round(moonDistance, 0);
                double angDiameterDeg = PAMacros.decimalDegreesDegrees(moonAngularDiameter + 0.008333);
                double angDiameterMin = PAMacros.decimalDegreesMinutes(moonAngularDiameter + 0.008333);
                double horParallaxDeg = PAMacros.decimalDegreesDegrees(moonHorizontalParallax);
                double horParallaxMin = PAMacros.decimalDegreesMinutes(moonHorizontalParallax);
                double horParallaxSec = PAMacros.decimalDegreesSeconds(moonHorizontalParallax);

                return new MoonDistAngDiamHorParallax(earthMoonDist, angDiameterDeg, angDiameterMin, horParallaxDeg,
                                horParallaxMin, horParallaxSec);
        }
}
