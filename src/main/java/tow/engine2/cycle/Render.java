package tow.engine2.cycle;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import tow.engine.Global;
import tow.engine2.Loader;
import tow.engine.Vector2;
import tow.engine2.image.Texture;
import tow.engine3.image.Camera;
import tow.engine3.title.Title;
import tow.engine3.io.KeyboardHandler;
import tow.engine2.io.Logger;
import tow.engine3.io.MouseHandler;
import tow.engine3.obj.Obj;
import tow.engine2.resources.settings.SettingsStorage;
import tow.engine2.image.Color;

import java.awt.Font;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Render{

	public String strAnalysis1 = "";//Вывод отладочных данных
	public String strAnalysis2 = "";

	private ArrayList<Title> titleArray = new ArrayList<Title>();

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
		glClear(GL_COLOR_BUFFER_BIT);

		//заливка фона
		if (Global.room.background != null){
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			Obj backgroundObj = new Obj(0, 0, 0, Global.room.background);
			backgroundObj.position.absolute = false;

			int size = Global.room.background.getWidth();//Размер плитки с фоном
			int startX = (int) ((Camera.absoluteX- Global.engine.render.getWidth()/2) -
					(Camera.absoluteX- Global.engine.render.getWidth()/2)%size);
			int startY = (int) ((Camera.absoluteY- Global.engine.render.getHeight()/2) -
					(Camera.absoluteX- Global.engine.render.getHeight()/2)%size);

			for (int dy = startY; dy<=startY+getHeight()+size*2; dy+=size){
				for (int dx = startX; dx<=startX+getWidth()+size*2; dx+=size){
					Vector2<Integer> relativePosition = Camera.toRelativePosition(new Vector2<>(dx, dy));
					backgroundObj.position.x = relativePosition.x;
					backgroundObj.position.y = relativePosition.y;
					backgroundObj.draw();
				}
			}
		} else {
			GL11.glLoadIdentity();
			GL11.glTranslatef(0, 0, 0);

			Texture.unbind();
			new Color(Color.WHITE).bind();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(width, 0);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(0, height);
			GL11.glEnd();
		}

		//Заливка фона за границами карты
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Color.GRAY.bind();

		int fillW = (width - Global.room.width)/2;
		int fillH = (height - Global.room.height)/2;

		if (Global.engine.render.getWidth() > Global.room.width){
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(fillW, 0);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(fillW, height);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(0, height);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(width-fillW, 0);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(width, 0);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(width-fillW, height);
			GL11.glEnd();
		}
		if (Global.engine.render.getHeight() > Global.room.height){
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(width, 0);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(width, fillH);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(0, fillH);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0, height-fillH);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(width, height-fillH);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(0, height);
			GL11.glEnd();
		}


		//Обновить главный игровой класс при необходимости
		Global.game.render();

		//Отрисовка объектов
		Global.room.mapControl.render((int) Camera.absoluteX, (int) Camera.absoluteY, getWidth(), getHeight());

		//Отрисвока надписей
		addTitle(new Title(1, getHeight()-27,strAnalysis1, Color.black, 12, Font.BOLD));
		addTitle(new Title(1, getHeight()-15,strAnalysis2, Color.black, 12, Font.BOLD));
		for (int i = 0; i < titleArray.size(); i++){
			titleArray.get(i).draw();
		}

		//Отрисовка мыши
		MouseHandler.draw();

		//TODO: эвенты приходят в PollEvents
		//Очистка потока ввода
		MouseHandler.update();
		KeyboardHandler.update();

		glfwPollEvents();
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

	public void clearTitle(){
		titleArray.clear();
	}

	public void addTitle(Title t) {
		titleArray.add(t);
	}
}
