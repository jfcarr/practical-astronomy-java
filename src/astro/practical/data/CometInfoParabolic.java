package astro.practical.data;

import java.util.ArrayList;
import java.util.List;
import astro.practical.models.data.CometDataParabolic;

public class CometInfoParabolic {
    List<CometDataParabolic> cometDataParabolic;

    public CometInfoParabolic() {

        cometDataParabolic = new ArrayList<>() {
            {
                add(new CometDataParabolic("Kohler", 10.5659, 11, 1977, 163.4799, 181.8175, 0.990662, 48.7196));
            }
        };
    }

    public CometDataParabolic getCometInfo(String name) {
        CometDataParabolic cometInfo = cometDataParabolic.stream()
                .filter(p -> p.Name == name)
                .findFirst()
                .orElse(new CometDataParabolic("NotFound", 0, 0, 0, 0, 0, 0, 0));

        return cometInfo;
    }
}
