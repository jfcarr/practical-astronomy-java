package astro.practical.lib;

import astro.practical.models.LunarEclipseCircumstances;
import astro.practical.models.LunarEclipseOccurrenceDetails;
import astro.practical.models.SolarEclipseCircumstances;
import astro.practical.models.SolarEclipseOccurrence;
import astro.practical.types.EclipseOccurrence;

public class PAEclipses {
        /** Determine if a lunar eclipse is likely to occur. */
        public LunarEclipseOccurrenceDetails lunarEclipseOccurrenceDetails(double localDateDay, int localDateMonth,
                        int localDateYear, boolean isDaylightSaving, int zoneCorrectionHours) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double julianDateOfFullMoon = PAMacros.fullMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                                localDateMonth, localDateYear);

                double gDateOfFullMoonDay = PAMacros.julianDateDay(julianDateOfFullMoon);
                double integerDay = Math.floor(gDateOfFullMoonDay);
                int gDateOfFullMoonMonth = PAMacros.julianDateMonth(julianDateOfFullMoon);
                int gDateOfFullMoonYear = PAMacros.julianDateYear(julianDateOfFullMoon);
                double utOfFullMoonHours = gDateOfFullMoonDay - integerDay;

                double localCivilDateDay = PAMacros.universalTimeLocalCivilDay(utOfFullMoonHours, 0.0, 0.0,
                                daylightSaving,
                                zoneCorrectionHours, integerDay, gDateOfFullMoonMonth, gDateOfFullMoonYear);
                int localCivilDateMonth = PAMacros.universalTimeLocalCivilMonth(utOfFullMoonHours, 0.0, 0.0,
                                daylightSaving,
                                zoneCorrectionHours, integerDay, gDateOfFullMoonMonth, gDateOfFullMoonYear);
                int localCivilDateYear = PAMacros.universalTimeLocalCivilYear(utOfFullMoonHours, 0.0, 0.0,
                                daylightSaving,
                                zoneCorrectionHours, integerDay, gDateOfFullMoonMonth, gDateOfFullMoonYear);

                EclipseOccurrence eclipseOccurrence = PAMacros.lunarEclipseOccurrence(daylightSaving,
                                zoneCorrectionHours,
                                localDateDay, localDateMonth, localDateYear);

                EclipseOccurrence status = eclipseOccurrence;
                double eventDateDay = localCivilDateDay;
                int eventDateMonth = localCivilDateMonth;
                int eventDateYear = localCivilDateYear;

                return new LunarEclipseOccurrenceDetails(status, eventDateDay, eventDateMonth, eventDateYear);
        }

        /**
         * Calculate the circumstances of a lunar eclipse.
         */
        public LunarEclipseCircumstances lunarEclipseCircumstances(double localDateDay, int localDateMonth,
                        int localDateYear, boolean isDaylightSaving, int zoneCorrectionHours) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double julianDateOfFullMoon = PAMacros.fullMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                                localDateMonth, localDateYear);
                double gDateOfFullMoonDay = PAMacros.julianDateDay(julianDateOfFullMoon);
                double integerDay = Math.floor(gDateOfFullMoonDay);
                int gDateOfFullMoonMonth = PAMacros.julianDateMonth(julianDateOfFullMoon);
                int gDateOfFullMoonYear = PAMacros.julianDateYear(julianDateOfFullMoon);
                double utOfFullMoonHours = gDateOfFullMoonDay - integerDay;

                double localCivilDateDay = PAMacros.universalTimeLocalCivilDay(utOfFullMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfFullMoonMonth,
                                gDateOfFullMoonYear);
                int localCivilDateMonth = PAMacros.universalTimeLocalCivilMonth(utOfFullMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfFullMoonMonth,
                                gDateOfFullMoonYear);
                int localCivilDateYear = PAMacros.universalTimeLocalCivilYear(utOfFullMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfFullMoonMonth,
                                gDateOfFullMoonYear);

                double utMaxEclipse = PAMacros.utMaxLunarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours);
                double utFirstContact = PAMacros.utFirstContactLunarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours);
                double utLastContact = PAMacros.utLastContactLunarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours);
                double utStartUmbralPhase = PAMacros.utStartUmbraLunarEclipse(localDateDay, localDateMonth,
                                localDateYear, daylightSaving, zoneCorrectionHours);
                double utEndUmbralPhase = PAMacros.utEndUmbraLunarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours);
                double utStartTotalPhase = PAMacros.utStartTotalLunarEclipse(localDateDay, localDateMonth,
                                localDateYear, daylightSaving, zoneCorrectionHours);
                double utEndTotalPhase = PAMacros.utEndTotalLunarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours);

                double eclipseMagnitude1 = PAMacros.magLunarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours);

                double lunarEclipseCertainDateDay = localCivilDateDay;
                int lunarEclipseCertainDateMonth = localCivilDateMonth;
                int lunarEclipseCertainDateYear = localCivilDateYear;

                double utStartPenPhaseHour = (utFirstContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utFirstContact + 0.008333);
                double utStartPenPhaseMinutes = (utFirstContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utFirstContact + 0.008333);

                double utStartUmbralPhaseHour = (utStartUmbralPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utStartUmbralPhase + 0.008333);
                double utStartUmbralPhaseMinutes = (utStartUmbralPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utStartUmbralPhase + 0.008333);

                double utStartTotalPhaseHour = (utStartTotalPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utStartTotalPhase + 0.008333);
                double utStartTotalPhaseMinutes = (utStartTotalPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utStartTotalPhase + 0.008333);

                double utMidEclipseHour = (utMaxEclipse == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utMaxEclipse + 0.008333);
                double utMidEclipseMinutes = (utMaxEclipse == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utMaxEclipse + 0.008333);

                double utEndTotalPhaseHour = (utEndTotalPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utEndTotalPhase + 0.008333);
                double utEndTotalPhaseMinutes = (utEndTotalPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utEndTotalPhase + 0.008333);

                double utEndUmbralPhaseHour = (utEndUmbralPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utEndUmbralPhase + 0.008333);
                double utEndUmbralPhaseMinutes = (utEndUmbralPhase == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utEndUmbralPhase + 0.008333);

                double utEndPenPhaseHour = (utLastContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utLastContact + 0.008333);
                double utEndPenPhaseMinutes = (utLastContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utLastContact + 0.008333);

                double eclipseMagnitude = (eclipseMagnitude1 == -99.0) ? -99.0 : PAUtil.round(eclipseMagnitude1, 2);

                return new LunarEclipseCircumstances(lunarEclipseCertainDateDay, lunarEclipseCertainDateMonth,
                                lunarEclipseCertainDateYear, utStartPenPhaseHour, utStartPenPhaseMinutes,
                                utStartUmbralPhaseHour, utStartUmbralPhaseMinutes, utStartTotalPhaseHour,
                                utStartTotalPhaseMinutes, utMidEclipseHour, utMidEclipseMinutes, utEndTotalPhaseHour,
                                utEndTotalPhaseMinutes, utEndUmbralPhaseHour, utEndUmbralPhaseMinutes,
                                utEndPenPhaseHour, utEndPenPhaseMinutes, eclipseMagnitude);
        }

        /** Determine if a solar eclipse is likely to occur. */
        public SolarEclipseOccurrence solarEclipseOccurrence(double localDateDay, int localDateMonth, int localDateYear,
                        boolean isDaylightSaving, int zoneCorrectionHours) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double julianDateOfNewMoon = PAMacros.newMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                                localDateMonth, localDateYear);
                double gDateOfNewMoonDay = PAMacros.julianDateDay(julianDateOfNewMoon);
                double integerDay = Math.floor(gDateOfNewMoonDay);
                int gDateOfNewMoonMonth = PAMacros.julianDateMonth(julianDateOfNewMoon);
                int gDateOfNewMoonYear = PAMacros.julianDateYear(julianDateOfNewMoon);
                double utOfNewMoonHours = gDateOfNewMoonDay - integerDay;

                double localCivilDateDay = PAMacros.universalTimeLocalCivilDay(utOfNewMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                                gDateOfNewMoonYear);
                int localCivilDateMonth = PAMacros.universalTimeLocalCivilMonth(utOfNewMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                                gDateOfNewMoonYear);
                int localCivilDateYear = PAMacros.universalTimeLocalCivilYear(utOfNewMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                                gDateOfNewMoonYear);

                EclipseOccurrence eclipseOccurrence = PAMacros.solarEclipseOccurrence(daylightSaving,
                                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
                EclipseOccurrence status = eclipseOccurrence;
                double eventDateDay = localCivilDateDay;
                int eventDateMonth = localCivilDateMonth;
                int eventDateYear = localCivilDateYear;

                return new SolarEclipseOccurrence(status, eventDateDay, eventDateMonth, eventDateYear);
        }

        /** Calculate the circumstances of a solar eclipse. */
        public SolarEclipseCircumstances solarEclipseCircumstances(double localDateDay, int localDateMonth,
                        int localDateYear, boolean isDaylightSaving, int zoneCorrectionHours, double geogLongitudeDeg,
                        double geogLatitudeDeg) {
                int daylightSaving = isDaylightSaving ? 1 : 0;

                double julianDateOfNewMoon = PAMacros.newMoon(daylightSaving, zoneCorrectionHours, localDateDay,
                                localDateMonth, localDateYear);
                double gDateOfNewMoonDay = PAMacros.julianDateDay(julianDateOfNewMoon);
                double integerDay = Math.floor(gDateOfNewMoonDay);
                int gDateOfNewMoonMonth = PAMacros.julianDateMonth(julianDateOfNewMoon);
                int gDateOfNewMoonYear = PAMacros.julianDateYear(julianDateOfNewMoon);
                double utOfNewMoonHours = gDateOfNewMoonDay - integerDay;
                double localCivilDateDay = PAMacros.universalTimeLocalCivilDay(utOfNewMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                                gDateOfNewMoonYear);
                int localCivilDateMonth = PAMacros.universalTimeLocalCivilMonth(utOfNewMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                                gDateOfNewMoonYear);
                int localCivilDateYear = PAMacros.universalTimeLocalCivilYear(utOfNewMoonHours, 0.0, 0.0,
                                daylightSaving, zoneCorrectionHours, integerDay, gDateOfNewMoonMonth,
                                gDateOfNewMoonYear);

                double utMaxEclipse = PAMacros.utMaxSolarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);
                double utFirstContact = PAMacros.utFirstContactSolarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);
                double utLastContact = PAMacros.utLastContactSolarEclipse(localDateDay, localDateMonth, localDateYear,
                                daylightSaving, zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);
                double magnitude = PAMacros.magSolarEclipse(localDateDay, localDateMonth, localDateYear, daylightSaving,
                                zoneCorrectionHours, geogLongitudeDeg, geogLatitudeDeg);

                double solarEclipseCertainDateDay = localCivilDateDay;
                int solarEclipseCertainDateMonth = localCivilDateMonth;
                int solarEclipseCertainDateYear = localCivilDateYear;

                double utFirstContactHour = (utFirstContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utFirstContact + 0.008333);
                double utFirstContactMinutes = (utFirstContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utFirstContact + 0.008333);

                double utMidEclipseHour = (utMaxEclipse == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utMaxEclipse + 0.008333);
                double utMidEclipseMinutes = (utMaxEclipse == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utMaxEclipse + 0.008333);

                double utLastContactHour = (utLastContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursHour(utLastContact + 0.008333);
                double utLastContactMinutes = (utLastContact == -99.0) ? -99.0
                                : PAMacros.decimalHoursMinute(utLastContact + 0.008333);

                double eclipseMagnitude = (magnitude == -99.0) ? -99.0 : PAUtil.round(magnitude, 3);

                return new SolarEclipseCircumstances(solarEclipseCertainDateDay, solarEclipseCertainDateMonth,
                                solarEclipseCertainDateYear, utFirstContactHour, utFirstContactMinutes,
                                utMidEclipseHour, utMidEclipseMinutes, utLastContactHour, utLastContactMinutes,
                                eclipseMagnitude);
        }
}
