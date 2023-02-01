package cc.abro.orchengine.cycle;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.gameobject.LocationManager;
import lombok.extern.log4j.Log4j2;
import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.lwjgl.opengl.GL11;
import org.picocontainer.Startable;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

@Log4j2
@EngineService
public class LeguiRender implements Startable {

    private final Render render;
    private final LocationManager locationManager;

    private DefaultInitializer leguiInitializer;

    public LeguiRender(Render render, LocationManager locationManager) {
        this.render = render;
        this.locationManager = locationManager;
    }

    @Override
    public void start() {
        try {
            leguiInitializer = new DefaultInitializer(render.getWindowID(), new Frame());
            leguiInitializer.getRenderer().initialize();
        } catch (Exception e) {
            log.fatal("LeGUI initialization failed", e);
            throw e;
        }
    }

    public Frame createFrame() {
        return createFrame(render.getWidth(), render.getHeight());
    }
    
    public Frame createFrame(int width, int height) {
        Frame frame = new Frame(width, height);
        frame.getContainer().setFocusable(true);

        return frame;
    }

    public void render(Frame frame) {
        //Обновление интерфейса в соответствие с параметрами окна
        leguiInitializer.getContext().updateGlfwWindow();

        //Отрисовка интерфейса
        leguiInitializer.getRenderer().render(frame, leguiInitializer.getContext());

        //Нормализация параметров OpenGL после отрисовки интерфейса
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void pollEvents(Frame frame) {
        //Получение событий ввода и других callbacks от OpenGL
        glfwPollEvents();

        //Обработка событий (Системных и GUI)
        leguiInitializer.getSystemEventProcessor().processEvents(frame, leguiInitializer.getContext());
        leguiInitializer.getGuiEventProcessor().processEvents();

        //Перерасположить компоненты
        Frame activeGuiFrame = locationManager.getActiveLocation().getGuiLocationFrame().getGuiFrame();
        LayoutManager.getInstance().layout(activeGuiFrame);

        //Запуск анимаций
        AnimatorProvider.getAnimator().runAnimations();
    }

    public void setFrameFocused(Frame frame) {
        leguiInitializer.getContext().setFocusedGui(frame.getContainer());
    }

    @Override
    public void stop() {
        leguiInitializer.getRenderer().destroy();
    }
}
