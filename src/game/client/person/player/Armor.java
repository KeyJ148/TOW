package game.client.person.player;

import engine.Global;
import engine.image.TextureHandler;
import engine.map.Border;
import engine.obj.Obj;
import engine.obj.components.Collision;
import engine.obj.components.Movement;
import engine.obj.components.Position;
import engine.obj.components.render.Animation;
import engine.setting.ConfigReader;
import game.client.map.Box;
import game.client.map.Wall;
import game.client.person.enemy.EnemyArmor;

public class Armor extends Obj{

	public final String PATH_SETTING = "armor/";

	public Player player;

	public double hp;
	public double hpMax;
	public double hpRegen; //Кол-во реген хп в секунду
	public double speedTankUp; //Кол-во пикселей в секунду
	public double speedTankDown;
	public double directionGunUp; //Кол-во градусов в секунду
	public double directionTankUp;
	
	public int animSpeed;
	public String[] allowGun;

	public int stability; //На сколько прочные объекты может сбивать танк

	public TextureHandler[] textureHandlers;

	public Armor(TextureHandler[] textureHandlers){
		this.textureHandlers = textureHandlers;
	}

	public void init(Player player, double x, double y, double direction){
		this.player = player;

		position = new Position(this, x, y, textureHandlers[0].depth, direction);
		movement = new Movement(this);
		rendering = new Animation(this, textureHandlers);

		collision = new Collision(this, textureHandlers[0].mask);
		collision.addCollisionObjects(new Class[] {Wall.class, EnemyArmor.class, Box.class, Border.class});
		collision.addListener(player.controller);

		String className = getClass().getName();
		loadData(PATH_SETTING + className.substring(className.lastIndexOf('.')+1) + ".properties");
	}

	@Override
	public void update(long delta){
		super.update(delta);

		//Если мы мертвы, то ничего не делать
		if (!player.alive) return;
		
		//Для анимации гусениц
		Animation animation = (Animation) rendering;
		if (movement.speed != 0 && animation.getFrameSpeed() == 0){
			animation.setFrameSpeed(animSpeed);
		}
		if (movement.speed == 0 && animation.getFrameSpeed() != 0){
			animation.setFrameSpeed(0);
		}
		
		//hp
		if(hp <= 0){
			Global.tcpControl.send(12, "");
			player.exploded();
		} else {
			if ((hp+delta/Math.pow(10, 9)*hpRegen) > hpMax){
				hp = hpMax;
			} else {
				hp += delta/Math.pow(10, 9)*hpRegen;
			}
		}

		//Следование player и пушки за бронёй
		player.position.x = position.x;
		player.position.y = position.y;
		Global.room.mapControl.update(player);

		player.gun.position.x = position.x;
		player.gun.position.y = position.y;
		Global.room.mapControl.update(player.gun);//Уже прописано в obj.update, но на всякий
	}
	
	public void loadData(String fileName){
		ConfigReader cr = new ConfigReader(fileName);
		
		hpMax = cr.findDouble("HP_MAX");
		hp = hpMax;
		hpRegen = cr.findDouble("HP_REGEN");
		speedTankUp = cr.findDouble("SPEED_UP");
		speedTankDown = cr.findDouble("SPEED_DOWN");
		directionGunUp = cr.findDouble("DIRECTION_GUN_UP");
		directionTankUp = cr.findDouble("DIRECTION_TANK_UP");
		
		animSpeed = cr.findInteger("ANIMATION_SPEED");
		allowGun = cr.findString("ALLOW_GUN").split(" ");

		stability = cr.findInteger("STABILITY");
	}
}