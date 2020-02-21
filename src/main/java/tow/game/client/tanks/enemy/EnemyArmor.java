package tow.game.client.tanks.enemy;

import tow.engine.image.TextureHandler;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Collision;
import tow.engine.obj.components.Movement;

public class EnemyArmor extends Obj {
	
	public Enemy enemy;
	
	public EnemyArmor(int x, int y, double direction, TextureHandler[] textureHandler, Enemy enemy){
		super(x, y, direction, textureHandler);
		this.enemy = enemy;

		movement = new Movement(this);
		movement.directionDrawEquals = false;
		collision = new Collision(this, textureHandler[0].mask);
	}
}