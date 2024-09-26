package astro.practical.lib;

import astro.practical.data.PlanetInfo;
import astro.practical.models.data.PlanetData;
import astro.practical.types.complex.PlanetPosition;

public class PAPlanet {
    /**
     * Calculate approximate position of a planet.
     */
    public PlanetPosition approximatePositionOfPlanet(double lctHour, double lctMin, double lctSec,
            boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
            int localDateYear, String planetName) {
        PlanetInfo planetInfo = new PlanetInfo();

        int daylightSaving = isDaylightSaving ? 1 : 0;

        PlanetData planetData = planetInfo.getPlanetInfo(planetName);

        double gdateDay = PAMacros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateMonth = PAMacros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int gdateYear = PAMacros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

        double utHours = PAMacros.localCivilTimeToUniversalTime(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        double dDays = PAMacros.civilDateToJulianDate(gdateDay + (utHours / 24), gdateMonth, gdateYear)
                - PAMacros.civilDateToJulianDate(0, 1, 2010);
        double npDeg1 = 360 * dDays / (365.242191 * planetData.tp_PeriodOrbit);
        double npDeg2 = npDeg1 - 360 * Math.floor(npDeg1 / 360);
        double mpDeg = npDeg2 + planetData.long_LongitudeEpoch - planetData.peri_LongitudePerihelion;
        double lpDeg1 = npDeg2 + (360 * planetData.ecc_EccentricityOrbit * Math.sin(Math.toRadians(mpDeg)) / Math.PI)
                + planetData.long_LongitudeEpoch;
        double lpDeg2 = lpDeg1 - 360 * Math.floor(lpDeg1 / 360);
        double planetTrueAnomalyDeg = lpDeg2 - planetData.peri_LongitudePerihelion;
        double rAU = planetData.axis_AxisOrbit * (1 - Math.pow(planetData.ecc_EccentricityOrbit, 2))
                / (1 + planetData.ecc_EccentricityOrbit * Math.cos(Math.toRadians(planetTrueAnomalyDeg)));

        PlanetData earthData = planetInfo.getPlanetInfo("Earth");

        double neDeg1 = 360 * dDays / (365.242191 * earthData.tp_PeriodOrbit);
        double neDeg2 = neDeg1 - 360 * Math.floor(neDeg1 / 360);
        double meDeg = neDeg2 + earthData.long_LongitudeEpoch - earthData.peri_LongitudePerihelion;
        double leDeg1 = neDeg2 + earthData.long_LongitudeEpoch
                + 360 * earthData.ecc_EccentricityOrbit * Math.sin(Math.toRadians(meDeg)) / Math.PI;
        double leDeg2 = leDeg1 - 360 * Math.floor(leDeg1 / 360);
        double earthTrueAnomalyDeg = leDeg2 - earthData.peri_LongitudePerihelion;
        double rAU2 = earthData.axis_AxisOrbit * (1 - Math.pow(earthData.ecc_EccentricityOrbit, 2))
                / (1 + earthData.ecc_EccentricityOrbit * Math.cos(Math.toRadians(earthTrueAnomalyDeg)));
        double lpNodeRad = Math.toRadians(lpDeg2 - planetData.node_LongitudeAscendingNode);
        double psiRad = Math.asin(Math.sin(lpNodeRad) * Math.sin(Math.toRadians(planetData.incl_OrbitalInclination)));
        double y = Math.sin(lpNodeRad) * Math.cos(Math.toRadians(planetData.incl_OrbitalInclination));
        double x = Math.cos(lpNodeRad);
        double ldDeg = PAMacros.wToDegrees(Math.atan2(y, x)) + planetData.node_LongitudeAscendingNode;
        double rdAU = rAU * Math.cos(psiRad);
        double leLdRad = Math.toRadians(leDeg2 - ldDeg);
        double atan2Type1 = Math.atan2((rdAU * Math.sin(leLdRad)), (rAU2 - rdAU * Math.cos(leLdRad)));
        double atan2Type2 = Math.atan2((rAU2 * Math.sin(-leLdRad)), (rdAU - rAU2 * Math.cos(leLdRad)));
        double aRad = (rdAU < 1) ? atan2Type1 : atan2Type2;
        double lamdaDeg1 = (rdAU < 1) ? 180 + leDeg2 + PAMacros.wToDegrees(aRad) : PAMacros.wToDegrees(aRad) + ldDeg;
        double lamdaDeg2 = lamdaDeg1 - 360 * Math.floor(lamdaDeg1 / 360);
        double betaDeg = PAMacros.wToDegrees(Math.atan(
                rdAU * Math.tan(psiRad) * Math.sin(Math.toRadians(lamdaDeg2 - ldDeg)) / (rAU2 * Math.sin(-leLdRad))));
        double raHours = PAMacros.decimalDegreesToDegreeHours(
                PAMacros.ecRA(lamdaDeg2, 0, 0, betaDeg, 0, 0, gdateDay, gdateMonth, gdateYear));
        double decDeg = PAMacros.ecDec(lamdaDeg2, 0, 0, betaDeg, 0, 0, gdateDay, gdateMonth, gdateYear);

        int planetRAHour = PAMacros.decimalHoursHour(raHours);
        int planetRAMin = PAMacros.decimalHoursMinute(raHours);
        double planetRASec = PAMacros.decimalHoursSecond(raHours);
        double planetDecDeg = PAMacros.decimalDegreesDegrees(decDeg);
        double planetDecMin = PAMacros.decimalDegreesMinutes(decDeg);
        double planetDecSec = PAMacros.decimalDegreesSeconds(decDeg);

        return new PlanetPosition(planetRAHour, planetRAMin, planetRASec, planetDecDeg, planetDecMin, planetDecSec);
    }
}
