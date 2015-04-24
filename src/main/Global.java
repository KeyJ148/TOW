package main;

import java.util.ArrayList;
import java.util.Vector;

import main.net.*;
import main.player.*;
import main.player.enemy.*;

public class Global {
	
	public static Vector<Obj> obj; //ћассив со всеми объектами
	public static Vector<DepthVector> depth; //ћассив с DepthVector
	
	public static Player player; //главный игрок
	public static ClientNetSend clientSend; //цикл отправки данных на сервер
	public static ClientNetThread clientThread; //цикл считывани€ данных с сервера
	public static int idNet = 1; //id объекта создаваемого у противника из-за действий игрока  
	public static LinkCS linkCS; //общее звено клиента и сервера
	public static Enemy[] enemy; //список всех противников
	public static Vector<EnemyBullet> enemyBullet; //список всех патронов противников (EnemyBullet)
	
	public static double cameraX;
	public static double cameraY;
	public static double cameraXView;
	public static double cameraYView;
	
	public static Sprite background;
	
	public static Sprite road_g;
	public static Sprite road_g_fork;
	public static Sprite road_g_turn;
	public static Sprite road_g_inter;
	public static Sprite road_a;
	public static Sprite road_a_g;
	public static Sprite road_a_fork;
	public static Sprite road_a_inter_big;
	public static Sprite b_default;
	public static Sprite b_mortar;
	public static Sprite b_massgun;
	public static Sprite b_massgun_small;
	public static Sprite b_gunfury;
	public static Sprite defaultgun;
	public static Sprite doublegun;
	public static Sprite gunfury;
	public static Sprite mortar;
	public static Sprite rocketgun;
	public static Sprite massgun;
	public static Sprite player_color;
	public static Sprite home1;
	public static Sprite home2;
	public static Sprite home3;
	public static Sprite home4;
	public static Sprite home5;
	public static Sprite home6;
	public static Sprite home7;
	public static Sprite home8;
	public static Sprite home9;
	public static Sprite home10;
	public static Sprite home11;
	public static Sprite home12;
	public static Sprite home13;
	public static Sprite grayrock1;
	public static Sprite grayrock2;
	public static Sprite grayrock3;
	public static Sprite grayrock4;
	public static Sprite grayrock5;
	public static Sprite grayrock6;
	public static Sprite tree;
	public static Sprite error;
	public static Sprite player_sys;
	
	public static Animation c_default;
}

