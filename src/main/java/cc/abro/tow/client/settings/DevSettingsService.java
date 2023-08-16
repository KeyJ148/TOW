package cc.abro.tow.client.settings;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.resources.JsonContainerLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@GameService
public class DevSettingsService {

    private static final String PATH = "configs/dev_setting.json";

    @Getter @Setter
    private DevSettings devSettings;

    public DevSettingsService() {
        try {
            loadSettingsFromDisk();
        } catch (IOException e) {
            log.error("Dev settings can't load. Use default settings.");
            log.debug(e);
            devSettings = new DevSettings();
        }
    }

    public void loadSettingsFromDisk() throws IOException {
        devSettings = JsonContainerLoader.loadInternalFile(DevSettings.class, PATH);
    }
}
