package tow.game.client.tanks.enemy;

import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Collision;
import tow.engine.gameobject.components.Movement;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.AnimationRender;
import tow.engine.resources.animations.Animation;

import java.util.Arrays;

public class EnemyArmor extends GameObject {
	
	public Enemy enemy;
	
	public EnemyArmor(int x, int y, double direction, int depth, Animation animation, Enemy enemy){
		super(Arrays.asList(new Position(x, y, depth, (int) direction), new AnimationRender(animation.getTextures())));
		this.enemy = enemy;

		setComponent(new Movement());
		getComponent(Movement.class).directionDrawEquals = false;
		setComponent(new Collision(animation.getMask()));
	}
}