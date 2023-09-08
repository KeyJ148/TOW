package cc.abro.tow.client.settings;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.resources.JsonContainerLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@GameService
public class GameSettingsService {

    private static final String PATH = "configs/game/game_setting.json";

    @Getter @Setter
    private GameSettings gameSettings;

    public GameSettingsService() {
        try {
            loadSettingsFromDisk();
        } catch (IOException e) {
            log.error("Game settings can't load.", e);
            throw new RuntimeException(e);
        }
    }

    public void loadSettingsFromDisk() throws IOException {
        gameSettings = JsonContainerLoader.loadInternalFile(GameSettings.class, PATH);
    }
}
