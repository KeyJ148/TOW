package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.image.Sprite;

public class Render extends Canvas{
	
	public ArrayList<Title> titleArray = new ArrayList<Title>();
	public String strAnalysis1 = "";//Вывод отладочных данных
	public String strAnalysis2 = "";
	
	public Sprite cursor;
	public int mouseX;
	public int mouseY;
	public int mouseWidth;
	public int mouseHeight;
	
	private static final long serialVersionUID = 1L;
	
	public void init(){
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
	}
	
	public void loop() {
		//Включение двойной буферизации
		BufferStrategy bs = getBufferStrategy(); 
		if (bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
        //Настройки окна
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(new Color(24,116,0));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//заливка фона
		Image back = Global.background.getImage();
		int dxf,dyf;
		int size = 64;//Размер плитки с фоном
		for (int dy = 0; dy<=Global.heightMap; dy+=size){
			for (int dx = 0; dx<=Global.widthMap; dx+=size){
				dxf = (int) Math.round(Global.cameraXView - (Global.cameraX - dx));
				dyf = (int) Math.round(Global.cameraYView - (Global.cameraY - dy));
				g.drawImage(back,dxf,dyf,null);
			}
		}
		
		//Отрисовка объектов
		Global.mapControl.render((int) Global.cameraX, (int) Global.cameraY, getWidth(), getHeight(), g);
		
		//Отрисвока надписей
		addTitle(1,getHeight()-15,strAnalysis1, Color.BLACK, 12, Font.BOLD);
		addTitle(1,getHeight()-3,strAnalysis2, Color.BLACK, 12, Font.BOLD);
		for (int i = 0; i < titleArray.size(); i++){
			titleArray.get(i).draw(g);
		}
		
		//Отрисовка курсора
		cursor.draw(g, mouseX-mouseWidth/2, mouseY-mouseHeight/2, 0.0);
		
		//Магия [ON]
		g.dispose();
		bs.show();
		//Магия [OFF]
	}
	
	@Override
	public int getWidth(){
		if (Global.setting.FULL_SCREEN){
			return super.getWidth()-2;
		} else {
			return super.getWidth();
		}
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
