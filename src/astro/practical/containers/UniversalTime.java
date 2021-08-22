package astro.practical.containers;

import astro.practical.types.Time;
import astro.practical.types.PAWarningFlag;

public class UniversalTime extends Time {
	public PAWarningFlag warningFlag;

	public UniversalTime(double hours, double minutes, double seconds) {
		super(hours, minutes, seconds);
		this.warningFlag = PAWarningFlag.OK;
	}

	public UniversalTime(double hours, double minutes, double seconds, PAWarningFlag paWarningFlag) {
		super(hours, minutes, seconds);
		this.warningFlag = paWarningFlag;
	}
}
