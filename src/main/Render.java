package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import main.image.DepthVector;

public class Render extends Canvas{
	
	//public ArrayList<Title> titleArray;
	
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
		Image back = Global.background.getImage();
		int dxf,dyf;
		for (int dy = 0; dy<=Global.heightMap; dy+=64){
			for (int dx = 0; dx<=Global.widthMap; dx+=64){
				dxf = (int) Math.round(Global.cameraXView - (Global.cameraX - dx));
				dyf = (int) Math.round( Global.cameraYView - (Global.cameraY - dy));
				g.drawImage(back,dxf,dyf,null);
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
		AffineTransform at = new AffineTransform(); 
		g.setTransform(at);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font(null,Font.PLAIN,12));
		if (!Global.player.getDestroy()){
			g.drawString(Global.name,Global.player.nameX,Global.player.nameY);
		}
		for(int i = 0;i<Global.enemy.length;i++){
			try{
				if (!Global.enemy[i].getDestroy()){
					g.drawString(Global.enemy[i].name,Global.enemy[i].nameX,Global.enemy[i].nameY);
				}
			}catch(NullPointerException e){}
		}
		g.setFont(new Font(null,Font.BOLD,20));
		if (!Global.player.getDestroy()){
			long hp = Math.round(Global.player.getArmor().getHp());
			long hpMax = Math.round(Global.player.getArmor().getHpMax());
			g.drawString("HP: " + hp + "/" + hpMax,1,16);
		}
		if (Global.setting.DEBUG_MONITOR_FPS){
			g.setFont(new Font(null,Font.PLAIN,12));
			g.drawString(Global.game.monitorStrFPS,1,Global.setting.HEIGHT_SCREEN+9);
		}
		
		//Магия [ON]
		g.dispose();
		bs.show();
		//Магия [OFF]
	}

	private void drawTitle() {
		
	}
}
