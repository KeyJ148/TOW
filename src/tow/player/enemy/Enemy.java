package tow.player.enemy;

import java.awt.Color;

import tow.Global;
import tow.image.Animation;
import tow.image.Rendering;
import tow.obj.Obj;

public class Enemy extends Obj{
	
	public boolean animOn = false;
	public boolean haveData = false;//Имеем ли мы все данные об этом враге
	
	public Color c = Color.WHITE;
	public String name;
	public Obj gun;
	public EnemyArmor armor;
	
	public Enemy(double x, double y, double direction, String name){
		super(x,y,0.0,direction,1,false,Global.player_sys);
		this.name = name;
		this.armor = new EnemyArmor(Global.a_default,this);
		this.gun = new Obj(x,y,0.0,direction,-1,false,Global.g_default);
	}
	
	public void setData(String s){
		setX(Double.parseDouble(Global.parsString(s,2)));
		setY(Double.parseDouble(Global.parsString(s,3)));
		setDirection(Double.parseDouble(Global.parsString(s,4)));
		gun.setDirection(Double.parseDouble(Global.parsString(s,7)));
		dragIn();
		setSpeed(Double.parseDouble(Global.parsString(s,8)));
		int anim = Integer.parseInt(Global.parsString(s,5)); 
		if ((anim != -1) && (!animOn)){
			armor.getAnimation().setFrameSpeed(anim);
			animOn = true;
		}
		if ((anim == -1) && (animOn)){
			armor.getAnimation().setFrameSpeed(0);
			animOn = false;
		}
	}
	
	public void destroyExtended(){
		this.gun.destroy();
		this.armor.destroy();
		destroy();
	}
	
	public void setColor(){
		setColorArmor();
		setColorGun();
	}
	
	public void setColorArmor(){
		//armor.getImage().setColor(c);
	}
	
	public void setColorGun(){
		//gun.getImage().setColor(c);
	}
	
	@Override
	public void updateChildFinal(long delta){
		dragIn();
		try{
			int nameX = (int) Math.round(getXView()-name.length()*3.25); // lengthChar/2
			int nameY = (int) getYView()-50;
			
			Global.game.render.addTitle(nameX, nameY, name);
		} catch(NullPointerException e){
			Global.error("Draw enemy name");
		}
	}
	
	public void newArmor(String nameArmor){
		double lastArmorDirection = armor.direction;
		armor.destroy();
		Animation anim;
		switch(nameArmor){
			case "ADefault": anim = Global.a_default; break;
			case "AFortified": anim = Global.a_fortified; break;
			case "AElephant": anim = Global.a_elephant; break;
			case "AFury": anim = Global.a_fury; break;
			case "AMite": anim = Global.a_mite; break;
			case "AVampire": anim = Global.a_vampire; break;
			default: anim = Global.a_default; Global.error("Not find armor name (take14)"); break;
		}
		armor = new EnemyArmor(anim,this);
		armor.direction = lastArmorDirection;
		setColorArmor();
	}
	
	public void newGun(String nameGun){
		double lastGunDirection = gun.direction;
		gun.destroy();
		Rendering image;
		switch(nameGun){
			case "GDefault": image = Global.g_default; break;
			case "GDouble": image = Global.g_double; break;
			case "GPower": image = Global.g_power; break;
			case "GBig": image = Global.g_big; break;
			case "GFury": image = Global.g_fury; break;
			case "GMortar": image = Global.g_mortar; break;
			case "GRocketd": image = Global.g_rocketd; break;
			case "GKkp": image = Global.g_kkp; break;
			case "GSniper": image = Global.g_sniper; break;
			case "GVampire": image = Global.g_vampire; break;
			default: image = Global.g_default; Global.error("Not find gun name (take15)"); break;
		}
		gun = new Obj(x,y,0.0,direction,-1,false,image);
		gun.direction = lastGunDirection;
		setColorGun();
	}
	
	public void dragIn(){
		try{
			armor.setX(getX());
			armor.setY(getY());
			armor.setDirection(getDirection());
			Global.mapControl.update(armor);//Необходимо делать после перемещения объекта
			gun.setX(getX());
			gun.setY(getY());
			Global.mapControl.update(gun);//Уже прописано в obj.update, но на всякий
		} catch(NullPointerException e){
			Global.error("DragIn to enemy");
		}
	}
}