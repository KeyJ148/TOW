package tow.engine.input.keyboard;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import tow.engine.Global;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardEventHistory {

    private List<Event> eventHistory = new LinkedList<>();
    private static GLFWKeyCallbackI keyCallback;

    public KeyboardEventHistory(){
        //Создание обратного вызова для фиксирования всех событий клавиатуры
        glfwSetKeyCallback(Global.engine.render.getWindowID(),
                keyCallback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
                    eventHistory.add(new Event(key, action));
                }));
    }

    public List<Event> getList(){
        return List.copyOf(eventHistory);
    }

    public void update(){
        eventHistory.clear();
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
