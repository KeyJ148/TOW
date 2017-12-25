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

		Global.setting = new SettingStorage();//Создание хранилища настроек
		Global.setting.init();//Загрузка настроек
		if (Global.setting.ERROR_CONSOLE) Logger.enable(Logger.Type.ERROR);
		if (Global.setting.DEBUG_CONSOLE) Logger.enable(Logger.Type.DEBUG);
		if (Global.setting.DEBUG_CONSOLE_OBJECT) Logger.enable(Logger.Type.DEBUG_OBJECT);
		if (Global.setting.DEBUG_CONSOLE_IMAGE) Logger.enable(Logger.Type.DEBUG_IMAGE);
		if (Global.setting.DEBUG_CONSOLE_MASK) Logger.enable(Logger.Type.DEBUG_MASK);
		if (Global.setting.DEBUG_CONSOLE_FPS) Logger.enable(Logger.Type.CONSOLE_FPS);

		Global.engine = new Engine();//Создание класса для главного цикла
		Global.engine.render.initGL();//Инициализация OpenGL

		Global.tcpControl = new TCPControl();
		Global.tcpRead = new TCPRead();

		TextureManager.initTexture();

		Global.infMain = new InfMain();
		MouseHandler.init();
			
		if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) 
			Global.engine.analyzer = new Analyzer();
		
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
			System.exit(0);
		}
	}
}
