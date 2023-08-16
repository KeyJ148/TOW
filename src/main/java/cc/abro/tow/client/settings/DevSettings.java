package cc.abro.tow.client.settings;

import lombok.Data;
import lombok.Getter;

@Data
public class DevSettings {
    private boolean maskRendering;
    private String defaultMap;
    private String version;
}
