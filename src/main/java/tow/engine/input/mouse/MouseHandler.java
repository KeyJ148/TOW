package tow.engine.input.mouse;

import org.lwjgl.BufferUtils;
import tow.engine.Vector2;
import tow.engine.image.TextureHandler;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Sprite;
import tow.engine.Global;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler {
	
	private Obj cursor;
	private boolean captureCursor = false;

	private MouseEventHistory eventHistory;

	public MouseHandler() {
		//Создание объекта курсора (используется компонент Position и Sprite)
		cursor = new Obj();
		cursor.position = new Position(cursor, 0, 0, -1000);
		cursor.position.absolute = false;

		//Создание объекта фиксирующего все события мыши
		eventHistory = new MouseEventHistory();
	}

	public Vector2<Integer> getMousePos(){
		return new Vector2<>((int) cursor.position.x, (int) cursor.position.y);
	}

	//TODO: убрать и оставить только History (либо генерировать это из History)
	public boolean isButtonDown(int button){
		return glfwGetMouseButton(Global.engine.render.getWindowID(), button) == GLFW_PRESS;
	}
	
	public void update(){
		Vector2<Integer> mousePos = getMousePosFromGLFW();
		cursor.position.x = mousePos.x;
		cursor.position.y = mousePos.y;

		eventHistory.update();
	}
	
	public void draw(){
		cursor.draw();
	}

	public void setCursorTexture(TextureHandler texture){
		//Отключение стнадартного курсора
		glfwSetInputMode(Global.engine.render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		//Присвоение текстуры объекту курсора
		cursor.rendering = new Sprite(cursor, texture);
	}

	public void setDefaultCursorTexture(){
		//Включение стнадартного курсора
		glfwSetInputMode(Global.engine.render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		//Отключение текстуры у объекта курсора
		cursor.rendering = null;
	}

	public void setCaptureCursor(boolean captureCursor){
		this.captureCursor = captureCursor;
	}

	public MouseEventHistory getEventHistory(){
		return eventHistory;
	}

	//Обновление позиции объекта cursor напрямую из позиции мыши в OpenGL
	private Vector2<Integer> getMousePosFromGLFW(){
		DoubleBuffer bufX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer bufY = BufferUtils.createDoubleBuffer(1);

		glfwGetCursorPos(Global.engine.render.getWindowID(), bufX, bufY);
		Vector2<Integer> mousePos = new Vector2<>((int) bufX.get(), (int) bufY.get());

		if (captureCursor) captureCursorInWindow(mousePos);
		glfwSetCursorPos(Global.engine.render.getWindowID(), mousePos.x, mousePos.y);
		return mousePos;
	}

	//Если позиция мыши выходит за пределы окна, то функция нормализует значения в mousePos
	private void captureCursorInWindow(Vector2<Integer> mousePos){
		int width = Global.engine.render.getWidth();
		int height = Global.engine.render.getHeight();

		if (mousePos.x < 0) mousePos.x = 0;
		if (mousePos.x > width) mousePos.x = width;
		if (mousePos.y < 0) mousePos.y = 0;
		if (mousePos.y > height) mousePos.y = height;
	}
}
