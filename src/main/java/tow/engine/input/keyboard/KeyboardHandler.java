package tow.engine.input.keyboard;

import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.input.Keyboard;
import tow.engine.Global;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler {

	private Set<Integer> keyPressed = new HashSet<>();
	private KeyboardEventHistory eventHistory;

	public KeyboardHandler(){
		//Создание объекта фиксирующего все события клавиатуры
		eventHistory = new KeyboardEventHistory();
	}

	public boolean isKeyDown(int key){
		return keyPressed.contains(key);
	}

	public void update(){
		List<KeyEvent> eventHistoryList = getEventHistory().getList();
		for(KeyEvent event : eventHistoryList){
			if (event.getAction() == GLFW_PRESS) keyPressed.add(event.getKey());
			if (event.getAction() == GLFW_RELEASE) keyPressed.remove(event.getKey());
		}

		eventHistory.update();
	}

	public KeyboardEventHistory getEventHistory(){
		return eventHistory;
	}
}
