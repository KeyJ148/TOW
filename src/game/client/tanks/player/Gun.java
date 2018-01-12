package game.client.tanks.player;

import engine.Global;
import engine.Loader;
import engine.Vector2;
import engine.image.TextureHandler;
import engine.image.TextureManager;
import engine.io.Logger;
import engine.obj.Obj;
import engine.obj.components.Movement;
import engine.obj.components.Position;
import engine.obj.components.render.Sprite;
import engine.setting.ConfigReader;
import game.client.tanks.Effect;
import game.client.tanks.equipment.bullet.BDefault;

public class Gun extends Obj {

	public static final String PATH_SETTING = "game/gun/";
	public String name;

	public int countTrunk; //Кол-во стволов
	public Vector2<Integer>[] trunksOffset; //Смещение конца каждого ствола по (x,y) (откуда вылетает снаряд)
	public double[] directionTrunk; //Направление конца каждого ствола (в каком направление вылетает снаряд)

	public Effect effect = new Effect();
	public long nanoSecFromAttack = 0;//Кол-во времени до конца перезарядки в наносекундах

	public Player player;
	public TextureHandler texture;

	public void init(Player player, double x, double y, double direction, String name){
		this.player = player;
		this.name = name;

		loadData();

		position = new Position(this, x, y, texture.depth, direction);
		rendering = new Sprite(this, texture);

		movement = new Movement(this);
		movement.directionDrawEquals = false;
	}

	@Override
	public void update(long delta){
		super.update(delta);

		//Если мы мертвы, то ничего не делать
		if (!player.alive) return;

		//Уменьшаем время до выстрела
		nanoSecFromAttack -= delta;
	}

	@Override
	public void destroy(){
		super.destroy();
		player.effects.remove(effect);
	}

	public void attack(){
		nanoSecFromAttack = (long) ((double) 1/player.stats.attackSpeed*Math.pow(10, 9)); //Устанавливаем время перезарядки
		try{
			//По очереди стреляем из всех стволов
			for (int i = 0; i < countTrunk; i++) {
				attackFromTrunk(trunksOffset[i].x, trunksOffset[i].y, directionTrunk[i]);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
			Logger.println("Bullet create error: " + player.bullet, Logger.Type.ERROR);
			Loader.exit();
		}
	}

	private void attackFromTrunk(int trunkX, int trunkY, double direction) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		double trunkXdx = trunkX*Math.cos(Math.toRadians(position.getDirectionDraw())-Math.PI/2);//первый отступ "вперед"
		double trunkXdy = trunkX*Math.sin(Math.toRadians(position.getDirectionDraw())-Math.PI/2);//в отличие от маски мы отнимаем от каждого по PI/2
		double trunkYdx = trunkY*Math.cos(Math.toRadians(position.getDirectionDraw())-Math.PI);//потому что изначально у теустуры измененное направление
		double trunkYdy = trunkY*Math.sin(Math.toRadians(position.getDirectionDraw())-Math.PI);//второй отступ "вбок"

		//Создание класса через рефлексию
		String newBulletClassName = new ConfigReader(Bullet.PATH_SETTING + player.bullet + ".properties").findString("CLASS");
		String newBulletFullName = BDefault.class.getPackage().getName() + "." + newBulletClassName;
		Bullet newBullet = (Bullet) Class.forName(newBulletFullName).newInstance();
		newBullet.init(
				player,
				position.x+trunkXdx+trunkYdx,
				position.y-trunkXdy-trunkYdy,
				position.getDirectionDraw()+direction,
				player.stats.damage,
				player.stats.range,
				player.bullet
		);

		Global.room.objAdd(newBullet);
	}

	public String getConfigFileName(){
		return PATH_SETTING + name + ".properties";
	}
	
	public void loadData(){
		ConfigReader cr = new ConfigReader(getConfigFileName());

		countTrunk = cr.findInteger("COUNT_TRUNK");
		trunksOffset = new Vector2[countTrunk];
		directionTrunk = new double[countTrunk];

		for (int i = 0; i < countTrunk; i++) {
			int trunkX = cr.findInteger("TRUNK_" + (i+1) +  "_X");
			int trunkY = cr.findInteger("TRUNK_" + (i+1) +  "_Y");
			double trunkDir = cr.findInteger("TRUNK_" + (i+1) +  "_DIR");

			trunksOffset[i] = new Vector2<>(trunkX, trunkY);
			directionTrunk[i] = trunkDir;
		}

		effect.addition.attackSpeed = cr.findDouble("ATTACK_SPEED");
		effect.addition.directionGunUp = cr.findDouble("DIRECTION_UP");
		effect.addition.damage  = cr.findDouble("DAMAGE");
		effect.addition.range = cr.findInteger("RANGE");

		texture = TextureManager.getTexture(cr.findString("IMAGE_NAME"));
	}
}