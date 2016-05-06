package tow.image;

import java.io.File;

public class TextureManager {

	public static TextureHandler background;
	
	public static TextureHandler road_g;
	public static TextureHandler road_g_fork;
	public static TextureHandler road_g_turn;
	public static TextureHandler road_g_inter;
	public static TextureHandler road_a;
	public static TextureHandler road_a_g;
	public static TextureHandler road_a_fork;
	public static TextureHandler road_a_inter;
	
	public static TextureHandler b_default;
	public static TextureHandler b_steel;
	public static TextureHandler b_mass;
	public static TextureHandler b_mass_small;
	public static TextureHandler b_square;
	public static TextureHandler b_fury;
	public static TextureHandler b_streamlined; 
	public static TextureHandler b_patron; 
	public static TextureHandler b_vampire; 
	
	public static TextureHandler g_default;
	public static TextureHandler g_double;
	public static TextureHandler g_big;
	public static TextureHandler g_power;
	public static TextureHandler g_fury;
	public static TextureHandler g_mortar;
	public static TextureHandler g_rocketd; 
	public static TextureHandler g_kkp; 
	public static TextureHandler g_sniper; 
	public static TextureHandler g_vampire; 
	
	public static TextureHandler home1;
	public static TextureHandler home2;
	public static TextureHandler home3;
	public static TextureHandler home4;
	public static TextureHandler home5;
	public static TextureHandler home6;
	public static TextureHandler home7;
	public static TextureHandler home8;
	public static TextureHandler home9;
	public static TextureHandler home10;
	public static TextureHandler home11;
	public static TextureHandler home12;
	public static TextureHandler home13;
	public static TextureHandler grayrock1;
	public static TextureHandler grayrock2;
	public static TextureHandler grayrock3;
	public static TextureHandler grayrock4;
	public static TextureHandler grayrock5;
	public static TextureHandler grayrock6;
	public static TextureHandler tree;
	
	public static TextureHandler error;
	public static TextureHandler player_sys;
	public static TextureHandler player_color;
	public static TextureHandler cursor_aim;
	
	public static TextureHandler box_armor;
	public static TextureHandler box_gun;
	public static TextureHandler box_bullet;
	public static TextureHandler box_health;
	
	public static TextureHandler[] a_default;
	public static TextureHandler[] a_fortified;
	public static TextureHandler[] a_elephant;
	public static TextureHandler[] a_fury; 
	public static TextureHandler[] a_mite; 
	public static TextureHandler[] a_vampire; 
	
	//Для функций
	public static final String pathImage = "res/image/";
	public static final String pathAnim = "res/animation/";
	
