package tow.player;

import tow.Global;
import tow.image.TextureHandler;
import tow.obj.Obj;
import tow.player.enemy.EnemyArmor;
import tow.setting.ConfigReader;

public class Bullet extends Obj{
	
	@SuppressWarnings("unused")
	private Player player;
	private double damage;//дамаг пушки+патрона
	private int range;//дальность пушки+патрона
	
	private double startX;
	private double startY;
	private long idNet;
	
	public static final String pathSetting = "bullet/";
	
	public Bullet(Player player, double x, double y, double dir,double damage, int range, TextureHandler textureHandler){
		super(x,y,0,dir,0,true,textureHandler);
		this.player = player;
		this.damage = damage;//ƒамаг исключительно от выстрелевшей пушки
		this.range = range;
		loadData(getClass().getName());
		
		this.idNet = Global.idNet;
		Global.tcpSend.send1(this);
		Global.idNet++;
		
		startX = getX();
		startY = getY();
		setCollObj(new String[] {"tow.map.Home", "tow.player.enemy.EnemyArmor", "tow.map.Border"});
		mask.thisBullet(this.range, collObj, startX, startY, directionDraw);
	}
	
	public void collReport(Obj obj){
		if (obj.getClass().getName().equals("tow.map.Home") || obj.getClass().getName().equals("tow.map.Border")){
			destroyBullet();
		}
		if (obj.getClass().getName().equals("tow.player.enemy.EnemyArmor")){
			EnemyArmor ea = (EnemyArmor) obj;
			Global.tcpSend.send3(this,ea.enemy.name);
			destroyBullet();
		}
	}
	
	public void destroyBullet(){
		Global.tcpSend.send2(this);
		destroy();
	}
	
	@Override
	public void updateChildStart(long delta){
		if (!getDestroy()){
			if (Math.sqrt(Math.pow(startX-getX(), 2) + Math.pow(startY-getY(), 2)) >= range){
				destroyBullet();
			}
		}
	}
	
	public void loadData(String fileName){
		ConfigReader cr = new ConfigReader(pathSetting + fileName.substring(fileName.lastIndexOf('.')+1) + ".properties");
		
		setSpeed(cr.findDouble("SPEED"));
		damage += cr.findDouble("DAMAGE");//  дамагу пушки прибавл€ем дамаг патрона
		range += cr.findInteger("RANGE");//  дальности пушки прибавл€ем дальность патрона
	}

	//” патрон спрайт не поорачиваетс€ в сторону полЄта(ѕереопределено в ракете и т.д.)
	@Override
	public void directionDrawEqulas(){
		setDirectionDraw(0);
	}
	
	public double getDamage(){
		return damage;
	}
	
	public double getRange(){
		return range;
	}
	
	public long getIdNet(){
		return idNet;
	}
}