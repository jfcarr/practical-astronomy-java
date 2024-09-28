package astro.practical.lib;

import astro.practical.data.CometInfoElliptical;
import astro.practical.models.PositionOfEllipticalComet;
import astro.practical.models.data.CometDataElliptical;

public class PAComet {
    /**
     * Calculate position of an elliptical comet.
     */
    public PositionOfEllipticalComet positionOfEllipticalComet(double lctHour, double lctMin, double lctSec,
            boolean isDaylightSaving, int zoneCorrectionHours, double localDateDay, int localDateMonth,
            int localDateYear, String cometName) {
        CometInfoElliptical cometInfoElliptical = new CometInfoElliptical();

        int daylightSaving = isDaylightSaving ? 1 : 0;

        double greenwichDateDay = PAMacros.localCivilTimeGreenwichDay(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int greenwichDateMonth = PAMacros.localCivilTimeGreenwichMonth(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);
        int greenwichDateYear = PAMacros.localCivilTimeGreenwichYear(lctHour, lctMin, lctSec, daylightSaving,
                zoneCorrectionHours, localDateDay, localDateMonth, localDateYear);

        CometDataElliptical cometInfo = cometInfoElliptical.getCometInfo(cometName);

        double timeSinceEpochYears = (PAMacros.civilDateToJulianDate(greenwichDateDay, greenwichDateMonth,
                greenwichDateYear) - PAMacros.civilDateToJulianDate(0.0, 1, greenwichDateYear)) / 365.242191
                + greenwichDateYear - cometInfo.epoch_EpochOfPerihelion;
        double mcDeg = 360 * timeSinceEpochYears / cometInfo.period_PeriodOfOrbit;
        double mcRad = Math.toRadians(mcDeg - 360 * Math.floor(mcDeg / 360));
        double eccentricity = cometInfo.ecc_EccentricityOfOrbit;
        double trueAnomalyDeg = PAMacros.wToDegrees(PAMacros.trueAnomaly(mcRad, eccentricity));
        double lcDeg = trueAnomalyDeg + cometInfo.peri_LongitudeOfPerihelion;
        double rAU = cometInfo.axis_SemiMajorAxisOfOrbit * (1 - eccentricity * eccentricity)
                / (1 + eccentricity * Math.cos(Math.toRadians(trueAnomalyDeg)));
        double lcNodeRad = Math.toRadians(lcDeg - cometInfo.node_LongitudeOfAscendingNode);
        double psiRad = Math.asin(Math.sin(lcNodeRad) * Math.sin(Math.toRadians(cometInfo.incl_InclinationOfOrbit)));

        double y = Math.sin(lcNodeRad) * Math.cos(Math.toRadians(cometInfo.incl_InclinationOfOrbit));
        double x = Math.cos(lcNodeRad);

        double ldDeg = PAMacros.wToDegrees(Math.atan2(y, x)) + cometInfo.node_LongitudeOfAscendingNode;
        double rdAU = rAU * Math.cos(psiRad);

        double earthLongitudeLeDeg = PAMacros.sunLong(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                localDateDay, localDateMonth, localDateYear) + 180.0;
        double earthRadiusVectorAU = PAMacros.sunDist(lctHour, lctMin, lctSec, daylightSaving, zoneCorrectionHours,
                localDateDay, localDateMonth, localDateYear);

        double leLdRad = Math.toRadians(earthLongitudeLeDeg - ldDeg);
        double aRad = (rdAU < earthRadiusVectorAU)
                ? Math.atan2((rdAU * Math.sin(leLdRad)), (earthRadiusVectorAU - rdAU * Math.cos(leLdRad)))
                : Math.atan2((earthRadiusVectorAU * Math.sin(-leLdRad)),
                        (rdAU - earthRadiusVectorAU * Math.cos(leLdRad)));

        double cometLongDeg1 = (rdAU < earthRadiusVectorAU)
                ? 180.0 + earthLongitudeLeDeg + PAMacros.wToDegrees(aRad)
                : PAMacros.wToDegrees(aRad) + ldDeg;
        double cometLongDeg = cometLongDeg1 - 360 * Math.floor(cometLongDeg1 / 360);
        double cometLatDeg = PAMacros.wToDegrees(Math.atan(rdAU * Math.tan(psiRad)
                * Math.sin(Math.toRadians(cometLongDeg1 - ldDeg)) / (earthRadiusVectorAU * Math.sin(-leLdRad))));
        double cometRAHours1 = PAMacros.decimalDegreesToDegreeHours(PAMacros.ecRA(cometLongDeg, 0, 0, cometLatDeg, 0, 0,
                greenwichDateDay, greenwichDateMonth, greenwichDateYear));
        double cometDecDeg1 = PAMacros.ecDec(cometLongDeg, 0, 0, cometLatDeg, 0, 0, greenwichDateDay,
                greenwichDateMonth, greenwichDateYear);
        double cometDistanceAU = Math.sqrt(Math.pow(earthRadiusVectorAU, 2) + Math.pow(rAU, 2) - 2.0
                * earthRadiusVectorAU * rAU * Math.cos(Math.toRadians(lcDeg - earthLongitudeLeDeg)) * Math.cos(psiRad));

        int cometRAHour = PAMacros.decimalHoursHour(cometRAHours1 + 0.008333);
        int cometRAMin = PAMacros.decimalHoursMinute(cometRAHours1 + 0.008333);
        double cometDecDeg = PAMacros.decimalDegreesDegrees(cometDecDeg1 + 0.008333);
        double cometDecMin = PAMacros.decimalDegreesMinutes(cometDecDeg1 + 0.008333);
        double cometDistEarth = PAUtil.round(cometDistanceAU, 2);

        return new PositionOfEllipticalComet(cometRAHour, cometRAMin, cometDecDeg, cometDecMin, cometDistEarth);
    }
}