	//Загрузка всех текстур
	public static void initTexture(){
		TextureManager.road_g = new TextureHandler(pathImage + "Backwall/road_g.png");
		TextureManager.road_g_fork = new TextureHandler(pathImage + "Backwall/road_g_fork.png");
		TextureManager.road_g_turn = new TextureHandler(pathImage + "Backwall/road_g_turn.png");
		TextureManager.road_g_inter = new TextureHandler(pathImage + "Backwall/road_g_inter.png");
		TextureManager.road_a = new TextureHandler(pathImage + "Backwall/road_a.png");
		TextureManager.road_a_g = new TextureHandler(pathImage + "Backwall/road_a_g.png");
		TextureManager.road_a_fork = new TextureHandler(pathImage + "Backwall/road_a_fork.png");
		TextureManager.road_a_inter = new TextureHandler(pathImage + "Backwall/road_a_inter.png");
			
		TextureManager.b_default = new TextureHandler(pathImage + "Bullet/b_default.png");
		TextureManager.b_steel = new TextureHandler(pathImage + "Bullet/b_steel.png");
		TextureManager.b_square = new TextureHandler(pathImage + "Bullet/b_square.png");
		TextureManager.b_fury = new TextureHandler(pathImage + "Bullet/b_fury.png");
		TextureManager.b_mass = new TextureHandler(pathImage + "Bullet/b_mass.png");
		TextureManager.b_mass_small = new TextureHandler(pathImage + "Bullet/b_mass_small.png");
		TextureManager.b_vampire = new TextureHandler(pathImage + "Bullet/b_vampire.png"); 
		TextureManager.b_patron = new TextureHandler(pathImage + "Bullet/b_patron.png"); 
		TextureManager.b_streamlined = new TextureHandler(pathImage + "Bullet/b_streamlined.png"); 
			
		TextureManager.g_default = new TextureHandler(pathImage + "Gun/g_default.png");
		TextureManager.g_double = new TextureHandler(pathImage + "Gun/g_double.png");
		TextureManager.g_fury = new TextureHandler(pathImage + "Gun/g_fury.png");
		TextureManager.g_mortar = new TextureHandler(pathImage + "Gun/g_mortar.png");
		TextureManager.g_rocketd = new TextureHandler(pathImage + "Gun/g_rocketd.png"); 
		TextureManager.g_big = new TextureHandler(pathImage + "Gun/g_mass.png");
		TextureManager.g_power = new TextureHandler(pathImage + "Gun/g_power.png");
		TextureManager.g_kkp = new TextureHandler(pathImage + "Gun/g_kkp.png"); 
		TextureManager.g_sniper = new TextureHandler(pathImage + "Gun/g_sniper.png"); 
		TextureManager.g_vampire = new TextureHandler(pathImage + "Gun/g_vampire.png"); 
		
		TextureManager.player_color = new TextureHandler(pathImage + "Sys/player_color.png");
		TextureManager.player_sys = new TextureHandler(pathImage + "Sys/player_sys.png");
		TextureManager.error = new TextureHandler(pathImage + "Sys/error.png");
		TextureManager.cursor_aim = new TextureHandler(pathImage + "Sys/cursor_aim.png");
	
		TextureManager.home1 = new TextureHandler(pathImage + "Wall/home1.png");
		TextureManager.home2 = new TextureHandler(pathImage + "Wall/home2.png");
		TextureManager.home3 = new TextureHandler(pathImage + "Wall/home3.png");
		TextureManager.home4 = new TextureHandler(pathImage + "Wall/home4.png");
		TextureManager.home5 = new TextureHandler(pathImage + "Wall/home5.png");
		TextureManager.home6 = new TextureHandler(pathImage + "Wall/home6.png");
		TextureManager.home7 = new TextureHandler(pathImage + "Wall/home7.png");
		TextureManager.home8 = new TextureHandler(pathImage + "Wall/home8.png");
		TextureManager.home9 = new TextureHandler(pathImage + "Wall/home9.png");
		TextureManager.home10 = new TextureHandler(pathImage + "Wall/home10.png");
		TextureManager.home11 = new TextureHandler(pathImage + "Wall/home11.png");
		TextureManager.home12 = new TextureHandler(pathImage + "Wall/home12.png");
		TextureManager.home13 = new TextureHandler(pathImage + "Wall/home13.png");
		TextureManager.grayrock1 = new TextureHandler(pathImage + "Wall/grayrock1.png");
		TextureManager.grayrock2 = new TextureHandler(pathImage + "Wall/grayrock2.png");
		TextureManager.grayrock3 = new TextureHandler(pathImage + "Wall/grayrock3.png");
		TextureManager.grayrock4 = new TextureHandler(pathImage + "Wall/grayrock4.png");
		TextureManager.grayrock5 = new TextureHandler(pathImage + "Wall/grayrock5.png");
		TextureManager.grayrock6 = new TextureHandler(pathImage + "Wall/grayrock6.png");
		TextureManager.tree = new TextureHandler(pathImage + "Wall/tree.png");
		
		TextureManager.box_armor = new TextureHandler(pathImage + "Gaming/box_armor.png");
		TextureManager.box_gun = new TextureHandler(pathImage + "Gaming/box_gun.png");
		TextureManager.box_bullet = new TextureHandler(pathImage + "Gaming/box_bullet.png");
		TextureManager.box_health = new TextureHandler(pathImage + "Gaming/box_health.png");
		
		TextureManager.a_default = parseAnimation(pathAnim + "Corps/a_default");
		TextureManager.a_fortified = parseAnimation(pathAnim + "Corps/a_fortified");
		TextureManager.a_elephant = parseAnimation(pathAnim + "Corps/a_elephant");
		TextureManager.a_fury = parseAnimation(pathAnim + "Corps/a_fury"); 
		TextureManager.a_mite = parseAnimation(pathAnim + "Corps/a_mite"); 
		TextureManager.a_vampire = parseAnimation(pathAnim + "Corps/a_vampire");
	}
	
