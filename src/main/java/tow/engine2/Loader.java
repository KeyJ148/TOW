package tow.engine2;

import org.lwjgl.glfw.GLFWErrorCallback;
import tow.engine3.cycle.Analyzer;
import tow.engine3.cycle.Engine;
import tow.engine3.image.TextureManager;
import tow.engine2.implementation.*;
import tow.engine2.io.Logger;
import tow.engine3.io.KeyboardHandler;
import tow.engine3.io.MouseHandler;
import tow.engine3.net.client.tcp.TCPControl;
import tow.engine3.net.client.tcp.TCPRead;
import tow.engine3.net.client.udp.UDPControl;
import tow.engine3.net.client.udp.UDPRead;
import tow.engine2.resources.settings.SettingsStorage;
import tow.engine2.resources.settings.SettingsStorageHandler;

import java.io.IOException;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class Loader {


	public static void start(GameInterface game, NetGameReadInterface netGameRead, StorageInterface storage,
							 ServerInterface server, NetServerReadInterface netServerRead) {
		Global.game = game;
		Global.server = server;
		Global.netGameRead = netGameRead;
		Global.netServerRead = netServerRead;
		Global.storage = storage;

		loggerInit();//Загрузка логгера для вывода ошибок
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
		Global.engine = new Engine();//Создание класса для главного цикла
		Global.engine.render.initGL();//Инициализация OpenGL

		Global.tcpControl = new TCPControl();
		Global.tcpRead = new TCPRead();
		Global.udpControl = new UDPControl();
		Global.udpRead = new UDPRead();

		//TODO
		TextureManager.init();//Загрузка текстур и анимаций
		//FontManager.init();//Загрузка шрифтов
		//AudioManager.init();//Загрузка звуков

		MouseHandler.init();
		KeyboardHandler.init();

		Global.engine.analyzer = new Analyzer();//Создаём анализатор производительности для движка

		Logger.println("Inicialization end", Logger.Type.DEBUG);

		//Инициализация игры
		Global.game.init();
	}

	public static void exit(){
		glfwFreeCallbacks(Global.window);
		glfwDestroyWindow(Global.window);
		glfwTerminate();
		GLFWErrorCallback errorCallback = glfwSetErrorCallback(null);
		if (errorCallback != null) errorCallback.free();

		//TODO: AL.destroy();

		//Вывод пути выхода
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE){
			Logger.println("Exit stack trace: ", Logger.Type.DEBUG);
			new Exception().printStackTrace(System.out);
		}

		System.exit(0);
	}
}
