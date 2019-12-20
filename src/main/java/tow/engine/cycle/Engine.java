package tow.engine.cycle;

import tow.engine.Loader;
import org.lwjgl.opengl.Display;

public class Engine{

	public Update update;
	public Render render;
	public Analyzer analyzer;//Вывод отладочных данных
	
	
	public Engine(){
		update = new Update();
		render = new Render();
	}
	
	public void run(){
		long lastUpdate = System.nanoTime();//Для update
		long startUpdate, startRender;//Для анализатора

		while(!Display.isCloseRequested()){
			startUpdate = System.nanoTime();
			update.loop(System.nanoTime() - lastUpdate);
			lastUpdate = startUpdate;//Начало предыдущего update, чтобы длительность update тоже учитывалась
			analyzer.loopsUpdate(startUpdate);
				
			startRender = System.nanoTime();
			render.loop();
			analyzer.loopsRender(startRender);
			render.sync();//Пауза для синхронизации кадров, должна быть после analyzer
		}

		Loader.exit();
	}
	
}