	public static TextureHandler[] parseAnimation(String path){
		int n=0;
		while (new File(path + "/" + (n+1) + ".png").exists()){
			n++;
		}
		
		TextureHandler[] textureHandler = new TextureHandler[n];
		
		//Загрузка изображений
		for(int i=0;i<n;i++){
			textureHandler[i] = new TextureHandler(path + "/" + (i+1) + ".png");
		}
		
		return textureHandler;
	}
	
	//Получение ссылки на спрайт из строки
	public static TextureHandler getTexture(String s){
		switch(s){
			case "home1": return TextureManager.home1;
			case "home2": return TextureManager.home2;
			case "home3": return TextureManager.home3;
			case "home4": return TextureManager.home4;
			case "home5": return TextureManager.home5;
			case "home6": return TextureManager.home6;
			case "home7": return TextureManager.home7;
			case "home8": return TextureManager.home8;
			case "home9": return TextureManager.home9;
			case "home10": return TextureManager.home10;
			case "home11": return TextureManager.home11;
			case "home12": return TextureManager.home12;
			case "home13": return TextureManager.home13;
			
			case "road_g": return TextureManager.road_g;
			case "road_g_fork": return TextureManager.road_g_fork;
			case "road_g_turn": return TextureManager.road_g_turn;
			case "road_g_inter": return TextureManager.road_g_inter;
			case "road_a": return TextureManager.road_a;
			case "road_a_g": return TextureManager.road_a_g;
			case "road_a_fork": return TextureManager.road_a_fork;
			case "road_a_inter": return TextureManager.road_a_inter;
			
			case "player_color": return TextureManager.player_color;
			
			case "b_default": return TextureManager.b_default;
			case "b_steel": return TextureManager.b_steel;
			case "b_mortar": return TextureManager.b_square;
			case "b_fury": return TextureManager.b_fury;
			case "b_mass": return TextureManager.b_mass;
			case "b_mass_small": return TextureManager.b_mass_small;
			case "b_patron": return TextureManager.b_patron; 
			case "b_vampire": return TextureManager.b_vampire; 
			case "b_streamlined": return TextureManager.b_streamlined; 
		}
		return TextureManager.error;
	}
	
	public static String getType(String s){
		switch(s){
			case "home1": return "Home";
			case "home2": return "Home";
			case "home3": return "Home";
			case "home4": return "Home";
			case "home5": return "Home";
			case "home6": return "Home";
			case "home7": return "Home";
			case "home8": return "Home";
			case "home9": return "Home";
			case "home10": return "Home";
			case "home11": return "Home";
			case "home12": return "Home";
			case "home13": return "Home";
			
			case "road_g": return "Road";
			case "road_g_fork": return "Road";
			case "road_g_turn": return "Road";
			case "road_g_inter": return "Road";
			case "road_a": return "Road";
			case "road_a_g": return "Road";
			case "road_a_fork": return "Road";
			case "road_a_inter": return "Road";
			
			case "b_default": return "Bullet";
			case "b_steel": return "Bullet";
			case "b_mortar": return "Bullet";
			case "b_fury": return "Bullet";
			case "b_mass": return "Bullet";
			case "b_mass_small": return "Bullet";
			case "b_vampire": return "Bullet"; 
			case "b_patron": return "Bullet"; 
			case "b_streamlined": return "Bullet"; 
		}
		return "Error";
	}

}
