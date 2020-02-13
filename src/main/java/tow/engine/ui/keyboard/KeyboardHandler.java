package tow.engine.ui.keyboard;

import tow.engine.Global;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler {

	private KeyboardEventHistory eventHistory;

	public KeyboardHandler(){
		//Создание объекта фиксирующего все события клавиатуры
		eventHistory = new KeyboardEventHistory();
	}

	//TODO: убрать и оставить только History (либо генерировать это из History)
	public boolean isKeyDown(int key){
		return glfwGetKey(Global.engine.render.getWindowID(), key) == GLFW_PRESS;
	}

	public void update(){
		eventHistory.update();
	}

	public KeyboardEventHistory getEventHistory(){
		return eventHistory;
	}
}
