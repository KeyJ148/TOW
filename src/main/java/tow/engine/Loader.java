package tow.engine;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.Log;
import tow.engine.cycle.Analyzer;
import tow.engine.cycle.Engine;
import tow.engine.image.TextureManager;
import tow.engine.implementation.*;
import tow.engine.inf.InfMain;
import tow.engine.inf.title.FontManager;
import tow.engine.io.Logger;
import tow.engine.io.MouseHandler;
import tow.engine.net.client.tcp.TCPControl;
import tow.engine.net.client.tcp.TCPRead;
import tow.engine.net.client.udp.UDPControl;
import tow.engine.net.client.udp.UDPRead;
import tow.engine.resources.settings.SettingsStorage;
import tow.engine.resources.settings.SettingsStorageHandler;

import java.io.IOException;

public class Loader {


	public static void start(GameInterface game, NetGameReadInterface netGameRead, StorageInterface storage,
							 ServerInterface server, NetServerReadInterface netServerRead) {
		Global.game = game;
		Global.server = server;
		Global.netGameRead = netGameRead;
		Global.netServerRead = netServerRead;
		Global.storage = storage;

		loggerInit();//Загрузка логгера для вывода ошибок
		loadLibrary();//Загрузка библиотек
		init(); //Инициализация перед запуском
		Global.engine.run();//Запуск главного цикла
	}

	private static void loggerInit(){
		try {
			SettingsStorageHandler.init();//Загрузка настроек
		} catch (IOException e){
			e.printStackTrace();
			exit();
		}

		//Установка настроек логирования
		Logger.enable(Logger.Type.INFO);
		Logger.enable(Logger.Type.SERVER_INFO);
		if (SettingsStorage.LOGGER.ERROR_CONSOLE) Logger.enable(Logger.Type.ERROR);
		if (SettingsStorage.LOGGER.ERROR_CONSOLE_SERVER) Logger.enable(Logger.Type.SERVER_ERROR);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE) Logger.enable(Logger.Type.DEBUG);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_OBJECT) Logger.enable(Logger.Type.DEBUG_OBJECT);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_IMAGE) Logger.enable(Logger.Type.DEBUG_IMAGE);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_MASK) Logger.enable(Logger.Type.DEBUG_MASK);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_AUDIO) Logger.enable(Logger.Type.DEBUG_AUDIO);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_FPS) Logger.enable(Logger.Type.CONSOLE_FPS);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_SERVER) Logger.enable(Logger.Type.SERVER_DEBUG);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_MPS) Logger.enable(Logger.Type.MPS);
	}

	//Инициализация движка перед запуском
	private static void init() {
		Log.setVerbose(false); //Отключения логов в Slick-util

		Global.engine = new Engine();//Создание класса для главного цикла
		Global.engine.render.initGL();//Инициализация OpenGL

		Global.tcpControl = new TCPControl();
		Global.tcpRead = new TCPRead();
		Global.udpControl = new UDPControl();
		Global.udpRead = new UDPRead();

		TextureManager.init();//Загрузка текстур и анимаций
		FontManager.init();//Загрузка шрифтов
		AudioManager.init();//Загрузка звуков

		Global.infMain = new InfMain();
		MouseHandler.init();

		Global.engine.analyzer = new Analyzer();//Создаём анализатор производительности для движка

		Logger.println("Inicialization end", Logger.Type.DEBUG);

		//Инициализация игры
		Global.game.init();
	}

	private static void loadLibrary(){
		boolean successLoad = false;

		if (!successLoad){
			try{
				System.loadLibrary("jinput-dx8_64");
				System.loadLibrary("jinput-raw_64");
				System.loadLibrary("lwjgl64");
				System.loadLibrary("OpenAL64");
				Logger.println("64-bit native module load complite (Windows)", Logger.Type.DEBUG);
				successLoad = true;
			} catch (UnsatisfiedLinkError e){}
		}

		if (!successLoad){
			try{
				System.loadLibrary("libjinput-linux64");
				System.loadLibrary("libopenal64");
				System.loadLibrary("liblwjgl64");
				Logger.println("64-bit native module load complite (Linux)", Logger.Type.DEBUG);
				successLoad = true;
			} catch (UnsatisfiedLinkError e){}
		}

		if (!successLoad){
			Logger.println("Native module not loading", Logger.Type.ERROR);
			Loader.exit();
		}
	}

	public static void exit(){
		Display.destroy();
		AL.destroy();

		//Вывод пути выхода
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE){
			Logger.println("Exit stack trace: ", Logger.Type.DEBUG);
			new Exception().printStackTrace(System.out);
		}

		System.exit(0);
	}
}
