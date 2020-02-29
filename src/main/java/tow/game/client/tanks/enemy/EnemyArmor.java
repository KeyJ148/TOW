package tow.game.client.tanks.enemy;

import tow.engine.image.TextureHandler;
import tow.engine.obj.GameObject;
import tow.engine.obj.components.Collision;
import tow.engine.obj.components.Movement;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Animation;

import java.util.Arrays;

public class EnemyArmor extends GameObject {
	
	public Enemy enemy;
	
	public EnemyArmor(int x, int y, double direction, TextureHandler[] textureHandler, Enemy enemy){
		super(Arrays.asList(new Position(x, y, textureHandler[0].depth, (int) direction), new Animation(textureHandler)));
		this.enemy = enemy;

		setComponent(new Movement());
		getComponent(Movement.class).directionDrawEquals = false;
		setComponent(new Collision(textureHandler[0].mask));
	}
}