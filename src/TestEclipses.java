import astro.practical.lib.PAEclipses;
import astro.practical.models.LunarEclipseCircumstances;
import astro.practical.models.LunarEclipseOccurrenceDetails;
import astro.practical.test.TestLib;
import astro.practical.types.LunarEclipseOccurrence;

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
                .Assert(LunarEclipseOccurrence.LUNAR_ECLIPSE_CERTAIN, lunarEclipseOccurrenceDetails.status)
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
}
