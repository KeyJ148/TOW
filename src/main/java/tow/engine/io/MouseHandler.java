package tow.engine.io;

import org.lwjgl.BufferUtils;
import tow.engine.image.TextureManager;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Sprite;
import tow.engine2.Global;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class MouseHandler {
	
	public static Obj cursor;

	public static int mouseX;
	public static int mouseY;
	public static boolean mouseDown1;
	public static boolean mouseDown2;
	public static boolean mouseDown3;

	public static void init() {
		//Отключение стнадартного курсора
		//TODO: Mouse.setGrabbed(true);
				
		//Добавление своего курсора
		cursor = new Obj();
		cursor.position = new Position(cursor, 0, 0, -1000);
		cursor.position.absolute = false;
		cursor.rendering = new Sprite(cursor, TextureManager.getTexture("cursor"));

		mouseX = getMouseX();
		mouseY = Global.engine.render.getHeight() - getMouseY();
	}
	
	public static void update(){
		mouseX = getMouseX();
		mouseY = Global.engine.render.getHeight() - getMouseY();

		//TODO:
		/*
		mouseDown1 = Mouse.isButtonDown(0);
		mouseDown2 = Mouse.isButtonDown(1);
		mouseDown3 = Mouse.isButtonDown(2);
		 */
			
	}
	
	public static void draw(){
		cursor.position.x = mouseX;
		cursor.position.y = mouseY;
		//TODO: cursor.draw();
	}

	private static int getMouseX(){
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Global.window, posX, null);
		return (int) Math.round(posX.get(0));
	}

	private static int getMouseY(){
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Global.window, null, posY);
		return (int) Math.round(posY.get(0));
	}
}
