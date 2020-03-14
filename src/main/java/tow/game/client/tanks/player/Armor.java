package tow.game.client.tanks.player;

import tow.engine.image.TextureHandler;
import tow.engine.image.TextureManager;
import tow.engine.map.Border;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Collision;
import tow.engine.gameobject.components.Follower;
import tow.engine.gameobject.components.Movement;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.AnimationRender;
import tow.engine.gameobject.components.render.Rendering;
import tow.engine.setting.ConfigReader;
import tow.game.client.map.Box;
import tow.game.client.map.Wall;
import tow.game.client.tanks.Effect;
import tow.game.client.tanks.enemy.EnemyArmor;

/*
ПРИ ДОБАВЛЕНИЕ НОВОГО КЛАССА БРОНИ ОБНОВИТЬ BMassSmall.java
 */

public class Armor extends GameObject {

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

		setComponent(new Position(x, y, textureHandlers[0].depth, direction));
		setComponent(new AnimationRender(textureHandlers));
		setComponent(new Movement());
		getComponent(Movement.class).setDirection(direction);
		getComponent(Movement.class).update(0);

		setComponent(new Collision(textureHandlers[0].mask));
		getComponent(Collision.class).addCollisionObjects(new Class[] {Wall.class, EnemyArmor.class, Box.class, Border.class});
		getComponent(Collision.class).addListener(player.controller);

		if (player.gun != null) player.gun.setComponent(new Follower(this, false));
	}

	@Override
	public void update(long delta){
		super.update(delta);

		//Если мы мертвы, то ничего не делать
		if (!player.alive) return;
		
		//Для анимации гусениц
		AnimationRender animationRender = (AnimationRender) getComponent(Rendering.class);
		if (getComponent(Movement.class).speed != 0 && animationRender.getFrameSpeed() == 0){
			animationRender.setFrameSpeed(animSpeed);
		}
		if (getComponent(Movement.class).speed == 0 && animationRender.getFrameSpeed() != 0){
			animationRender.setFrameSpeed(0);
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