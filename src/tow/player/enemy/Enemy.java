package tow.player.enemy;

import org.newdawn.slick.Color;

import tow.Global;
import tow.image.TextureHandler;
import tow.image.TextureManager;
import tow.obj.Obj;
import tow.title.Title;

public class Enemy extends Obj{
	
	public boolean animOn = false;
	public boolean haveData = false;//Имеем ли мы все данные об этом враге
	
	public Color c = Color.white;
	public String name;
	public Obj gun;
	public EnemyArmor armor;
	
	public Enemy(double x, double y, double direction, String name){
		super(x,y,0.0,direction,1,false,TextureManager.sys_null);
		this.name = name;
		this.armor = new EnemyArmor(TextureManager.a_default,this);
		this.gun = new Obj(x,y,0.0,direction,-1,false,TextureManager.g_default);
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
		armor.getImage().setColor(c);
	}
	
	public void setColorGun(){
		gun.getImage().setColor(c);
	}
	
	@Override
	public void updateChildFinal(long delta){
		dragIn();
		try{
			int nameX = (int) Math.round(getXView()-name.length()*3.25); // lengthChar/2
			int nameY = (int) getYView()-50;
			
			Global.game.render.addTitle(new Title(nameX, nameY, name));
		} catch(NullPointerException e){
			Global.error("Draw enemy name");
		}
	}
	
	public void newArmor(String nameArmor){
		double lastArmorDirection = armor.direction;
		armor.destroy();
		TextureHandler[] anim;
		switch(nameArmor){
			case "ADefault": anim = TextureManager.a_default; break;
			case "AFortified": anim = TextureManager.a_fortified; break;
			case "AElephant": anim = TextureManager.a_elephant; break;
			case "AFury": anim = TextureManager.a_fury; break;
			case "AMite": anim = TextureManager.a_mite; break;
			case "AVampire": anim = TextureManager.a_vampire; break;
			default: anim = TextureManager.a_default; Global.error("Not find armor name (take14)"); break;
		}
		armor = new EnemyArmor(anim,this);
		armor.direction = lastArmorDirection;
		setColorArmor();
	}
	
	public void newGun(String nameGun){
		double lastGunDirection = gun.direction;
		gun.destroy();
		TextureHandler textureHandler;
		switch(nameGun){
			case "GDefault": textureHandler = TextureManager.g_default; break;
			case "GDouble": textureHandler = TextureManager.g_double; break;
			case "GPower": textureHandler = TextureManager.g_power; break;
			case "GBig": textureHandler = TextureManager.g_big; break;
			case "GFury": textureHandler = TextureManager.g_fury; break;
			case "GMortar": textureHandler = TextureManager.g_mortar; break;
			case "GRocketd": textureHandler = TextureManager.g_rocketd; break;
			case "GKkp": textureHandler = TextureManager.g_kkp; break;
			case "GSniper": textureHandler = TextureManager.g_sniper; break;
			case "GVampire": textureHandler = TextureManager.g_vampire; break;
			case "GOsmos": textureHandler = TextureManager.g_osmos; break;
			default: textureHandler = TextureManager.g_default; Global.error("Not find gun name (take15)"); break;
		}
		gun = new Obj(x,y,0.0,direction,-1,false,textureHandler);
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