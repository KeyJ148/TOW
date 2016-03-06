package tow;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import tow.image.Sprite;

public class Render{
	
	public ArrayList<Title> titleArray = new ArrayList<Title>();
	public String strAnalysis1 = "";//Вывод отладочных данных
	public String strAnalysis2 = "";
	
	public Sprite cursor;
	public int mouseX;
	public int mouseY;
	public int mouseWidth;
	public int mouseHeight;
	
	private int width = 1280;
	private int height = 720;
	
	public void init(){
		/*
		//Отключение стнадартного курсора
		Cursor noCursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon(new byte[0])).getImage(), new Point(0,0), "noCursor");
		setCursor(noCursor);
		
		//Добавление своего курсора
		cursor = Global.cursor_aim;
		mouseWidth = cursor.getWidth();
		mouseHeight = cursor.getHeight();
		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
			public void mouseDragged(MouseEvent e){
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		*/
		
		try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
            //Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
 
        GL11.glEnable(GL11.GL_TEXTURE_2D);               
         
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
         
        // enable alpha blending
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
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		/*
		//заливка фона
		int dxf,dyf;
		int size = Global.background.getWidth();//Размер плитки с фоном
		int startX = (int) ((Global.cameraX-Global.cameraXView) - (Global.cameraX-Global.cameraXView)%size);
		int startY = (int) ((Global.cameraY-Global.cameraYView) - (Global.cameraY-Global.cameraYView)%size);
		
		for (int dy = startY; dy<=startY+getHeight()+size; dy+=size){
			for (int dx = startX; dx<=startX+getWidth()+size; dx+=size){
				dxf = (int) Math.round(Global.cameraXView - (Global.cameraX - dx));
				dyf = (int) Math.round(Global.cameraYView - (Global.cameraY - dy));
				Global.background.draw(dxf,dyf,0);
			}
		}
		*/
		//Отрисовка объектов
		Global.mapControl.render((int) Global.cameraX, (int) Global.cameraY, getWidth(), getHeight());
		/*
		//Отрисвока надписей
		addTitle(1,getHeight()-15,strAnalysis1, Color.BLACK, 12, Font.BOLD);
		addTitle(1,getHeight()-3,strAnalysis2, Color.BLACK, 12, Font.BOLD);
		for (int i = 0; i < titleArray.size(); i++){
			titleArray.get(i).draw(g);
		}
		*/
		//Отрисовка курсора
		//cursor.draw(g, mouseX-mouseWidth/2, mouseY-mouseHeight/2, 0.0);
		
		Display.update();
		Display.sync(20000);
	}
	
	public int getWidth(){
		if (Global.setting.FULL_SCREEN){
			return width-2;
		} else {
			return width;
		}
	}
	
	public int getHeight(){
		return height;
	}
	
	public void clearTitle(){
		titleArray.clear();
	}

	public void addTitle(int x, int y, String str) {
		titleArray.add(new Title(x, y, str));
	}
	
	public void addTitle(int x, int y, String str, Color c) {
		titleArray.add(new Title(x, y, str, c));
	}
	
	public void addTitle(int x, int y, String str, int size) {
		titleArray.add(new Title(x, y, str, size));
	}
	
	public void addTitle(int x, int y, String str, Color c, int size, int font) {
		titleArray.add(new Title(x, y, str, c, size, font));
	}
}
