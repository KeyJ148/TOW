package tow.game.client.tanks.player;

import tow.engine3.image.TextureHandler;
import tow.engine3.image.TextureManager;
import tow.engine3.map.Border;
import tow.engine3.obj.Obj;
import tow.engine3.obj.components.Collision;
import tow.engine3.obj.components.Movement;
import tow.engine3.obj.components.Position;
import tow.engine3.obj.components.render.Animation;
import tow.engine3.setting.ConfigReader;
import tow.game.client.map.Box;
import tow.game.client.map.Wall;
import tow.game.client.tanks.Effect;
import tow.game.client.tanks.enemy.EnemyArmor;

/*
ПРИ ДОБАВЛЕНИЕ НОВОГО КЛАССА БРОНИ ОБНОВИТЬ BMassSmall.java
 */

public class Armor extends Obj{

	public static final String PATH_SETTING = "game/armor/";
	public String name, title; //name - техническое название, title - игровое

	public Player player;
	public Effect effect = new Effect();

	public int animSpeed;
	public TextureHandler[] textureHandlers;

	public void init(Player player, double x, double y, double direction, String name){
		this.player = player;
		this.name = name;

		loadData();

		position = new Position(this, x, y, textureHandlers[0].depth, direction);
		rendering = new Animation(this, textureHandlers);
		movement = new Movement(this);
		movement.setDirection(direction);
		movement.update(0);

		collision = new Collision(this, textureHandlers[0].mask);
		collision.addCollisionObjects(new Class[] {Wall.class, EnemyArmor.class, Box.class, Border.class});
		collision.addListener(player.controller);
	}

	@Override
	public void update(long delta){
		super.update(delta);

		//Если мы мертвы, то ничего не делать
		if (!player.alive) return;

		//Чтобы остальные чатси не отставали
		player.followToArmor(player.gun);
		player.followToArmor(player.camera);
		
		//Для анимации гусениц
		Animation animation = (Animation) rendering;
		if (movement.speed != 0 && animation.getFrameSpeed() == 0){
			animation.setFrameSpeed(animSpeed);
		}
		if (movement.speed == 0 && animation.getFrameSpeed() != 0){
			animation.setFrameSpeed(0);
		}
	}

	@Override
	public void destroy(){
		super.destroy();
		player.effects.remove(effect);
	}

	public String getConfigFileName(){
		return PATH_SETTING + name + ".properties";
	}
	
	public void loadData(){
		ConfigReader cr = new ConfigReader(getConfigFileName());
		
		effect.addition.hpMax = cr.findDouble("HP_MAX");
		effect.addition.hpRegen = cr.findDouble("HP_REGEN");
		effect.addition.speedTankUp = cr.findDouble("SPEED_UP");
		effect.addition.speedTankDown = cr.findDouble("SPEED_DOWN");
		effect.addition.directionGunUp = cr.findDouble("DIRECTION_GUN_UP");
		effect.addition.directionTankUp = cr.findDouble("DIRECTION_TANK_UP");
		effect.addition.stability = cr.findInteger("STABILITY");

		animSpeed = cr.findInteger("ANIMATION_SPEED");
		textureHandlers = TextureManager.getAnimation(cr.findString("IMAGE_NAME"));
		title = cr.findString("TITLE");
	}
}