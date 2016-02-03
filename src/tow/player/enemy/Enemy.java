package tow.player.enemy;

import java.awt.Color;

import tow.Global;
import tow.image.Animation;
import tow.image.Rendering;
import tow.obj.Obj;

public class Enemy extends Obj{
	
	public boolean animOn = false;
	public boolean haveData = false;//»меем ли мы все данные об этом враге
	
	public Color c = Color.WHITE;
	public String name;
	public Obj gun;
	public EnemyArmor armor;
	
	public Enemy(double x, double y, double direction, String name){
		super(x,y,0.0,direction,1,false,Global.player_sys);
		this.name = name;
		this.armor = new EnemyArmor(Global.c_default,this);
		this.gun = new Obj(x,y,0.0,direction,-1,false,Global.defaultgun);
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
			armor.getAnimation().setFrameSpeed(anim);
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
		armor.getImage().setColor(c);
	}
	
	public void setColorGun(){
		gun.getImage().setColor(c);
	}
	
	public void updateChildFinal(){
		dragIn();
		try{
			int nameX = (int) Math.round(getXViewCenter()-name.length()*3.25); // lengthChar/2
			int nameY = (int) getYViewCenter()-50;
			
			Global.game.render.addTitle(nameX, nameY, name);
		} catch(NullPointerException e){
			p("[ERROR] Draw enemy name");
		}
	}
	
	public void newArmor(String nameArmor){
		double lastArmorDirection = armor.direction;
		armor.destroy();
		Animation anim;
		switch(nameArmor){
			case "DefaultArmor": anim = Global.c_default; break;
			case "FortifiedArmor": anim = Global.c_fortified; break;
			case "ElephantArmor": anim = Global.c_elephant; break;
			default: anim = Global.c_default; Global.error("Not find armor name (take14)"); break;
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
			case "DefaultGun": image = Global.defaultgun; break;
			case "DoubleGun": image = Global.doublegun; break;
			case "PowerGun": image = Global.powergun; break;
			case "BigGun": image = Global.biggun; break;
			default: image = Global.defaultgun; Global.error("Not find gun name (take15)"); break;
		}
		gun = new Obj(x,y,0.0,direction,-1,false,image);
		gun.direction = lastGunDirection;
		setColorGun();
	}
	
	public void dragIn(){
		try{
			armor.setXcenter(getXcenter());
			armor.setYcenter(getYcenter());
			armor.setDirection(getDirection());
			gun.setXcenter(getXcenter());
			gun.setYcenter(getYcenter());
		} catch(NullPointerException e){
			p("[ERROR] DragIn to enemy");
		}
	}
}