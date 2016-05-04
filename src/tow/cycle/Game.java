package tow.cycle;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import tow.Global;
import tow.Loader;

public class Game{

	public Update update;
	public Render render;
	public Analyzer analyzer;//Вывод отладочных данных
	
	public boolean running = false; //Выполнение главного игрового цикла
	public boolean restart = false; //Перезагрузка карты
	
	public Game(){
		update = new Update();
		render = new Render();
	}
	
	public void run(){
		long lastUpdate = System.nanoTime();//Для update
		long startUpdate, startRender;//Для анализатора
		
		while(!Display.isCloseRequested()){
			startUpdate = System.nanoTime();
			update.loop(System.nanoTime() - lastUpdate);
			lastUpdate = System.nanoTime();
			if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsUpdate(startUpdate);
				
			startRender = System.nanoTime();
			render.loop();
			if ((Global.setting.DEBUG_CONSOLE_FPS) || (Global.setting.DEBUG_MONITOR_FPS)) analyzer.loopsRender(startRender);
			
			if (restart) Loader.restart();
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
		}
		
		System.exit(0);
	}
	
	public void start() {
		running = true;
	}
	
	public void startRestart(){
		restart = true;
	}
	
}
