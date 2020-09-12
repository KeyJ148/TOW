package tech.abro.tow.client.tanks.player;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.gameobject.GameObject;
import tech.abro.orchengine.gameobject.components.Collision;
import tech.abro.orchengine.gameobject.components.Follower;
import tech.abro.orchengine.gameobject.components.Movement;
import tech.abro.orchengine.gameobject.components.Position;
import tech.abro.orchengine.gameobject.components.render.AnimationRender;
import tech.abro.orchengine.gameobject.components.render.Rendering;
import tech.abro.orchengine.map.Border;
import tech.abro.orchengine.resources.animations.Animation;
import tech.abro.orchengine.setting.ConfigReader;
import tech.abro.tow.client.map.objects.Box;
import tech.abro.tow.client.map.objects.textured.TexturedMapObject;
import tech.abro.tow.client.tanks.Effect;
import tech.abro.tow.client.tanks.enemy.EnemyArmor;

/*
ПРИ ДОБАВЛЕНИЕ НОВОГО КЛАССА БРОНИ ОБНОВИТЬ BMassSmall.java
 */

public class Armor extends GameObject {

	public static final String PATH_SETTING = "game/armor/";
	public String name, title; //name - техническое название, title - игровое

	public Player player;
	public Effect effect = new Effect();

	public int animSpeed;
	public Animation textureHandlers;

	public void init(Player player, double x, double y, double direction, String name){
		this.player = player;
		this.name = name;

		loadData();

		setComponent(new Position(x, y, 1000, direction));
		setComponent(new AnimationRender(textureHandlers.getTextures()));
		setComponent(new Movement());
		getComponent(Movement.class).setDirection(direction);
		getComponent(Movement.class).update(0);

		setComponent(new Collision(textureHandlers.getMask()));
		getComponent(Collision.class).addCollisionObjects(new Class[] {TexturedMapObject.class, EnemyArmor.class, Box.class, Border.class});
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
		textureHandlers = Global.animationStorage.getAnimation(cr.findString("IMAGE_NAME"));
		title = cr.findString("TITLE");
	}
}