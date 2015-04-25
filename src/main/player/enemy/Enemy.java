package main.player.enemy;

import main.*;

public class Enemy extends Obj{
	
	public int nameX;
	public int nameY;
	
	public boolean animOn = false;
	
	public String name;
	public Obj gun;
	public EnemyArmor armor;
	
	public Enemy(double x, double y, double direction, Game game, String name){
		super(x,y,0.0,direction,1,false,Global.player_sys,game);
		this.name = name;
		this.armor = new EnemyArmor(Global.c_default,this,game);
		this.gun = new Obj(x,y,0.0,direction,-1,false,Global.defaultgun,game);
	}
	
	public void setData(String s){
		setX(Double.parseDouble(Global.linkCS.parsString(s,2)));
		setY(Double.parseDouble(Global.linkCS.parsString(s,3)));
		setDirection(Double.parseDouble(Global.linkCS.parsString(s,4)));
		armor.setXcenter(getXcenter());
		armor.setYcenter(getYcenter());
		armor.setDirection(getDirection());
		gun.setXcenter(getXcenter());
		gun.setYcenter(getYcenter());
		gun.setDirection(Double.parseDouble(Global.linkCS.parsString(s,7)));
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
	
	public void updateChildFinal(){
		try{
			nameX = (int) Math.round(getXViewCenter()-name.length()*3.25); // lengthChar/2
			nameY = (int) getYViewCenter()-50;
		} catch(NullPointerException e){
			p("[ERROR] Draw enemy name");
		}
	}
}