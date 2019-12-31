package tow.engine3.cycle;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import tow.engine.Global;
import tow.engine2.Loader;
import tow.engine.Vector2;
import tow.engine3.image.Camera;
import tow.engine3.title.Title;
import tow.engine3.io.KeyboardHandler;
import tow.engine2.io.Logger;
import tow.engine3.io.MouseHandler;
import tow.engine3.obj.Obj;
import tow.engine2.resources.settings.SettingsStorage;
import tow.engine2.image.Color;

import java.awt.Font;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Render{

	public String strAnalysis1 = "";//Вывод отладочных данных
	public String strAnalysis2 = "";

	private ArrayList<Title> titleArray = new ArrayList<Title>();

	private long windowID; //ID окна игры для LWJGL
	private int width;
	private int height;

	public void initGL(){

		//Создание и настройка окна
		try {
			if (SettingsStorage.DISPLAY.FULL_SCREEN){
				//TODO
				/*
				Display.setFullscreen(true);
				this.width = Display.getWidth();
				this.height = Display.getHeight();
				*/
			} else {
				if ( !glfwInit() )
					throw new IllegalStateException("Unable to initialize GLFW");

				// Configure GLFW
				glfwDefaultWindowHints(); // optional, the current window hints are already the default
				glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
				glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

				this.width = SettingsStorage.DISPLAY.WIDTH_SCREEN;
				this.height = SettingsStorage.DISPLAY.HEIGHT_SCREEN;
				windowID = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
			}

			// Get the thread stack and push a new frame
			try ( MemoryStack stack = stackPush() ) {
				IntBuffer pWidth = stack.mallocInt(1); // int*
				IntBuffer pHeight = stack.mallocInt(1); // int*

				// Get the window size passed to glfwCreateWindow
				glfwGetWindowSize(windowID, pWidth, pHeight);

				// Get the resolution of the primary monitor
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

				// Center the window
				glfwSetWindowPos(
						windowID,
						(vidmode.width() - pWidth.get(0)) / 2,
						(vidmode.height() - pHeight.get(0)) / 2
				);
			} // the stack frame is popped automatically

			//Make the OpenGL context current
			glfwMakeContextCurrent(windowID);
			// Enable v-sync
			glfwSwapInterval(1);

			// Make the window visible
			glfwShowWindow(windowID);

			GL.createCapabilities();
			glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

			/*
			Display.create();
			Display.setResizable(false);
			Display.setTitle(SettingsStorage.DISPLAY.WINDOW_NAME);
			Display.setVSyncEnabled(SettingsStorage.DISPLAY.SYNC != 0);
			*/
		} catch (Exception e) {
			//TODO: e.printStackTrace(); иначе просто null, разобраться с логирование исключений (желательно стека)
			Logger.println(e.getMessage(), Logger.Type.ERROR);
			Loader.exit();
		}
		//TODO:

		//Настройка графики
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void loop() {
		glClear(GL_COLOR_BUFFER_BIT);

		//заливка фона
		if (Global.room.background != null){
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
			GL11.glDisable(GL11.GL_TEXTURE_2D);

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
		GL11.glTranslatef(0, 0, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		new Color(Color.GRAY).bind();

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

		glfwSwapBuffers(windowID);
		//TODO: syncFPS(60) - не выполнять при корректном swapBuffer
		glfwPollEvents();



	}

	//TODO:
	public void sync(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		/*
		Display.update();
		if (SettingsStorage.DISPLAY.SYNC != 0)
			Display.sync(SettingsStorage.DISPLAY.SYNC);
			*/
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

	public void clearTitle(){
		titleArray.clear();
	}

	public void addTitle(Title t) {
		titleArray.add(t);
	}
}
