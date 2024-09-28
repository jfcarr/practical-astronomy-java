package astro.practical.lib;

import astro.practical.data.BinaryInfo;
import astro.practical.models.BinaryStarOrbit;
import astro.practical.models.data.BinaryData;

public class PABinary {
    /**
     * Calculate orbital data for binary star.
     */
    public BinaryStarOrbit binaryStarOrbit(double greenwichDateDay, int greenwichDateMonth, int greenwichDateYear,
            String binaryName) {
        BinaryInfo binaryInfo = new BinaryInfo();

        BinaryData binaryData = binaryInfo.getBinaryInfo(binaryName);

        double yYears = greenwichDateYear
                + (PAMacros.civilDateToJulianDate(greenwichDateDay, greenwichDateMonth, greenwichDateYear)
                        - PAMacros.civilDateToJulianDate(0, 1, greenwichDateYear)) / 365.242191
                - binaryData.EpochPeri;
        double mDeg = 360 * yYears / binaryData.Period;
        double mRad = Math.toRadians(mDeg - 360 * Math.floor(mDeg / 360));
        double eccentricity = binaryData.Ecc;
        double trueAnomalyRad = PAMacros.trueAnomaly(mRad, eccentricity);
        double rArcsec = (1 - eccentricity * Math.cos(PAMacros.eccentricAnomaly(mRad, eccentricity))) * binaryData.Axis;
        double taPeriRad = trueAnomalyRad + Math.toRadians(binaryData.LongPeri);

        double y = Math.sin(taPeriRad) * Math.cos(Math.toRadians(binaryData.Incl));
        double x = Math.cos(taPeriRad);
        double aDeg = PAMacros.wToDegrees(Math.atan2(y, x));
        double thetaDeg1 = aDeg + binaryData.PANode;
        double thetaDeg2 = thetaDeg1 - 360 * Math.floor(thetaDeg1 / 360);
        double rhoArcsec = rArcsec * Math.cos(taPeriRad) / Math.cos(Math.toRadians(thetaDeg2 - binaryData.PANode));

        double positionAngleDeg = PAUtil.round(thetaDeg2, 1);
        double separationArcsec = PAUtil.round(rhoArcsec, 2);

        return new BinaryStarOrbit(positionAngleDeg, separationArcsec);
    }
}
