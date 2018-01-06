package engine.cycle;

import engine.Global;
import engine.Loader;
import engine.Vector2;
import engine.image.Camera;
import engine.inf.title.Title;
import engine.io.KeyboardHandler;
import engine.io.Logger;
import engine.io.MouseHandler;
import engine.obj.Obj;
import engine.setting.SettingStorage;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import java.awt.*;
import java.util.ArrayList;

public class Render{

	public String strAnalysis1 = "";//Вывод отладочных данных
	public String strAnalysis2 = "";

	private ArrayList<Title> titleArray = new ArrayList<Title>();

	private int width;
	private int height;

	public void initGL(){

		//Создание и настройка окна
		try {
			if (SettingStorage.Display.FULL_SCREEN){
				Display.setFullscreen(true);
				this.width = Display.getWidth();
				this.height = Display.getHeight();
			} else {
				this.width = SettingStorage.Display.WIDTH_SCREEN;
				this.height = SettingStorage.Display.HEIGHT_SCREEN;
				Display.setDisplayMode(new DisplayMode(width, height));
			}
			Display.create();
			Display.setResizable(false);
			Display.setTitle(SettingStorage.Display.WINDOW_NAME);
			Display.setVSyncEnabled(SettingStorage.Display.SYNC != 0);
		} catch (LWJGLException e) {
			Logger.println(e.getMessage(), Logger.Type.ERROR);
			Loader.exit();
		}

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
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

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
					Vector2<Integer> relativePosition = Camera.toRelativePosition(new Vector2(dx, dy));
					backgroundObj.position.x = relativePosition.x;
					backgroundObj.position.y = relativePosition.y;
					backgroundObj.draw();
				}
			}
		} else {
			GL11.glLoadIdentity();
			GL11.glTranslatef(0, 0, 0);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Color.white.bind();

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
		Color.gray.bind();
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

		//Отрисовка интерфейса
		Global.infMain.draw();

		//Отрисвока надписей
		addTitle(new Title(1, getHeight()-27,strAnalysis1, Color.black, 12, Font.BOLD));
		addTitle(new Title(1, getHeight()-15,strAnalysis2, Color.black, 12, Font.BOLD));
		for (int i = 0; i < titleArray.size(); i++){
			titleArray.get(i).draw();
		}


		//Считывание потока ввода
		MouseHandler.update();
		KeyboardHandler.update();

		//Отрисовка мыши
		MouseHandler.draw();
	}

	public void sync(){
		Display.update();
		if (SettingStorage.Display.SYNC != 0)
			Display.sync(SettingStorage.Display.SYNC);
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public void clearTitle(){
		titleArray.clear();
	}

	public void addTitle(Title t) {
		titleArray.add(t);
	}
}
