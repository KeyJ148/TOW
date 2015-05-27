package main.net;

import main.Global;
import main.image.Animation;
import main.image.Sprite;

public class LinkCS {
	
	public void initSprite(){
		Global.road_g = new Sprite("image/Backwall/road_g.png");
		Global.road_g_fork = new Sprite("image/Backwall/road_g_fork.png");
		Global.road_g_turn = new Sprite("image/Backwall/road_g_turn.png");
		Global.road_g_inter = new Sprite("image/Backwall/road_g_inter.png");
		Global.road_a = new Sprite("image/Backwall/road_a.png");
		Global.road_a_g = new Sprite("image/Backwall/road_a_g.png");
		Global.road_a_fork = new Sprite("image/Backwall/road_a_fork.png");
		Global.road_a_inter_big = new Sprite("image/Backwall/road_a_inter_big.png");
		
		Global.b_default = new Sprite("image/Bullet/default.png");
		Global.b_steel = new Sprite("image/Bullet/steel.png");
		Global.b_mortar = new Sprite("image/Bullet/mortar.png");
		Global.b_gunfury = new Sprite("image/Bullet/gunfury.png");
		Global.b_mass = new Sprite("image/Bullet/massgun.png");
		Global.b_massgun_small = new Sprite("image/Bullet/massgun_small.png");
		
		Global.defaultgun = new Sprite("image/Gun/defaultgun.png");
		Global.doublegun = new Sprite("image/Gun/doublegun.png");
		Global.gunfury = new Sprite("image/Gun/gunfury.png");
		Global.mortar = new Sprite("image/Gun/mortar.png");
		Global.rocketgun = new Sprite("image/Gun/rocketgun.png");
		Global.biggun = new Sprite("image/Gun/massgun.png");
		Global.powergun = new Sprite("image/Gun/powergun.png");
		
		Global.player_color = new Sprite("image/Sys/player_color.png");
		Global.error = new Sprite("image/Sys/error.png");
		Global.player_sys = new Sprite("image/Sys/player_sys.png");
		
		Global.home1 = new Sprite("image/Wall/home1.png");
		Global.home2 = new Sprite("image/Wall/home2.png");
		Global.home3 = new Sprite("image/Wall/home3.png");
		Global.home4 = new Sprite("image/Wall/home4.png");
		Global.home5 = new Sprite("image/Wall/home5.png");
		Global.home6 = new Sprite("image/Wall/home6.png");
		Global.home7 = new Sprite("image/Wall/home7.png");
		Global.home8 = new Sprite("image/Wall/home8.png");
		Global.home9 = new Sprite("image/Wall/home9.png");
		Global.home10 = new Sprite("image/Wall/home10.png");
		Global.home11 = new Sprite("image/Wall/home11.png");
		Global.home12 = new Sprite("image/Wall/home12.png");
		Global.home13 = new Sprite("image/Wall/home13.png");
		Global.grayrock1 = new Sprite("image/Wall/grayrock1.png");
		Global.grayrock2 = new Sprite("image/Wall/grayrock2.png");
		Global.grayrock3 = new Sprite("image/Wall/grayrock3.png");
		Global.grayrock4 = new Sprite("image/Wall/grayrock4.png");
		Global.grayrock5 = new Sprite("image/Wall/grayrock5.png");
		Global.grayrock6 = new Sprite("image/Wall/grayrock6.png");
		Global.tree = new Sprite("image/Wall/tree.png");
		
		Global.box_armor = new Sprite("image/Gaming/box_armor.png");
		Global.box_gun = new Sprite("image/Gaming/box_gun.png");
		Global.box_bullet = new Sprite("image/Gaming/box_bullet.png");
		Global.box_health = new Sprite("image/Gaming/box_health.png");
		
		Global.c_default = new Animation("animation/Corps/Default",-1,2);
		Global.c_fortified = new Animation("animation/Corps/Fortified",-1,2);
		Global.c_elephant = new Animation("animation/Corps/Elephant",-1,1);
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

