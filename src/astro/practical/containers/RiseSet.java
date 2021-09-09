package astro.practical.containers;

import astro.practical.types.RiseSetStatus;

public class RiseSet {
	public RiseSetStatus riseSetStatus;
	public double utRiseHour;
	public double utRiseMin;
	public double utSetHour;
	public double utSetMin;
	public double azRise;
	public double azSet;

	public RiseSet(RiseSetStatus riseSetStatus, double utRiseHour, double utRiseMin, double utSetHour, double utSetMin,
			double azRise, double azSet) {
		this.riseSetStatus = riseSetStatus;
		this.utRiseHour = utRiseHour;
		this.utRiseMin = utRiseMin;
		this.utSetHour = utSetHour;
		this.utSetMin = utSetMin;
		this.azRise = azRise;
		this.azSet = azSet;
	}
}
