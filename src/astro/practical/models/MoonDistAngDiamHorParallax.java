package astro.practical.models;

public class MoonDistAngDiamHorParallax {
    public double earthMoonDist;
    public double angDiameterDeg;
    public double angDiameterMin;
    public double horParallaxDeg;
    public double horParallaxMin;
    public double horParallaxSec;

    public MoonDistAngDiamHorParallax(double earthMoonDist, double angDiameterDeg, double angDiameterMin,
            double horParallaxDeg, double horParallaxMin, double horParallaxSec) {
        this.earthMoonDist = earthMoonDist;
        this.angDiameterDeg = angDiameterDeg;
        this.angDiameterMin = angDiameterMin;
        this.horParallaxDeg = horParallaxDeg;
        this.horParallaxMin = horParallaxMin;
        this.horParallaxSec = horParallaxSec;
    }
}
