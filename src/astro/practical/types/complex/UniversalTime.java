package astro.practical.types.complex;

import astro.practical.types.WarningFlag;

/**
 * Representation of Universal Time (hours, minutes seconds)
 * 
 * Warning flag is optional. (Indicates calculation status)
 */
public class UniversalTime extends Time {
	public WarningFlag warningFlag;

	public UniversalTime(double hours, double minutes, double seconds) {
		super(hours, minutes, seconds);
		this.warningFlag = WarningFlag.OK;
	}

	public UniversalTime(double hours, double minutes, double seconds, WarningFlag paWarningFlag) {
		super(hours, minutes, seconds);
		this.warningFlag = paWarningFlag;
	}
}
