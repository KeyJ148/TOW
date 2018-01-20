package engine;

import engine.cycle.Analyzer;
import engine.cycle.Engine;
import engine.image.TextureManager;
import engine.inf.InfMain;
import engine.io.Logger;
import engine.io.MouseHandler;
import engine.net.client.TCPControl;
import engine.net.client.TCPRead;
import engine.setting.SettingStorage;
import game.client.Game;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.Log;

import java.io.File;

public class Loader {
	

	public static void main (String args[]) {
		loadLibrary();//Загрузка библиотек
		init(); //Инициализация перед запуском
		Global.engine.run();//Запуск главного цикла
	}
	
	//Инициализация движка перед запуском
	public static void init() {
		Log.setVerbose(false); //Отключения логов в Slick-util

		SettingStorage.init();//Загрузка настроек

		//Установка настроек логирования
		if (SettingStorage.Logger.ERROR_CONSOLE) Logger.enable(Logger.Type.ERROR);
		if (SettingStorage.Logger.DEBUG_CONSOLE) Logger.enable(Logger.Type.DEBUG);
		if (SettingStorage.Logger.DEBUG_CONSOLE_OBJECT) Logger.enable(Logger.Type.DEBUG_OBJECT);
		if (SettingStorage.Logger.DEBUG_CONSOLE_IMAGE) Logger.enable(Logger.Type.DEBUG_IMAGE);
		if (SettingStorage.Logger.DEBUG_CONSOLE_MASK) Logger.enable(Logger.Type.DEBUG_MASK);
		if (SettingStorage.Logger.DEBUG_CONSOLE_AUDIO) Logger.enable(Logger.Type.DEBUG_AUDIO);
		if (SettingStorage.Logger.DEBUG_CONSOLE_FPS) Logger.enable(Logger.Type.CONSOLE_FPS);

		Global.engine = new Engine();//Создание класса для главного цикла
		Global.engine.render.initGL();//Инициализация OpenGL

		Global.tcpControl = new TCPControl();
		Global.tcpRead = new TCPRead();

		TextureManager.init();//Загрузка текстур и анимаций
		AudioStorage.init();//Загрузка

		Global.infMain = new InfMain();
		MouseHandler.init();

		Global.engine.analyzer = new Analyzer();//Создаём анализатор производительности для движка
		
		Logger.println("Inicialization end", Logger.Type.DEBUG);
		
		//Инициализация игры
		Global.game = new Game();
		Global.game.init();
	}

	public static void loadLibrary(){
		boolean successLoad = false;

		if (!successLoad){
			try{
				System.loadLibrary("jinput-dx8");
				System.loadLibrary("jinput-raw");
				System.loadLibrary("lwjgl");
				System.loadLibrary("OpenAL32");
				Logger.println("32-bit native module load complite (Windows)", Logger.Type.DEBUG);
				successLoad = true;
			} catch (UnsatisfiedLinkError e){}
		}

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
				String nativeLibPath = new File("").getAbsolutePath() + "/lib/native/";
				System.load(nativeLibPath + "libjinput-linux.so");
				System.load(nativeLibPath + "libopenal.so");
				System.load(nativeLibPath + "liblwjgl.so");
				Logger.println("32-bit native module load complite (Linux)", Logger.Type.DEBUG);
				successLoad = true;
			} catch (UnsatisfiedLinkError e){}
		}

		if (!successLoad){
			try{
				String nativeLibPath = new File("").getAbsolutePath() + "/lib/native/";
				System.load(nativeLibPath + "libjinput-linux64.so");
				System.load(nativeLibPath + "libopenal64.so");
				System.load(nativeLibPath + "liblwjgl64.so");
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
		if (SettingStorage.Logger.DEBUG_CONSOLE){
			Logger.println("\nExit stack trace: ", Logger.Type.DEBUG);
			new Exception().printStackTrace(System.out);
		}

		System.exit(0);
	}
}
