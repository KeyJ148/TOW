package cc.abro.tow.client.settings;

public class Settings {

    public Graphics graphics = new Graphics();
    public Volume volume = new Volume();
    public Profile profile = new Profile();

    public static class Graphics {
        public int widthScreen = 1280;
        public int heightScreen = 720;
        public boolean fullScreen = true;
        public int vSyncDivider = 1;
        public int fpsLimit = 120;
        public String cursorSprite = "cursor_aim_1";
    }

    public static class Profile {
        public String nickname = "Player";
        public int[] color = {255, 255, 255};
    }

    public static class Volume {
        public double musicVolume = 50.0;
        public double soundVolume = 50.0;
    }
}
