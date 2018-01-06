package engine.setting;

public class SettingStorage {

	public static class Display{
		public static int WIDTH_SCREEN;
		public static int HEIGHT_SCREEN;//размер окна
		public static int SYNC;
		public static String WINDOW_NAME;
		public static boolean FULL_SCREEN;

		private static String fileName = "engine/display.properties";
		private static void init(){
			ConfigReader cr = new ConfigReader(fileName);

			WIDTH_SCREEN = cr.findInteger("WIDTH_SCREEN");
			HEIGHT_SCREEN = cr.findInteger("HEIGHT_SCREEN");
			WINDOW_NAME = cr.findString("WINDOW_NAME");
			FULL_SCREEN = cr.findBoolean("FULL_SCREEN");
			SYNC = cr.findInteger("SYNC");
		}
	}

	public static class Performance{
		public static boolean MAX_POWER;
		public static boolean MAX_POWER_SERVER;

		private static String fileName = "engine/performance.properties";
		private static void init(){
			ConfigReader cr = new ConfigReader(fileName);

			MAX_POWER = cr.findBoolean("MAX_POWER");
			MAX_POWER_SERVER = cr.findBoolean("MAX_POWER_SERVER");
		}
	}

	public static class Net{
		public static boolean TCP_NODELAY;
		public static boolean KEEP_ALIVE;
		public static int SEND_BUF_SIZE;
		public static int RECEIVE_BUF_SIZE;
		public static int TRAFFIC_CLASS;
		public static int PREFERENCE_CON_TIME;
		public static int PREFERENCE_LATENCY;
		public static int PREFERENCE_BANDWIDTH;

		private static String fileName = "engine/net.properties";
		private static void init(){
			ConfigReader cr = new ConfigReader(fileName);

			TCP_NODELAY = cr.findBoolean("TCP_NODELAY");
			KEEP_ALIVE = cr.findBoolean("KEEP_ALIVE");
			SEND_BUF_SIZE = cr.findInteger("SEND_BUF_SIZE");
			RECEIVE_BUF_SIZE = cr.findInteger("RECEIVE_BUF_SIZE");
			TRAFFIC_CLASS = cr.findInteger("TRAFFIC_CLASS");
			PREFERENCE_CON_TIME = cr.findInteger("PREFERENCE_CON_TIME");
			PREFERENCE_LATENCY = cr.findInteger("PREFERENCE_LATENCY");
			PREFERENCE_BANDWIDTH = cr.findInteger("PREFERENCE_BANDWIDTH");
		}
	}

	public static class Music{
		public static int MUSIC_VOLUME;
		public static int SOUND_VOLUME;

		private static String fileName = "engine/music.properties";
		private static void init(){
			ConfigReader cr = new ConfigReader(fileName);

			MUSIC_VOLUME = cr.findInteger("MUSIC_VOLUME");
			SOUND_VOLUME = cr.findInteger("SOUND_VOLUME");
		}
	}

	public static class Logger{
		public static boolean ERROR_CONSOLE;//выводить в консоль сообщения об ошибках?
		public static boolean DEBUG_CONSOLE;//выводить в консоль сообщения отладки?
		public static boolean DEBUG_CONSOLE_OBJECT;//выводить в консоль сообщения создания объектов?
		public static boolean DEBUG_CONSOLE_IMAGE;//выводить в консоль сообщения спрайтов и анимации?
		public static boolean DEBUG_CONSOLE_MASK;//выводить в консоль сообщения загрузки маски?
		public static boolean DEBUG_CONSOLE_FPS;//выводить в консоль фпс?
		public static boolean DEBUG_MONITOR_FPS;//выводить в окно фпс?
		public static boolean MASK_DRAW;//отрисовка маски

		private static String fileName = "engine/logger.properties";
		private static void init(){
			ConfigReader cr = new ConfigReader(fileName);

			ERROR_CONSOLE = cr.findBoolean("ERROR_CONSOLE");
			DEBUG_CONSOLE = cr.findBoolean("DEBUG_CONSOLE");
			DEBUG_CONSOLE_OBJECT = cr.findBoolean("DEBUG_CONSOLE_OBJECT");
			DEBUG_CONSOLE_IMAGE = cr.findBoolean("DEBUG_CONSOLE_IMAGE");
			DEBUG_CONSOLE_MASK = cr.findBoolean("DEBUG_CONSOLE_MASK");
			DEBUG_CONSOLE_FPS = cr.findBoolean("DEBUG_CONSOLE_FPS");
			DEBUG_MONITOR_FPS = cr.findBoolean("DEBUG_MONITOR_FPS");
			MASK_DRAW = cr.findBoolean("MASK_DRAW");
		}
	}

	public static void init(){
		Display.init();
		Performance.init();
		Net.init();
		Music.init();
		Logger.init();
	}
}
