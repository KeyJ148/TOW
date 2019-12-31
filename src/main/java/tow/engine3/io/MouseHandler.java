package tow.engine3.io;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import tow.engine3.image.TextureManager;
import tow.engine3.obj.Obj;
import tow.engine3.obj.components.Position;
import tow.engine3.obj.components.render.Sprite;
import tow.engine.Global;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class MouseHandler {
	
	public static Obj cursor;

	public static int mouseX;
	public static int mouseY;
	public static boolean mouseDown1;
	public static boolean mouseDown2;
	public static boolean mouseDown3;

	public static ArrayList<Event> events = new ArrayList<>();  //TODO: synchronized
	private static GLFWMouseButtonCallbackI mouseCallback;

	public static void init() {
		//Отключение стнадартного курсора
		//TODO: Mouse.setGrabbed(true);
		glfwSetInputMode(Global.engine.render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
				
		//Добавление своего курсора
		cursor = new Obj();
		cursor.position = new Position(cursor, 0, 0, -1000);
		cursor.position.absolute = false;
		cursor.rendering = new Sprite(cursor, TextureManager.getTexture("cursor"));

		mouseX = getMouseX();
		mouseY = Global.engine.render.getHeight() - getMouseY();

		glfwSetMouseButtonCallback(Global.engine.render.getWindowID(),
				mouseCallback = GLFWMouseButtonCallback.create((window, button, action, mods) -> {
			events.add(new Event(button, action));
		}));
	}
	
	public static void update(){
		mouseX = getMouseX();
		mouseY = getMouseY();

		//TODO: выход курсора за границы экрана

		mouseDown1 = glfwGetMouseButton(Global.engine.render.getWindowID(), GLFW_MOUSE_BUTTON_1) == GLFW_PRESS;
		mouseDown2 = glfwGetMouseButton(Global.engine.render.getWindowID(), GLFW_MOUSE_BUTTON_2) == GLFW_PRESS;
		mouseDown3 = glfwGetMouseButton(Global.engine.render.getWindowID(), GLFW_MOUSE_BUTTON_3) == GLFW_PRESS;

		events.clear();
	}
	
	public static void draw(){
		cursor.position.x = mouseX;
		cursor.position.y = mouseY;
		cursor.draw();
	}

	private static int getMouseX(){
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Global.engine.render.getWindowID(), posX, null);
		return (int) Math.round(posX.get(0));
	}

	private static int getMouseY(){
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(Global.engine.render.getWindowID(), null, posY);
		return (int) Math.round(posY.get(0));
	}

	public static class Event{
		public int button;
		public int action;

		public Event(int button, int action) {
			this.button = button;
			this.action = action;
		}
	}
}
