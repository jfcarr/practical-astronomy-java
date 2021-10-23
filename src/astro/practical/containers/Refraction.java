package astro.practical.containers;

public class Refraction {
	public double correctedRAHour;
	public double correctedRAMin;
	public double correctedRASec;
	public double correctedDecDeg;
	public double correctedDecMin;
	public double correctedDecSec;

	public Refraction(double correctedRAHour, double correctedRAMin, double correctedRASec, double correctedDecDeg,
			double correctedDecMin, double correctedDecSec) {
		this.correctedRAHour = correctedRAHour;
		this.correctedRAMin = correctedRAMin;
		this.correctedRASec = correctedRASec;
		this.correctedDecDeg = correctedDecDeg;
		this.correctedDecMin = correctedDecMin;
		this.correctedDecSec = correctedDecSec;
	}
}
