package tow.engine.io.mouse;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import tow.engine.Global;

import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class MouseEventHistory {

    private List<Event> eventHistory = new LinkedList<>();
    private GLFWMouseButtonCallbackI mouseCallback;

    public MouseEventHistory(){
        //Создание обратного вызова для фиксирования всех событий мыши
        glfwSetMouseButtonCallback(Global.engine.render.getWindowID(),
                mouseCallback = GLFWMouseButtonCallback.create((window, button, action, mods) -> {
                    eventHistory.add(new Event(button, action));
                }));
    }

    public List<Event> getList(){
        return List.copyOf(eventHistory);
    }

    public void update(){
        eventHistory.clear();
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
