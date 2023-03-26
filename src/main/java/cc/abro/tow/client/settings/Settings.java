package cc.abro.tow.client.settings;

import lombok.Data;
import lombok.Getter;

@Getter
public class Settings {

    private final Graphics graphics = new Graphics();
    private final Volume volume = new Volume();
    private final Profile profile = new Profile();

    @Data
    public static class Graphics {
        private int widthScreen = 1280;
        private int heightScreen = 720;
        private boolean fullScreen = true;
        private int vSyncDivider = 1;
        private int fpsLimit = 120;
        private String cursorSprite = "cursor_aim_1";
    }

    @Data
    public static class Profile {
        private String nickname = "Pitsa";
        private int[] color = {255, 255, 255};
        private String language = "ru";
    }

    @Data
    public static class Volume {
        private int musicVolume = 50;
        private int soundVolume = 50;
    }
}
