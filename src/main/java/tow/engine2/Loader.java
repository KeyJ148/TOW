package tow.engine2;

import org.lwjgl.glfw.GLFWErrorCallback;
import tow.engine.Global;
import tow.engine.cycle.Engine;
import tow.engine3.image.TextureManager;
import tow.engine2.implementation.*;
import tow.engine.io.logger.AggregateLogger;
import tow.engine.io.keyboard.KeyboardHandler;
import tow.engine.io.mouse.MouseHandler;
import tow.engine3.net.client.Ping;
import tow.engine3.net.client.tcp.TCPControl;
import tow.engine3.net.client.tcp.TCPRead;
import tow.engine3.net.client.udp.UDPControl;
import tow.engine3.net.client.udp.UDPRead;
import tow.engine.resources.settings.SettingsStorage;
import tow.engine.resources.settings.SettingsStorageHandler;
import tow.engine.io.logger.Logger;

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

		Global.logger = new AggregateLogger();

		//Установка настроек логирования
		Global.logger.enableType(Logger.Type.INFO);
		Global.logger.enableType(Logger.Type.SERVER_INFO);
		if (SettingsStorage.LOGGER.ERROR_CONSOLE) Global.logger.enableType(Logger.Type.ERROR);
		if (SettingsStorage.LOGGER.ERROR_CONSOLE_SERVER) Global.logger.enableType(Logger.Type.SERVER_ERROR);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE) Global.logger.enableType(Logger.Type.DEBUG);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_OBJECT) Global.logger.enableType(Logger.Type.DEBUG_OBJECT);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_IMAGE) Global.logger.enableType(Logger.Type.DEBUG_IMAGE);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_MASK) Global.logger.enableType(Logger.Type.DEBUG_MASK);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_AUDIO) Global.logger.enableType(Logger.Type.DEBUG_AUDIO);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_FPS) Global.logger.enableType(Logger.Type.CONSOLE_FPS);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_SERVER) Global.logger.enableType(Logger.Type.SERVER_DEBUG);
		if (SettingsStorage.LOGGER.DEBUG_CONSOLE_MPS) Global.logger.enableType(Logger.Type.MPS);
	}

	//Инициализация движка перед запуском
	private static void init() {
		Global.engine = new Engine();//Создание класса для главного цикла
		Global.engine.render.initGL();//Инициализация OpenGL

		Global.tcpControl = new TCPControl();
		Global.tcpRead = new TCPRead();
		Global.udpControl = new UDPControl();
		Global.udpRead = new UDPRead();

		Global.pingCheck = new Ping();

		TextureManager.init();//Загрузка текстур и анимаций
		//TODO: FontManager.init();//Загрузка шрифтов
		//TODO: AudioManager.init();//Загрузка звуков

		Global.mouse = new MouseHandler();
		Global.keyboard = new KeyboardHandler();

		Global.logger.println("Inicialization end", Logger.Type.DEBUG);

		//Инициализация игры
		Global.game.init();
	}

	public static void exit(){
		glfwFreeCallbacks(Global.engine.render.getWindowID());
		glfwDestroyWindow(Global.engine.render.getWindowID());
		glfwTerminate();
		GLFWErrorCallback errorCallback = glfwSetErrorCallback(null);
		if (errorCallback != null) errorCallback.free();
		//TODO: AL.destroy();

		Global.logger.println("Exit stack trace: ", new Exception(), Logger.Type.DEBUG);
		Global.logger.close();
		System.exit(0);
	}


}
