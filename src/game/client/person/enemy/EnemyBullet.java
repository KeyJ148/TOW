package game.client.person.enemy;

import engine.image.TextureHandler;
import engine.image.TextureManager;
import engine.obj.Obj;
import engine.obj.components.Movement;

public class EnemyBullet extends Obj {

	public int idEnemy;
	public long idNet;
	
	public EnemyBullet(double x, double y, double speed, double direction, TextureHandler texture, int idEnemy, long idNet){
		super(x, y, direction, texture);

		this.idEnemy = idEnemy;
		this.idNet = idNet;

		this.movement = new Movement(this, speed, direction);

		if (texture.equals(TextureManager.getTexture("b_default"))){
			movement.directionDrawEquals = false;
			position.setDirectionDraw(0);
		}
	}
}
