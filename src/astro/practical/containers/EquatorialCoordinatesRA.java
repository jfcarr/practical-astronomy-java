package astro.practical.containers;

import astro.practical.types.RightAscensionDeclination;

public class EquatorialCoordinatesRA extends RightAscensionDeclination {
	public EquatorialCoordinatesRA(double rightAscensionHours, double rightAscensionMinutes,
			double rightAscensionSeconds, double declinationDegrees, double declinationMinutes,
			double declinationSeconds) {
		super(rightAscensionHours, rightAscensionMinutes, rightAscensionSeconds, declinationDegrees, declinationMinutes,
				declinationSeconds);
	}
}
