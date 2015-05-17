package main.setting;

public class SettingStorage {

	public int TPS; //Кол-во повторений update в секунду
	public int SKIP_TICKS;//Перерыв в милисекундах между повторениями
	
	public boolean DEBUG_CONSOLE;//выводить в консоль сообщения отладки?
	public boolean DEBUG_CONSOLE_IMAGE;//выводить в консоль сообщения спрайтов и анимации?
	public boolean DEBUG_CONSOLE_MASK;//выводить в консоль сообщения загрузки маски?
	public boolean DEBUG_CONSOLE_FPS;//выводить в консоль фпс?
	public boolean DEBUG_MONITOR_FPS;//выводить в окно фпс?
	public boolean MASK_DRAW;//отрисовка маски
	
	public int WIDTH_SCREEN;
	public int HEIGHT_SCREEN;//размер окна
	public String WINDOW_NAME;
	
	public int SEND_STEP_MAX;//Отправлять данные о игроке каждые n updat'ов
	
	public String fileName = "main.properties";
	
	public void initFromFile(){
		ConfigReader cr = new ConfigReader(fileName);
		
		TPS = cr.findInteger("TPS");
		WIDTH_SCREEN = cr.findInteger("WIDTH_SCREEN");
		HEIGHT_SCREEN = cr.findInteger("HEIGHT_SCREEN");
		SEND_STEP_MAX = cr.findInteger("SEND_STEP_MAX");
		
		WINDOW_NAME = cr.findString("WINDOW_NAME");
		
		DEBUG_CONSOLE = cr.findBoolean("DEBUG_CONSOLE");
		DEBUG_CONSOLE_IMAGE = cr.findBoolean("DEBUG_CONSOLE_IMAGE");
		DEBUG_CONSOLE_MASK = cr.findBoolean("DEBUG_CONSOLE_MASK");
		DEBUG_CONSOLE_FPS = cr.findBoolean("DEBUG_CONSOLE_FPS");
		DEBUG_MONITOR_FPS = cr.findBoolean("DEBUG_MONITOR_FPS");
		MASK_DRAW = cr.findBoolean("MASK_DRAW");
		
		SKIP_TICKS = 1000/TPS;
	}
}
