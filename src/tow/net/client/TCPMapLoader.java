package tow.net.client;

import tow.Global;
import tow.image.TextureHandler;
import tow.image.TextureManager;
import tow.map.Home;
import tow.map.Road;
import tow.map.Tree;
import tow.player.Player;
import tow.player.enemy.Enemy;

public class TCPMapLoader {
	
	public void initMap(){
		if (Global.setting.DEBUG_CONSOLE) Global.p("Download map start.");
		
		//���������� �����
		String s = "";
			
		if (Global.game.restart){
			Global.tcpControl.send("-1 ");
		}
			
		if (Global.setting.DEBUG_CONSOLE) Global.p("Wait loading map.");
		while(true){
			s = Global.tcpControl.read();
			if (s.equals("6 ")) break;
		}
			
		//���������� �����(��� ������)
		s = downloadMap();
		if (Global.setting.DEBUG_CONSOLE) Global.p("Download map size complite.");
		Global.widthMap = Integer.parseInt(Global.parsString(s,1));
		Global.heightMap = Integer.parseInt(Global.parsString(s,2));
		TextureManager.background = new TextureHandler("res/image/Background/" + Global.parsString(s,3) + ".png");
		Global.mapControl.init(Global.widthMap, Global.heightMap);
		
		//�������� ��������
		int x,y,direction;
		String sprite;
		if (Global.setting.DEBUG_CONSOLE) Global.p("Download map object start.");
		while(true){
			s = downloadMap();
			
			if (s.equals("8 ")){
				if (Global.setting.DEBUG_CONSOLE) Global.p("Download map object complite.");
				break;
			}
			x = Integer.parseInt(Global.parsString(s,1));
			y = Integer.parseInt(Global.parsString(s,2));
			direction = Integer.parseInt(Global.parsString(s,3));
			sprite = Global.parsString(s,4);
			switch(TextureManager.getType(sprite)){
				case "Home": new Home(x,y,direction,TextureManager.getTexture(sprite)); break;
				case "Road": new Road(x,y,direction,TextureManager.getTexture(sprite)); break;
				case "Tree": new Tree(x,y,direction,TextureManager.getTexture(sprite)); break;
			}
		}
		
		genTank();
	}
	
	//��������� ������
	public void genTank(){
		Global.tcpControl.send("-3 " + Global.name);//�������� �����
		String s = downloadMap();//��������� ��� �����
		double x = (double) Integer.parseInt(s.substring(0,s.indexOf(' ')));
		double y = (double) Integer.parseInt(s.substring(s.indexOf(' ')+1));
		s = downloadMap();//��������� ���-�� ������
		Global.peopleMax = Integer.parseInt(s.substring(0,s.indexOf(' ')));
		Global.enemy = new Enemy[Global.peopleMax-1];
		Global.player = new Player(x,y,Math.random()*360);
			
		if (Global.setting.DEBUG_CONSOLE) Global.p("Generation tank complite.");
	}
	
	public String downloadMap(){
		String s;
		do {
			s = Global.tcpControl.read();
			if (Integer.parseInt(Global.parsString(s,1)) == 8){
				return s;
			}
		}while(Integer.parseInt(Global.parsString(s,1)) != 7);
		
		return s.substring(2);
	}

}
