package tow.engine.cycle;

import tow.engine2.Loader;
import tow.engine2.cycle.Analyzer;
import tow.engine2.resources.settings.SettingsStorage;

import org.lwjgl.glfw.GLFW;

public class Engine{

	public Update update;
	public Render render;
	public Analyzer analyzer;
	private FPSLimit fpsLimit;
	
	public Engine(){
		update = new Update();
		render = new Render();
		analyzer = new Analyzer();
		fpsLimit = new FPSLimit(SettingsStorage.DISPLAY.FPS_LIMIT);
	}
	
	public void run(){
		while(!GLFW.glfwWindowShouldClose(render.getWindowID())){
			//Цикл Update
			analyzer.startUpdate();
			update.loop();
			analyzer.endUpdate();

			//Цикл Render
			analyzer.startRender();
			render.loop();
			analyzer.endRender();

			//Пауза для синхронизации кадров
			analyzer.startSync();
			render.vsync(); //Вертикальная синхронизация
			fpsLimit.sync(); //Ограничитель FPS (если вертикальная синхронизация отключена или не сработала)
			analyzer.endSync();
		}

		Loader.exit();
	}
	
}
