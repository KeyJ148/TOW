package cc.abro.orchengine.resources.settings;

public class SettingsStorage {

    public static Display DISPLAY;
    public static Network NETWORK;
    public static Music MUSIC;
    public static Logger LOGGER;

    public static class Display {
        public int WIDTH_SCREEN;
        public int HEIGHT_SCREEN;
        public int FPS_LIMIT;
        public int VSYNC_DIVIDER;
        public String WINDOW_NAME;
        public boolean FULL_SCREEN;
    }

    public static class Network {
        public boolean TCP_NODELAY;
        public boolean KEEP_ALIVE;
        public int SEND_BUF_SIZE;
        public int RECEIVE_BUF_SIZE;
        public int TRAFFIC_CLASS;
        public int PREFERENCE_CON_TIME;
        public int PREFERENCE_LATENCY;
        public int PREFERENCE_BANDWIDTH;
        public int UDP_READ_BYTE_ARRAY_LEN;
    }

    public static class Music {
        public double MUSIC_VOLUME;
        public double SOUND_VOLUME;
    }

    public static class Logger {
        public boolean ERROR_CONSOLE;//выводить в консоль сообщения об ошибках?
        public boolean ERROR_CONSOLE_SERVER;//выводить в консоль ошибки сервера?
        public boolean DEBUG_CONSOLE;//выводить в консоль сообщения отладки?
        public boolean DEBUG_CONSOLE_OBJECT;//выводить в консоль сообщения создания объектов?
        public boolean DEBUG_CONSOLE_IMAGE;//выводить в консоль сообщения спрайтов и анимации?
        public boolean DEBUG_CONSOLE_MASK;//выводить в консоль сообщения загрузки маски?
        public boolean DEBUG_CONSOLE_AUDIO;//выводить в консоль сообщения загрузки звука?
        public boolean DEBUG_CONSOLE_FPS;//выводить в консоль фпс?
        public boolean DEBUG_CONSOLE_SERVER;//выводить в консоль дебаг сообщения сервера?
        public boolean DEBUG_CONSOLE_MPS;//выводить в консоль мпс?
        public boolean DEBUG_MONITOR_FPS;//выводить в окно фпс?
        public boolean MASK_DRAW;//отрисовка маски
    }
}
