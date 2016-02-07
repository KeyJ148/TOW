package tow.net.client;

import tow.Global;
import tow.image.Sprite;
import tow.map.Home;
import tow.map.Road;
import tow.player.Player;
import tow.player.enemy.Enemy;

public class TCPMapLoader {
	
	public void initMap(){
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map start.");
		
		//Скачивание карты
		String s = "";
			
		if (Global.game.restart){
			Global.tcpControl.send("-1 ");
		}
			
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Wait loading map.");
		while(true){
			s = Global.tcpControl.read();
			if (s.equals("6 ")) break;
		}
			
		//Разрешение карты(для камеры)
		s = downloadMap();
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map size complite.");
		Global.widthMap = Integer.parseInt(Global.parsString(s,1));
		Global.heightMap = Integer.parseInt(Global.parsString(s,2));
		Global.background = new Sprite("res/image/Background/" + Global.parsString(s,3) + ".png");
		Global.mapControl.init(Global.widthMap, Global.heightMap);
		
		//Загрузка объектов
		int x,y,direction;
		String sprite;
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map object start.");
		while(true){
			s = downloadMap();
			
			if (s.equals("8 ")){
				if (Global.setting.DEBUG_CONSOLE) System.out.println("Download map object complite.");
				break;
			}
			x = Integer.parseInt(Global.parsString(s,1));
			y = Integer.parseInt(Global.parsString(s,2));
			direction = Integer.parseInt(Global.parsString(s,3));
			sprite = Global.parsString(s,4);
			switch(Global.getType(sprite)){
				case "Home": new Home(x,y,direction,Global.getSprite(sprite)); break;
				case "Road": new Road(x,y,direction,Global.getSprite(sprite)); break;
			}
		}
		
		genTank();
	}
	
	//получение данных
	public void genTank(){
		Global.tcpControl.send("-3 " + Global.name);//отправка имени
		String s = downloadMap();//получение кор танка
		double x = (double) Integer.parseInt(s.substring(0,s.indexOf(' ')));
		double y = (double) Integer.parseInt(s.substring(s.indexOf(' ')+1));
		s = downloadMap();//получение кол-во игрков
		Global.peopleMax = Integer.parseInt(s.substring(0,s.indexOf(' ')));
		Global.enemy = new Enemy[Global.peopleMax-1];
		Global.player = new Player(x,y,Math.random()*360);
			
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Generation tank complite.");
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
