import astro.practical.lib.PAEclipses;
import astro.practical.models.LunarEclipseCircumstances;
import astro.practical.models.LunarEclipseOccurrenceDetails;
import astro.practical.models.SolarEclipseCircumstances;
import astro.practical.models.SolarEclipseOccurrence;
import astro.practical.test.TestLib;
import astro.practical.types.EclipseOccurrence;

public class TestEclipses {
    PAEclipses paEclipses;
    TestLib testLib;

    public TestEclipses() {
        paEclipses = new PAEclipses();
        testLib = new TestLib();
    }

    public void testLunarEclipseOccurrenceDetails() {
        LunarEclipseOccurrenceDetails lunarEclipseOccurrenceDetails = paEclipses.lunarEclipseOccurrenceDetails(1, 4,
                2015, false, 10);

        testLib.setTestName("Lunar Eclipse Occurrence Details")
                .Assert(EclipseOccurrence.ECLIPSE_CERTAIN, lunarEclipseOccurrenceDetails.status)
                .Assert(4, lunarEclipseOccurrenceDetails.eventDateDay)
                .Assert(4, lunarEclipseOccurrenceDetails.eventDateMonth)
                .Assert(2015, lunarEclipseOccurrenceDetails.eventDateYear);
    }

    public void testLunarEclipseCircumstances() {
        LunarEclipseCircumstances lunarEclipseCircumstances = paEclipses.lunarEclipseCircumstances(1, 4, 2015, false,
                10);

        testLib.setTestName("Lunar Eclipse Circumstances")
                .Assert(4, lunarEclipseCircumstances.lunarEclipseCertainDateDay)
                .Assert(4, lunarEclipseCircumstances.lunarEclipseCertainDateMonth)
                .Assert(2015, lunarEclipseCircumstances.lunarEclipseCertainDateYear)
                .Assert(9, lunarEclipseCircumstances.utStartPenPhaseHour)
                .Assert(0, lunarEclipseCircumstances.utStartPenPhaseMinutes)
                .Assert(10, lunarEclipseCircumstances.utStartUmbralPhaseHour)
                .Assert(16, lunarEclipseCircumstances.utStartUmbralPhaseMinutes)
                .Assert(11, lunarEclipseCircumstances.utStartTotalPhaseHour)
                .Assert(55, lunarEclipseCircumstances.utStartTotalPhaseMinutes)
                .Assert(12, lunarEclipseCircumstances.utMidEclipseHour)
                .Assert(1, lunarEclipseCircumstances.utMidEclipseMinutes)
                .Assert(12, lunarEclipseCircumstances.utEndTotalPhaseHour)
                .Assert(7, lunarEclipseCircumstances.utEndTotalPhaseMinutes)
                .Assert(13, lunarEclipseCircumstances.utEndUmbralPhaseHour)
                .Assert(46, lunarEclipseCircumstances.utEndUmbralPhaseMinutes)
                .Assert(15, lunarEclipseCircumstances.utEndPenPhaseHour)
                .Assert(1, lunarEclipseCircumstances.utEndPenPhaseMinutes)
                .Assert(1.01, lunarEclipseCircumstances.eclipseMagnitude);
    }

    void testSolarEclipseOccurrence() {
        SolarEclipseOccurrence solarEclipseOccurrence = paEclipses.solarEclipseOccurrence(1, 4, 2015, false, 0);

        testLib.setTestName("Solar Eclipse Occurrence")
                .Assert(EclipseOccurrence.ECLIPSE_CERTAIN, solarEclipseOccurrence.status)
                .Assert(20, solarEclipseOccurrence.eventDateDay)
                .Assert(3, solarEclipseOccurrence.eventDateMonth)
                .Assert(2015, solarEclipseOccurrence.eventDateYear);

    }

    void testSolarEclipseCircumstances() {
        SolarEclipseCircumstances solarEclipseCircumstances = paEclipses.solarEclipseCircumstances(20, 3, 2015, false,
                0, 0, 68.65);

        testLib.setTestName("Solar Eclipse Circumstances")
                .Assert(20, solarEclipseCircumstances.solarEclipseCertainDateDay)
                .Assert(3, solarEclipseCircumstances.solarEclipseCertainDateMonth)
                .Assert(2015, solarEclipseCircumstances.solarEclipseCertainDateYear)
                .Assert(8, solarEclipseCircumstances.utFirstContactHour)
                .Assert(55, solarEclipseCircumstances.utFirstContactMinutes)
                .Assert(9, solarEclipseCircumstances.utMidEclipseHour)
                .Assert(57, solarEclipseCircumstances.utMidEclipseMinutes)
                .Assert(10, solarEclipseCircumstances.utLastContactHour)
                .Assert(58, solarEclipseCircumstances.utLastContactMinutes)
                .Assert(1.016, solarEclipseCircumstances.eclipseMagnitude);
    }
}
