package tech.abro.orchengine.cycle;

import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.lwjgl.opengl.GL11;
import tech.abro.orchengine.Global;
import tech.abro.orchengine.Loader;
import tech.abro.orchengine.logger.Logger;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

public class GUI {

    private DefaultInitializer initializer; //Инициализатор LeGUI

    public GUI(){
        try {
            //Инициализация интерфейса
            initLeGUI();
        } catch (Exception e) {
            Global.logger.println("LeGUI initialization failed", e, Logger.Type.ERROR);
            Loader.exit();
        }
    }

    private void initLeGUI(){
        Frame frame = createFrame();

        initializer = new DefaultInitializer(Global.engine.render.getWindowID(), frame);
        initializer.getRenderer().initialize();
    }

    public Frame createFrame(){
        Frame frame = new Frame(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        frame.getContainer().setFocusable(true);

        return frame;
    }

    public void render(){
        //Обновление интерфейса в соответствие с параметрами окна
        initializer.getContext().updateGlfwWindow();

        //Отрисовка интерфейса
        initializer.getRenderer().render(Global.location.getGuiFrame(), initializer.getContext());

        //Нормализация параметров OpenGL после отрисовки интерфейса
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void pollEvents(){
        //Получение событий ввода и других callbacks от OpenGL
        glfwPollEvents();

        //Обработка событий (Системных и GUI)
        initializer.getSystemEventProcessor().processEvents(Global.location.getGuiFrame(), initializer.getContext());
        initializer.getGuiEventProcessor().processEvents();

        //Перерасположить компоненты
        LayoutManager.getInstance().layout(Global.location.getGuiFrame());

        //Запуск анимаций
        AnimatorProvider.getAnimator().runAnimations();
    }

    public void setFrameFocused(Frame frame){
        initializer.getContext().setFocusedGui(frame.getContainer());
    }
}
