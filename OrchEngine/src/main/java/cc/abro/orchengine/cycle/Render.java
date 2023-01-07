package cc.abro.orchengine.cycle;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.exceptions.EngineException;
import cc.abro.orchengine.init.interfaces.GameInterface;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.picocontainer.Startable;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Log4j2
@EngineService
public class Render implements Startable {

    private final GameInterface game;
    private final LocationManager locationManager;
    private final LeguiRender leguiRender;

    private final Settings settings;
    private final FPSLimiter fpsLimiter;

    private long windowID; //ID окна игры для LWJGL
    private long monitorID; //ID монитора (0 для не полноэкранного режима)
    private int width;
    private int height;

    public Render(GameInterface game, LocationManager locationManager, LeguiRender leguiRender) {
        this.game = game;
        this.locationManager = locationManager;
        this.leguiRender = leguiRender;

        settings = game.getRenderSettings();
        fpsLimiter = new FPSLimiter(settings.fpsLimit);
    }

    @Override
    public void start() {
        //Инициализация GLFW
        if (!glfwInit()) {
            log.fatal("GLFW initialization failed");
            throw new EngineException("GLFW initialization failed");
        }

        //Инициализация и настройка окна
        try {
            initOpenGL();
        } catch (Exception e) {
            log.fatal("OpenGL initialization failed", e);
            throw e;
        }

        try {
            leguiRender.init(getWindowID());
        } catch (Exception e) {
            log.fatal("LeGUI initialization failed", e);
            throw e;
        }
    }

    private void initOpenGL() {
        //Установка параметров для окна
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        //Получение разрешения экрана
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int monitorWidth = vidMode.width();
        int monitorHeight = vidMode.height();

        //Выбор экрана и размеров окна
        if (settings.fullScreen) {
            monitorID = glfwGetPrimaryMonitor();
            width = monitorWidth;
            height = monitorHeight;
        } else {
            monitorID = 0;
            width = settings.widthScreen;
            height = settings.heightScreen;
        }

        //Создание окна
        windowID = glfwCreateWindow(width, height, settings.windowName, monitorID, NULL);
        //Перемещение окна на центр монитора
        glfwSetWindowPos(windowID, (monitorWidth - width) / 2, (monitorHeight - height) / 2);
        //Создание контекста GLFW
        glfwMakeContextCurrent(windowID);
        //Включение VSync: будет происходить синхронизация через каждые N кадров
        glfwSwapInterval(settings.vsyncDivider);
        //Создание контекста OpenGL
        GL.createCapabilities();

        //Настройка графики
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        //Заливка всего фона черным цветом
        GL11.glClearColor(0f, 0f, 0f, 0f);
        //Обновление кадра
        glfwSwapBuffers(windowID);
    }


    public void loop() {
        glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT); //Очистка рендера

        game.render(); //Отрисовка в главном игровом классе (ссылка передается в движок при инициализации)
        locationManager.getActiveLocation().render(getWidth(), getHeight()); //Отрисовка локации
    }

    @Override
    public void stop() {
        leguiRender.stop();

        glfwFreeCallbacks(getWindowID());
        glfwDestroyWindow(getWindowID());
        glfwTerminate();
        GLFWErrorCallback errorCallback = glfwSetErrorCallback(null);
        if (errorCallback != null) errorCallback.free();
    }

    //Настройка иконки окна
    public void setIcon(Texture texture) {
        ByteBuffer textureByteBuffer = Context.getService(TextureService.class)
                .getByteBufferFromBufferedImage(texture.getImage());
        GLFWImage icon = GLFWImage.malloc();
        icon.set(texture.getWidth(), texture.getHeight(), textureByteBuffer);
        GLFWImage.Buffer iconBuffer = GLFWImage.malloc(1);
        iconBuffer.put(0, icon);
        glfwSetWindowIcon(windowID, iconBuffer);
    }

    //Включение видимости окна
    public void showWindow() {
        glfwShowWindow(windowID);
    }

    public void vSync() {
        glfwSwapBuffers(windowID);
    }

    public void fpsSync() {
        fpsLimiter.sync();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowID() {
        return windowID;
    }

    public long getMonitorID() {
        return monitorID;
    }

    public record Settings(int widthScreen, int heightScreen, boolean fullScreen,
                           int fpsLimit, int vsyncDivider, String windowName) {
    }
}
