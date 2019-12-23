package tow.engine.io;

import org.lwjgl.glfw.GLFWKeyCallback;
import tow.engine2.Global;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler {

	public static ArrayList<Event> events = new ArrayList<>();

	public static void init(){
		glfwSetKeyCallback(Global.window, (window, key, scancode, action, mods) -> {
			events.add(new Event(key, action));
		});
	}

	public static void update(){
		events.clear();
	}

	public static boolean isKeyDown(int key){
		return glfwGetKey(Global.window, key) == GLFW_PRESS;
	}

	public static class Event{
		public int key;
		public int action;

		public Event(int key, int action) {
			this.key = key;
			this.action = action;
		}
	}
}
