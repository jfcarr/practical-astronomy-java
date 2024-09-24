package astro.practical.types;

public enum TwilightType {
    CIVIL(6),
    NAUTICAL(12),
    ASTRONOMICAL(18);

    public final int value;

    private TwilightType(int value) {
        this.value = value;
    }
}
