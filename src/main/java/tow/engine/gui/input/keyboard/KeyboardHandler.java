package tow.engine.gui.input.keyboard;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.KeyEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardHandler {

	private Set<Integer> keyPressed = new HashSet<>();
	private KeyboardEventHistory eventHistory;

	public KeyboardHandler(Frame frame){
		//Создание объекта фиксирующего все события клавиатуры
		eventHistory = new KeyboardEventHistory(frame);
	}

	//Инициализация обработчика клавиатуры с сохранением состояния предыдущего обработчика (нажатых клавиш)
	public KeyboardHandler(Frame frame, KeyboardHandler keyboard){
		this(frame);
		keyPressed = new HashSet<>(keyboard.keyPressed);
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
