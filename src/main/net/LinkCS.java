package main.net;

import main.Global;
import main.image.Animation;
import main.image.Sprite;

public class LinkCS {
	
	private String pathImage = "res/image/";
	private String pathAnim = "res/animation/";
	
	public void initSprite(){
		Global.road_g = new Sprite(pathImage + "Backwall/road_g.png");
		Global.road_g_fork = new Sprite(pathImage + "Backwall/road_g_fork.png");
		Global.road_g_turn = new Sprite(pathImage + "Backwall/road_g_turn.png");
		Global.road_g_inter = new Sprite(pathImage + "Backwall/road_g_inter.png");
		Global.road_a = new Sprite(pathImage + "Backwall/road_a.png");
		Global.road_a_g = new Sprite(pathImage + "Backwall/road_a_g.png");
		Global.road_a_fork = new Sprite(pathImage + "Backwall/road_a_fork.png");
		Global.road_a_inter_big = new Sprite(pathImage + "Backwall/road_a_inter_big.png");
		
		Global.b_default = new Sprite(pathImage + "Bullet/default.png");
		Global.b_steel = new Sprite(pathImage + "Bullet/steel.png");
		Global.b_mortar = new Sprite(pathImage + "Bullet/mortar.png");
		Global.b_gunfury = new Sprite(pathImage + "Bullet/gunfury.png");
		Global.b_mass = new Sprite(pathImage + "Bullet/massgun.png");
		Global.b_massgun_small = new Sprite(pathImage + "Bullet/massgun_small.png");
		
		Global.defaultgun = new Sprite(pathImage + "Gun/defaultgun.png");
		Global.doublegun = new Sprite(pathImage + "Gun/doublegun.png");
		Global.gunfury = new Sprite(pathImage + "Gun/gunfury.png");
		Global.mortar = new Sprite(pathImage + "Gun/mortar.png");
		Global.rocketgun = new Sprite(pathImage + "Gun/rocketgun.png");
		Global.biggun = new Sprite(pathImage + "Gun/massgun.png");
		Global.powergun = new Sprite(pathImage + "Gun/powergun.png");
		
		Global.player_color = new Sprite(pathImage + "Sys/player_color.png");
		Global.error = new Sprite(pathImage + "Sys/error.png");
		Global.player_sys = new Sprite(pathImage + "Sys/player_sys.png");
		Global.cursor_aim = new Sprite(pathImage + "Sys/cursor_aim.png");
		
		Global.home1 = new Sprite(pathImage + "Wall/home1.png");
		Global.home2 = new Sprite(pathImage + "Wall/home2.png");
		Global.home3 = new Sprite(pathImage + "Wall/home3.png");
		Global.home4 = new Sprite(pathImage + "Wall/home4.png");
		Global.home5 = new Sprite(pathImage + "Wall/home5.png");
		Global.home6 = new Sprite(pathImage + "Wall/home6.png");
		Global.home7 = new Sprite(pathImage + "Wall/home7.png");
		Global.home8 = new Sprite(pathImage + "Wall/home8.png");
		Global.home9 = new Sprite(pathImage + "Wall/home9.png");
		Global.home10 = new Sprite(pathImage + "Wall/home10.png");
		Global.home11 = new Sprite(pathImage + "Wall/home11.png");
		Global.home12 = new Sprite(pathImage + "Wall/home12.png");
		Global.home13 = new Sprite(pathImage + "Wall/home13.png");
		Global.grayrock1 = new Sprite(pathImage + "Wall/grayrock1.png");
		Global.grayrock2 = new Sprite(pathImage + "Wall/grayrock2.png");
		Global.grayrock3 = new Sprite(pathImage + "Wall/grayrock3.png");
		Global.grayrock4 = new Sprite(pathImage + "Wall/grayrock4.png");
		Global.grayrock5 = new Sprite(pathImage + "Wall/grayrock5.png");
		Global.grayrock6 = new Sprite(pathImage + "Wall/grayrock6.png");
		Global.tree = new Sprite(pathImage + "Wall/tree.png");
		
		Global.box_armor = new Sprite(pathImage + "Gaming/box_armor.png");
		Global.box_gun = new Sprite(pathImage + "Gaming/box_gun.png");
		Global.box_bullet = new Sprite(pathImage + "Gaming/box_bullet.png");
		Global.box_health = new Sprite(pathImage + "Gaming/box_health.png");
		
		Global.c_default = new Animation(pathAnim + "Corps/Default",-1,2);
		Global.c_fortified = new Animation(pathAnim + "Corps/Fortified",-1,2);
		Global.c_elephant = new Animation(pathAnim + "Corps/Elephant",-1,1);
	}
	
	public Sprite getSprite(String s){
		switch(s){
			case "home1": return Global.home1;
			case "home2": return Global.home2;
			case "home3": return Global.home3;
			case "home4": return Global.home4;
			case "home5": return Global.home5;
			case "home6": return Global.home6;
			case "home7": return Global.home7;
			case "home8": return Global.home8;
			case "home9": return Global.home9;
			case "home10": return Global.home10;
			case "home11": return Global.home11;
			case "home12": return Global.home12;
			case "home13": return Global.home13;
			
			case "road_g": return Global.road_g;
			case "road_g_fork": return Global.road_g_fork;
			case "road_g_turn": return Global.road_g_turn;
			case "road_g_inter": return Global.road_g_inter;
			case "road_a": return Global.road_a;
			case "road_a_g": return Global.road_a_g;
			case "road_a_fork": return Global.road_a_fork;
			case "road_a_inter_big": return Global.road_a_inter_big;
			
			case "player_color": return Global.player_color;
			
			case "b_default": return Global.b_default;
			case "b_steel": return Global.b_steel;
			case "b_mortar": return Global.b_mortar;
			case "b_gunfury": return Global.b_gunfury;
			case "b_massgun": return Global.b_mass;
			case "b_massgun_small": return Global.b_massgun_small;
		}
		return Global.error;
	}
	
	public String parsString(String s,int numFind){
		int numWord = 0;
		int numSpace = -1;
		int k = 0;
		do{
			if (s.charAt(k) == ' '){
				numWord++;
				if (numWord != numFind){
					numSpace = k;
				} else {
					return s.substring(numSpace+1,k);
				}
			}
			if (k == s.length()-1){
				return s.substring(numSpace+1,k+1);
			}
			k++;
		}while(true);
	}
}

