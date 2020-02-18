package tow.engine.input.mouse;

import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.lwjgl.BufferUtils;
import tow.engine.Vector2;
import tow.engine.image.TextureHandler;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Sprite;
import tow.engine.Global;

import java.nio.DoubleBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler {
	
	private MouseCursor cursor;

	private Set<Integer> buttonPressed = new HashSet<>();
	private MouseEventHistory eventHistory;

	public MouseHandler() {
		//Создание объекта для обработки курсора
		cursor = new MouseCursor();

		//Создание объекта фиксирующего все события мыши
		eventHistory = new MouseEventHistory();
	}

	public MouseCursor getCursor(){
		return cursor;
	}

	public boolean isButtonDown(int button){
		return buttonPressed.contains(button);
	}
	
	public void update(){
		List<MouseClickEvent> eventHistoryList = getEventHistory().getList();
		for(MouseClickEvent event : eventHistoryList){
			if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) buttonPressed.add(event.getButton().getCode());
			if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) buttonPressed.remove(event.getButton().getCode());
		}

		eventHistory.update();
		cursor.update();
	}
	
	public void draw(){
		cursor.draw();
	}

	public MouseEventHistory getEventHistory(){
		return eventHistory;
	}
}
