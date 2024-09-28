package astro.practical.data;

import java.util.ArrayList;
import java.util.List;
import astro.practical.models.data.BinaryData;

public class BinaryInfo {
    List<BinaryData> binaryData;

    public BinaryInfo() {
        binaryData = new ArrayList<>() {
            {
                add(new BinaryData("eta-Cor", 41.623, 1934.008, 219.907, 0.2763, 0.907, 59.025, 23.717));
                add(new BinaryData("gamma-Vir", 171.37, 1836.433, 252.88, 0.8808, 3.746, 146.05, 31.78));
                add(new BinaryData("eta-Cas", 480.0, 1889.6, 268.59, 0.497, 11.9939, 34.76, 278.42));
                add(new BinaryData("zeta-Ori", 1508.6, 2070.6, 47.3, 0.07, 2.728, 72.0, 155.5));
                add(new BinaryData("alpha-CMa", 50.09, 1894.13, 147.27, 0.5923, 7.5, 136.53, 44.57));
                add(new BinaryData("delta-Gem", 1200.0, 1437.0, 57.19, 0.11, 6.9753, 63.28, 18.38));
                add(new BinaryData("alpha-Gem", 420.07, 1965.3, 261.43, 0.33, 6.295, 115.94, 40.47));
                add(new BinaryData("aplah-CMi", 40.65, 1927.6, 269.8, 0.4, 4.548, 35.7, 284.3));
                add(new BinaryData("alpha-Cen", 79.92, 1955.56, 231.56, 0.516, 17.583, 79.24, 204.868));
                add(new BinaryData("alpha Sco", 900.0, 1889.0, 0.0, 0.0, 3.21, 86.3, 273.0));
            }
        };
    }

    public BinaryData getBinaryInfo(String name) {
        BinaryData binaryInfo = binaryData.stream()
                .filter(p -> p.Name == name)
                .findFirst()
                .orElse(new BinaryData("NotFound", 0, 0, 0, 0, 0, 0, 0));

        return binaryInfo;
    }
}
