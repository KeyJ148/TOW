package tow.game.client.tanks.enemy;

import tow.engine3.image.TextureHandler;
import tow.engine2.obj.Obj;
import tow.engine2.obj.components.Collision;
import tow.engine2.obj.components.Movement;

public class EnemyArmor extends Obj {
	
	public Enemy enemy;
	
	public EnemyArmor(int x, int y, double direction, TextureHandler[] textureHandler, Enemy enemy){
		super(x, y, direction, textureHandler);
		this.enemy = enemy;

		movement = new Movement(this);
		movement.directionDrawEquals = false;
		collision = new Collision(this, textureHandler[0].mask);
	}

	@Override
	public void update(long delta){
		super.update(delta);

		enemy.followToArmor(enemy.gun);
		enemy.followToArmor(enemy.camera);
	}
	
}