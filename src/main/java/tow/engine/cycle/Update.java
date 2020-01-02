package tow.engine.cycle;

import tow.engine.Global;
import tow.engine.io.logger.Logger;
import tow.engine2.Loader;
import tow.engine2.image.Camera;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class Update {

	private long startUpdateTime, lastUpdateTime = 0;//Для вычисления delta

	public void loop(){
		//При первом вызове устанавливаем текущее время
		if (lastUpdateTime == 0) lastUpdateTime = System.nanoTime();

		startUpdateTime = System.nanoTime();
		loop(System.nanoTime() - lastUpdateTime);
		lastUpdateTime = startUpdateTime;//Начало предыдущего update, чтобы длительность update тоже учитывалась
	}

	//Обновляем игру в соответствие с временем прошедшим с последнего обновления
	private void loop(long delta){
		glfwPollEvents();//Получение событий ввода и других callbacks

		Global.game.update(delta);//Обновить главный игровой класс при необходимости

		Global.tcpRead.update();//Обработать все полученные сообщения по TCP
		Global.udpRead.update();//Обработать все полученные сообщения по UDP

		if (Global.room != null) {
			Global.room.update(delta);//Обновить все объекты в комнате
		} else {
			Global.logger.println("No create room! (Global.room)", Logger.Type.ERROR);
			Loader.exit();
		}

		Camera.calc();//Расчёт положения камеры

		Global.mouse.update(); //Очистка истории событий мыши
		Global.keyboard.update(); //Очистка истории событий клавиатуры
	}

}
