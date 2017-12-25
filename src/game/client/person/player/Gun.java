package game.client.person.player;

import engine.Global;
import engine.Vector2;
import engine.image.TextureHandler;
import engine.io.Logger;
import engine.io.MouseHandler;
import engine.obj.Obj;
import engine.obj.components.Movement;
import engine.obj.components.Position;
import engine.obj.components.render.Sprite;
import engine.setting.ConfigReader;

public class Gun extends Obj {

	public final String PATH_SETTING = "gun/";

	public double attackSpeed1;//скорость атаки
	public double attackSpeed2;//Кол-во выстрелов в секунду
	public double directionGunUp;//скорость поворота дула

	public int range;//Дальность выстрела
	public double damage1;//дамаг
	public double damage2;

	public int trunk1X;//коры смещения от центра
	public int trunk1Y;
	public int trunk2X;
	public int trunk2Y;
	
	public String[] allowArmor;
	public String[] allowBullet;

	public Player player;
	public TextureHandler texture;

	private long nanoSecFromAttack1 = 0;//технические(стартовые)
	private long nanoSecFromAttack2 = 0;//Кол-во времени до конца перезарядки в наносекундах
	
	public Gun(TextureHandler texture){
		this.texture = texture;
	}

	public void init(Player player, double x, double y, double direction){
		this.player = player;

		position = new Position(this, x, y, texture.depth, direction);
		rendering = new Sprite(this, texture);

		movement = new Movement(this);
		movement.directionDrawEquals = false;

		String className = getClass().getName();
		loadData(PATH_SETTING + className.substring(className.lastIndexOf('.')+1) + ".properties");
	}
	
	public void attack1(){
		if ((nanoSecFromAttack1 <= 0) && (attackSpeed1 > 0)){
			switchBullet(player.bullet, trunk1X, trunk1Y, damage1);
			nanoSecFromAttack1 = (long) ((double) 1/attackSpeed1*1000*1000*1000);
		}
	}
	
	public void attack2(){
		if ((nanoSecFromAttack2 <= 0) && (attackSpeed2 > 0)){
			switchBullet(player.bullet, trunk2X, trunk2Y, damage2);
			nanoSecFromAttack2 = (long) ((double) 1/attackSpeed2*1000*1000*1000);
		}
	}

	public void switchBullet(Class bullet, int trunkX, int trunkY, double damage){
		double trunkXdx = trunkX*Math.cos(Math.toRadians(position.getDirectionDraw())-Math.PI/2);//первый отступ "вперед"
		double trunkXdy = trunkX*Math.sin(Math.toRadians(position.getDirectionDraw())-Math.PI/2);//в отличие от маски мы отнимаем от каждого по PI/2
		double trunkYdx = trunkY*Math.cos(Math.toRadians(position.getDirectionDraw())-Math.PI);//потому что изначально у теустуры измененное направление
		double trunkYdy = trunkY*Math.sin(Math.toRadians(position.getDirectionDraw())-Math.PI);//второй отступ "вбок"

		//Создание класса через рефлексию
		try {
			Bullet newBullet = (Bullet) bullet.newInstance();
			newBullet.init(
					player,
					position.x+trunkXdx+trunkYdx,
					position.y-trunkXdy-trunkYdy,
					position.getDirectionDraw(),
					damage,
					range
			);

			Global.room.objAdd(newBullet);
		} catch (InstantiationException | IllegalAccessException e){
			Logger.println("Bullet create error: " + bullet.getSimpleName(), Logger.Type.ERROR);
			System.exit(0);
		}
	}

	@Override
	public void update(long delta){
		super.update(delta);

		//Если мы мертвы, то ничего не делать
		if (!player.alive) return;

		Vector2<Integer> relativePosition = position.getRelativePosition();
		double relativeX = relativePosition.x+0.1;
		double relativeY = relativePosition.y+0.1;

		//поворот дула (много костылей)
		double pointDir = -Math.toDegrees(Math.atan((relativeY-MouseHandler.mouseY)/(relativeX-MouseHandler.mouseX)));
		
		double trunkUp = ((double) delta/1000000000)*(directionGunUp+player.armor.directionGunUp);
		if ((relativeX-MouseHandler.mouseX)>0){
			pointDir+=180;
		} else if ((relativeY-MouseHandler.mouseY)<0){
			pointDir+=360;
		}
		
		if ((pointDir - position.getDirectionDraw()) > 0){
			if ((pointDir - position.getDirectionDraw()) > 180){
				position.setDirectionDraw(position.getDirectionDraw() - trunkUp);
			} else {
				position.setDirectionDraw(position.getDirectionDraw() + trunkUp);
			}
		} else {
			if ((pointDir - position.getDirectionDraw()) < -180){
				position.setDirectionDraw(position.getDirectionDraw() + trunkUp);
			} else {
				position.setDirectionDraw(position.getDirectionDraw() - trunkUp);
			}
		}
		
		if (    (Math.abs(pointDir - position.getDirectionDraw()) < trunkUp*1.5) ||
				(Math.abs(pointDir - position.getDirectionDraw()) > 360-trunkUp*1.5)){
			position.setDirectionDraw(pointDir);
		}
		
		//Выстрел
		nanoSecFromAttack1 -= delta;
		nanoSecFromAttack2 -= delta;
		if (MouseHandler.mouseDown1) attack1();
		if (MouseHandler.mouseDown2) attack2();
	}
	
	public void loadData(String fileName){
		ConfigReader cr = new ConfigReader(fileName);
		
		attackSpeed1 = cr.findDouble("ATTACK_SPEED_1");
		attackSpeed2 = cr.findDouble("ATTACK_SPEED_2");
		trunk1X = cr.findInteger("TRUNK_1_X");
		trunk1Y = cr.findInteger("TRUNK_1_Y");
		trunk2X = cr.findInteger("TRUNK_2_X");
		trunk2Y = cr.findInteger("TRUNK_2_Y");
		
		directionGunUp = cr.findDouble("DIRECTION_UP");
		damage1  = cr.findDouble("DAMAGE_1");
		damage2  = cr.findDouble("DAMAGE_2");
		range = cr.findInteger("RANGE");
		
		allowArmor = cr.findString("ALLOW_ARMOR").split(" ");
		allowBullet = cr.findString("ALLOW_BULLET").split(" ");
	}
}