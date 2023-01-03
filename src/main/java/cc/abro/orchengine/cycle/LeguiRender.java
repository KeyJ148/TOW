package cc.abro.orchengine.cycle;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.location.LocationManager;
import lombok.extern.log4j.Log4j2;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

@Log4j2
@EngineService
public class LeguiRender {

    private Render render;

    public Frame createFrame() {
        render = Context.getService(Render.class); //TODO циклическая зависимость, избавиться
        Frame frame = new Frame(render.getWidth(), render.getHeight());
        frame.getContainer().setFocusable(true);

        return frame;
    }

    public void render(Frame frame) {
        //Обновление интерфейса в соответствие с параметрами окна
        render.getLeguiInitializer().getContext().updateGlfwWindow();

        //Отрисовка интерфейса
        render.getLeguiInitializer().getRenderer().render(frame, render.getLeguiInitializer().getContext());

        //Нормализация параметров OpenGL после отрисовки интерфейса
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void pollEvents(Frame frame) {
        //Получение событий ввода и других callbacks от OpenGL
        glfwPollEvents();

        //Обработка событий (Системных и GUI)
        render.getLeguiInitializer().getSystemEventProcessor().processEvents(frame, render.getLeguiInitializer().getContext());
        render.getLeguiInitializer().getGuiEventProcessor().processEvents();

        //Перерасположить компоненты
        LayoutManager.getInstance().layout(Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getGuiFrame());

        //Запуск анимаций
        AnimatorProvider.getAnimator().runAnimations();
    }

    public void setFrameFocused(Frame frame) {
        render.getLeguiInitializer().getContext().setFocusedGui(frame.getContainer());
    }
}
