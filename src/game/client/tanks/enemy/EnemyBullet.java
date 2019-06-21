package game.client.tanks.enemy;

import engine.Global;
import engine.image.TextureHandler;
import engine.image.TextureManager;
import engine.obj.Obj;
import engine.obj.components.Movement;
import game.client.particles.Explosion;

public class EnemyBullet extends Obj {

	public int idEnemy;
	public long idNet;
	
	public EnemyBullet(double x, double y, double speed, double direction, TextureHandler texture, int idEnemy, long idNet){
		super(x, y, direction, texture);

		this.idEnemy = idEnemy;
		this.idNet = idNet;

		movement = new Movement(this, speed, direction);

		if (texture.equals(TextureManager.getTexture("b_streamlined"))){
			movement.directionDrawEquals = true;
			position.setDirectionDraw(0);
		} else {
			movement.directionDrawEquals = false;
		}
	}

	public void destroy(int explosionSize){
		destroy();

		if (explosionSize > 0) {
			Obj explosion = new Obj(position.x, position.y, -100);
			explosion.particles = new Explosion(explosion, explosionSize);
			explosion.particles.destroyObject = true;
			Global.room.objAdd(explosion);
		}
	}
}
