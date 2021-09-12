package astro.practical.containers;

public class Aberration {
	public double apparentEclLongDeg;
	public double apparentEclLongMin;
	public double apparentEclLongSec;
	public double apparentEclLatDeg;
	public double apparentEclLatMin;
	public double apparentEclLatSec;

	public Aberration(double apparentEclLongDeg, double apparentEclLongMin, double apparentEclLongSec,
			double apparentEclLatDeg, double apparentEclLatMin, double apparentEclLatSec) {
		this.apparentEclLongDeg = apparentEclLongDeg;
		this.apparentEclLongMin = apparentEclLongMin;
		this.apparentEclLongSec = apparentEclLongSec;
		this.apparentEclLatDeg = apparentEclLatDeg;
		this.apparentEclLatMin = apparentEclLatMin;
		this.apparentEclLatSec = apparentEclLatSec;
	}
}
