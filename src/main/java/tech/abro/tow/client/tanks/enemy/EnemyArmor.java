package tech.abro.tow.client.tanks.enemy;

import tech.abro.orchengine.gameobject.GameObject;
import tech.abro.orchengine.gameobject.components.Collision;
import tech.abro.orchengine.gameobject.components.Movement;
import tech.abro.orchengine.gameobject.components.Position;
import tech.abro.orchengine.gameobject.components.render.AnimationRender;
import tech.abro.orchengine.resources.animations.Animation;

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