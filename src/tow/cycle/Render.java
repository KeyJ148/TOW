package tow.cycle;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import tow.Global;
import tow.image.Sprite;
import tow.title.Title;

public class Render{
	
	public String strAnalysis1 = "";//Вывод отладочных данных
	public String strAnalysis2 = "";
	
	private ArrayList<Title> titleArray = new ArrayList<Title>();
	
	private int width = 1280;
	private int height = 720;
	
	private Sprite background;
	
	public void initGL(){
		//Создание и настройка окна
		try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
            Display.setResizable(true);
            Display.setTitle(Global.setting.WINDOW_NAME);
            Display.setVSyncEnabled(Global.setting.SYNC != 0);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
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
		if (background == null) background = new Sprite(Global.background);
		int dxf,dyf;
		int size = background.getWidth();//Размер плитки с фоном
		int startX = (int) ((Global.cameraX-Global.cameraXView) - (Global.cameraX-Global.cameraXView)%size);
		int startY = (int) ((Global.cameraY-Global.cameraYView) - (Global.cameraY-Global.cameraYView)%size);
		
		for (int dy = startY; dy<=startY+getHeight()+size; dy+=size){
			for (int dx = startX; dx<=startX+getWidth()+size; dx+=size){
				dxf = (int) Math.round(Global.cameraXView - (Global.cameraX - dx));
				dyf = (int) Math.round(Global.cameraYView - (Global.cameraY - dy));
				background.draw(dxf,dyf,0);
			}
		}
		
		//Отрисовка объектов
		Global.mapControl.render((int) Global.cameraX, (int) Global.cameraY, getWidth(), getHeight());
		
		
		//Отрисвока надписей
		addTitle(new Title(1, getHeight()-27,strAnalysis1, Color.black, 12, Font.BOLD));
		addTitle(new Title(1, getHeight()-15,strAnalysis2, Color.black, 12, Font.BOLD));
		for (int i = 0; i < titleArray.size(); i++){
			titleArray.get(i).draw();
		}
		
		
		//Считывание потока ввода
		Global.mouseHandler.update();
		Global.keyboardHandler.update();
		
		//Отрисовка мыши
		Global.mouseHandler.draw();
	}
	
	public void sync(){
		Display.update();
		if (Global.setting.SYNC != 0)
			Display.sync(Global.setting.SYNC);
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
