package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.DepthConstants;
import cc.abro.tow.client.particles.Explosion;

import java.util.Arrays;

public class EnemyBullet extends GameObject {

	public int idEnemy;
	public long idNet;

	public EnemyBullet(Location location, double x, double y, double speed, double direction, Texture texture,
					   boolean rotation, int idEnemy, long idNet) {
		super(location, Arrays.asList(
				new Movement(speed), //direction
				new SpriteRender(texture, DepthConstants.ENEMY_BULLET_SPRITE_Z)
		));
		setPosition(x, y);
		getComponent(Movement.class).setDirection(direction);

		this.idEnemy = idEnemy;
		this.idNet = idNet;

		if (rotation) {
			getComponent(Rendering.class).setDirection(getComponent(Movement.class).getDirection());
		}
	}

	public void destroy(int explosionSize) {
		destroy();

		if (explosionSize > 0) {
			GameObject explosion = new GameObject(getLocation());
			explosion.setPosition(getX(), getY());
			Explosion explosionParticles = new Explosion(explosionSize, DepthConstants.EXPLOSION_Z);
			explosionParticles.destroyObject = true;
			explosion.addComponent(explosionParticles);
			explosionParticles.activate();
		}
	}
}
