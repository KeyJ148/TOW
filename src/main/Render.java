package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import main.image.DepthVector;

public class Render extends Canvas{
	
	public ArrayList<Title> titleArray = new ArrayList<Title>();
	public String strAnalysis1 = "";//Вывод отладочных данных
	public String strAnalysis2 = "";
	
	private static final long serialVersionUID = 1L;
	

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
		Global.game.analyzer.drawBack = 0;
		Global.game.analyzer.background = 0;
		Image back = Global.background.getImage();
		int dxf,dyf;
		int size = 64;//Размер плитки с фоном
		for (int dy = 0; dy<=Global.heightMap; dy+=size){
			for (int dx = 0; dx<=Global.widthMap; dx+=size){
				Global.game.analyzer.background++;
				dxf = (int) Math.round(Global.cameraXView - (Global.cameraX - dx));
				dyf = (int) Math.round(Global.cameraYView - (Global.cameraY - dy));
				if (needDraw(dxf, dyf, size, size)){
					Global.game.analyzer.drawBack++;
					g.drawImage(back,dxf,dyf,null);
				}
			}
		}
		
		//Сортировка массивов по глубине объектов в них
		for (int i=0; i<Global.depth.size(); i++){
			for (int j=0; j<Global.depth.size()-1; j++){
				DepthVector dv1 = (DepthVector) Global.depth.get(j);
				DepthVector dv2 = (DepthVector) Global.depth.get(j+1);
				if (dv1.depth < dv2.depth){
					Global.depth.set(j, dv2);
					Global.depth.set(j+1, dv1);
				}
			}
		}
		
		//Отрисовка объектов	
		Global.game.analyzer.draw = 0;
		for (int i=0; i<Global.depth.size(); i++){
			DepthVector dv = (DepthVector) Global.depth.get(i);
			for (int j=0; j<dv.number.size(); j++){
				if (Global.obj.get(i) != null){
					try{
						Obj obj = (Obj) Global.getObj((long) dv.number.get(j));
						obj.draw(g);
					}catch (NullPointerException e){
						Global.error("Draw object null");
					}
				}
			}
		}
		
		//Отрисвока надписей
		addTitle(1,Global.setting.HEIGHT_SCREEN-4,strAnalysis1, Color.BLACK, 12, Font.BOLD);
		addTitle(1,Global.setting.HEIGHT_SCREEN+9,strAnalysis2, Color.BLACK, 12, Font.BOLD);
		for (int i = 0; i < titleArray.size(); i++){
			titleArray.get(i).draw(g);
		}
		
		
		//Магия [ON]
		g.dispose();
		bs.show();
		//Магия [OFF]
	}
	
	public boolean needDraw(int xView, int yView, int width, int height){
		int error = 10;//Погрешность в пикселях
		
		int size;
		if (width > height){
			size = width;
		} else{
			size = height;
		}
		
		if ((xView + size*2 + error > 0) && (xView - size*2 - error < Global.setting.WIDTH_SCREEN) && (yView - size*2 - error < Global.setting.HEIGHT_SCREEN) && (yView + size*2 + error > 0)){
			Global.game.analyzer.draw++;
			return true;
		}
		return false;
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
