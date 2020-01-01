package tow.engine.cycle;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import tow.engine.Global;
import tow.engine2.Loader;
import tow.engine3.image.Camera;
import tow.engine2.io.Logger;
import tow.engine3.io.MouseHandler;
import tow.engine2.resources.settings.SettingsStorage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Render{

	private long windowID; //ID окна игры для LWJGL
	private long monitorID; //ID монитора (0 для не полноэкранного режима)
	private int width;
	private int height;

	public void initGL(){
		//Инициализация GLFW
		if (!glfwInit()){
			Logger.println("GLFW initialization failed", Logger.Type.ERROR);
			Loader.exit();
		}

		//Инициализация и настройка окна
		try {
			//Установка параметров для окна
			glfwDefaultWindowHints();
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

			//Получение разрешения экрана
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			int monitorWidth = vidMode.width();
			int monitorHeight = vidMode.height();

			//Выбор экрана и размеров окна
			if (SettingsStorage.DISPLAY.FULL_SCREEN){
				monitorID = glfwGetPrimaryMonitor();
				width = monitorWidth;
				height = monitorHeight;
			} else {
				monitorID = 0;
				width = SettingsStorage.DISPLAY.WIDTH_SCREEN;
				height = SettingsStorage.DISPLAY.HEIGHT_SCREEN;
			}

			//Создание окна
			windowID = glfwCreateWindow(width, height, SettingsStorage.DISPLAY.WINDOW_NAME, monitorID, NULL);
			//Перемещение окна на центр монитора
			glfwSetWindowPos(windowID, (monitorWidth - width)/2, (monitorHeight - height)/2);
			//Создание контекста GLFW
			glfwMakeContextCurrent(windowID);
			//Включение VSync: будет происходить синхронизация через каждые N кадров
			glfwSwapInterval(SettingsStorage.DISPLAY.VSYNC_DIVIDER);
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

			//Включение видимости окна
			glfwShowWindow(windowID);
			//Заливка всего фона черным цветом
			GL11.glClearColor(0f, 0f, 0f, 0f);
			//Обновление кадра
			glfwSwapBuffers(windowID);
		} catch (Exception e) {
			Logger.println("OpenGL initialization failed", e, Logger.Type.ERROR);
			Loader.exit();
		}
	}

	public void loop() {
		glClear(GL_COLOR_BUFFER_BIT); //Очистка буфера (только буфера цвета, буфер глубины не используется)

		Global.game.render(); //Отрисовка в главном игровом классе (ссылка передается в движок при инициализации)
		Global.room.render((int) Camera.absoluteX, (int) Camera.absoluteY, getWidth(), getHeight()); //Отрисовка комнаты
		MouseHandler.draw(); //Отрисовка мыши
	}

	public void vsync(){
		glfwSwapBuffers(windowID);
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public long getWindowID() {
		return windowID;
	}

	public long getMonitorID() {
		return monitorID;
	}
}
