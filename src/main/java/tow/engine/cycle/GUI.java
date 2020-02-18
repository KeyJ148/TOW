package tow.engine.cycle;

import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.lwjgl.opengl.GL11;
import tow.engine.Global;
import tow.engine.Loader;
import tow.engine.input.keyboard.KeyboardHandler;
import tow.engine.input.mouse.MouseHandler;
import tow.engine.logger.Logger;

import java.io.Reader;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;

public class GUI {

    private Frame frame; //Основное окно для работы с LeGUI
    private DefaultInitializer initializer; //Инициализатор LeGUI

    public GUI(){
        //Инициализация интерфейса
        try {
            initLeGUI();
        } catch (Exception e) {
            Global.logger.println("LeGUI initialization failed", e, Logger.Type.ERROR);
            Loader.exit();
        }
    }

    private void initLeGUI(){
        frame = new Frame(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        getFrameContainer().setFocusable(true);

        initializer = new DefaultInitializer(Global.engine.render.getWindowID(), frame);
        initializer.getRenderer().initialize();
    }

    public void renderGUI(){
        //Обновление интерфейса в соответствие с параметрами окна
        initializer.getContext().updateGlfwWindow();

        //Отрисовка интерфейса
        initializer.getRenderer().render(frame, initializer.getContext());

        //Нормализация параметров OpenGL после отрисовки интерфейса
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void pollEvents(){
        //Получение событий ввода и других callbacks от OpenGL
        glfwPollEvents();

        //Обработка событий (Системных и GUI)
        initializer.getSystemEventProcessor().processEvents(frame, initializer.getContext());
        initializer.getGuiEventProcessor().processEvents();

        //Перерасположить компоненты
        LayoutManager.getInstance().layout(frame);

        //Запуск анимаций
        AnimatorProvider.getAnimator().runAnimations();
    }

    public Component getFrameContainer(){
        return frame.getContainer();
    }

    public Layer<Component> getFrameLayer(){
        return frame.getComponentLayer();
    }

    public void setFrameFocused(){
        initializer.getContext().setFocusedGui(frame.getContainer());
    }

    public Frame getFrame(){
        return frame;
    }

    public Frame createFrame(){
        initLeGUI();
        Global.keyboard = new KeyboardHandler(frame, Global.keyboard);
        Global.mouse = new MouseHandler(frame, Global.mouse);
        return getFrame();
    }

}
