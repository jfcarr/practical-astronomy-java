package astro.practical.types;

public enum TwilightType {
    Civil(6),
    Nautical(12),
    Astronomical(18);

    public final int value;

    private TwilightType(int value) {
        this.value = value;
    }
}
