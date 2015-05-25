package main.player.enemy;

import java.awt.Color;

import main.Global;
import main.obj.Obj;

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
		setX(Double.parseDouble(Global.linkCS.parsString(s,2)));
		setY(Double.parseDouble(Global.linkCS.parsString(s,3)));
		setDirection(Double.parseDouble(Global.linkCS.parsString(s,4)));
		gun.setDirection(Double.parseDouble(Global.linkCS.parsString(s,7)));
		dragIn();
		setSpeed(Double.parseDouble(Global.linkCS.parsString(s,8)));
		int anim = Integer.parseInt(Global.linkCS.parsString(s,5)); 
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
		armor.getImage().setColor(c);
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